package com.zclcs.platform.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.constant.DictConstant;
import com.zclcs.common.core.constant.GeneratorConstant;
import com.zclcs.common.core.constant.MyConstant;
import com.zclcs.common.core.constant.ParamsConstant;
import com.zclcs.common.core.exception.MyException;
import com.zclcs.common.core.utils.BaseUtil;
import com.zclcs.common.core.utils.FileUtil;
import com.zclcs.common.web.starter.utils.WebUtil;
import com.zclcs.platform.system.api.entity.Column;
import com.zclcs.platform.system.api.entity.Menu;
import com.zclcs.platform.system.api.entity.Table;
import com.zclcs.platform.system.api.entity.ao.GenerateAo;
import com.zclcs.platform.system.api.entity.ao.MenuAo;
import com.zclcs.platform.system.api.entity.vo.GeneratorConfigVo;
import com.zclcs.platform.system.mapper.GeneratorMapper;
import com.zclcs.platform.system.service.GeneratorConfigService;
import com.zclcs.platform.system.service.GeneratorService;
import com.zclcs.platform.system.service.MenuService;
import com.zclcs.platform.system.utils.GeneratorUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * @author zclcs
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GeneratorServiceImpl implements GeneratorService {

    private final GeneratorMapper generatorMapper;
    private final GeneratorConfigService generatorConfigService;
    private final MenuService menuService;

    @Override
    public List<String> getDatabases(String databaseType) {
        return generatorMapper.getDatabases(databaseType);
    }

    @Override
    public BasePage<Table> getTables(String tableName, BasePageAo request, String databaseType, String schemaName) {
        BasePage<Table> page = new BasePage<>(request.getPageNum(), request.getPageSize());
        OrderItem createAt = new OrderItem("create_at", false);
        OrderItem updateAt = new OrderItem("update_at", false);
        page.setOrders(CollectionUtil.newArrayList(createAt, updateAt));
        generatorMapper.getTables(page, tableName, databaseType, schemaName);
        return page;
    }

    @Override
    public List<Column> getColumns(String databaseType, String schemaName, String tableName, List<String> excludeColumns) {
        return generatorMapper.getColumns(databaseType, schemaName, tableName, excludeColumns);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generate(GenerateAo generateAo, HttpServletResponse response) {
        String name = generateAo.getName();
        String remark = generateAo.getRemark();
        GeneratorConfigVo generatorConfigVo = generatorConfigService.findGeneratorConfig(generateAo.getGeneratorConfigId());
        if (generatorConfigVo == null) {
            throw new MyException("代码生成配置为空");
        }
        String className = name;
        if (GeneratorConfigVo.TRIM_YES.equals(generatorConfigVo.getIsTrim())) {
            className = StrUtil.removePrefix(name, generatorConfigVo.getTrimValue());
        }
        String underscoreToCamel = BaseUtil.underscoreToCamel(className);
        createMenu(generateAo, underscoreToCamel);
        generatorConfigVo.setTableName(name);
        generatorConfigVo.setClassName(underscoreToCamel);
        generatorConfigVo.setTableComment(remark);
        // 生成代码到临时目录
        List<Column> columns = this.getColumns(GeneratorConstant.DATABASE_TYPE, generateAo.getDatasource(), name, StrUtil.split(generatorConfigVo.getExcludeColumns(), StrUtil.COMMA));
        for (Column column : columns) {
            if (column.getIsKey()) {
                generatorConfigVo.setKeyName(column.getName());
            }
            String columnRemark = column.getRemark();
            if (StrUtil.contains(columnRemark, MyConstant.DICT_REMARK)) {
                column.setHasDict(true);
                List<String> strings = StrUtil.splitTrim(columnRemark, MyConstant.DICT_REMARK);
                column.setRemarkDict(strings.get(strings.size() - 1));
            } else {
                column.setHasDict(false);
            }
            column.setIsArray(StrUtil.contains(columnRemark, MyConstant.DICT_ARRAY));
            column.setIsTree(StrUtil.contains(columnRemark, MyConstant.DICT_TREE));
        }
        try {
            GeneratorUtil.generateEntityFile(columns, generatorConfigVo);
            GeneratorUtil.generateAoFile(columns, generatorConfigVo);
            GeneratorUtil.generateVoFile(columns, generatorConfigVo);
            GeneratorUtil.generateMapperFile(columns, generatorConfigVo);
            GeneratorUtil.generateMapperXmlFile(columns, generatorConfigVo);
            GeneratorUtil.generateServiceFile(columns, generatorConfigVo);
            GeneratorUtil.generateServiceImplFile(columns, generatorConfigVo);
            GeneratorUtil.generateControllerFile(columns, generatorConfigVo);
            // 打包
            String zipFile = System.currentTimeMillis() + ParamsConstant.SUFFIX;
            FileUtil.compress(GeneratorConstant.TEMP_PATH + "src", zipFile);
            // 下载
            WebUtil.download(zipFile, name + ParamsConstant.SUFFIX, true, response);
            // 删除临时目录
            FileSystemUtils.deleteRecursively(new File(GeneratorConstant.TEMP_PATH));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void createMenu(GenerateAo generateAo, String className) {
        className = StrUtil.lowerFirst(className);
        String isCreateMenuNew = Optional.ofNullable(generateAo.getIsCreateMenu()).filter(StrUtil::isNotBlank).orElse(DictConstant.YES_NO_0);
        String isCreateDirNew = Optional.ofNullable(generateAo.getIsCreateDir()).filter(StrUtil::isNotBlank).orElse(DictConstant.YES_NO_0);
        if (DictConstant.YES_NO_1.equals(isCreateDirNew)) {
            MenuAo dir = new MenuAo();
            setDir(dir, generateAo);
            Menu dirMenu = menuService.createMenu(dir);
            MenuAo menu = new MenuAo();
            setMenu(menu, generateAo, className, dirMenu.getMenuCode());
            addMenuAndButton(menu, className);
        } else if (DictConstant.YES_NO_1.equals(isCreateMenuNew)) {
            MenuAo menu = new MenuAo();
            setMenu(menu, generateAo, className, generateAo.getMenuCode());
            addMenuAndButton(menu, className);
        }
    }

    private void addMenuAndButton(MenuAo menuAo, String className) {
        Menu parentMenu = menuService.createMenu(menuAo);
        for (int i = 0; i < ParamsConstant.AUTHS.length; i++) {
            String auth = ParamsConstant.AUTHS[i];
            MenuAo button = new MenuAo();
            button.setMenuName(ParamsConstant.BUTTON_TEXT[i]);
            button.setParentCode(parentMenu.getMenuCode());
            button.setType(DictConstant.MENU_TYPE_1);
            button.setPerms(className + StrUtil.COLON + auth);
            menuService.createMenu(button);
        }
    }

    private void setDir(MenuAo dir, GenerateAo generateAo) {
        dir.setMenuName(generateAo.getDirName());
        dir.setType(DictConstant.MENU_TYPE_2);
        dir.setIcon(ParamsConstant.DEFAULT_MENU_ICON);
        dir.setPath(generateAo.getDirPath());
        dir.setHideMenu(DictConstant.YES_NO_0);
        dir.setHideBreadcrumb(DictConstant.YES_NO_0);
        dir.setHideChildrenInMenu(DictConstant.YES_NO_0);
    }

    private void setMenu(MenuAo menuAo, GenerateAo generateAo, String className, String parentCode) {
        menuAo.setMenuName(Optional.ofNullable(generateAo.getMenuName()).filter(StrUtil::isNotBlank).orElse(generateAo.getRemark()));
        menuAo.setKeepAliveName(StrUtil.upperFirst(className));
        menuAo.setParentCode(parentCode);
        menuAo.setType(DictConstant.MENU_TYPE_0);
        menuAo.setIcon(ParamsConstant.DEFAULT_MENU_ICON);
        menuAo.setPath(Optional.ofNullable(generateAo.getMenuPath()).filter(StrUtil::isNotBlank).orElse(className));
        menuAo.setComponent(generateAo.getMenuComponent());
        menuAo.setIgnoreKeepAlive(DictConstant.YES_NO_0);
        menuAo.setHideMenu(DictConstant.YES_NO_0);
        menuAo.setHideBreadcrumb(DictConstant.YES_NO_0);
        menuAo.setHideChildrenInMenu(DictConstant.YES_NO_0);
    }
}
