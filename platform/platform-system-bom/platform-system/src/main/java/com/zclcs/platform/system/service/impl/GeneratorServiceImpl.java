package com.zclcs.platform.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import com.zclcs.cloud.lib.core.constant.Dict;
import com.zclcs.cloud.lib.core.constant.Generator;
import com.zclcs.cloud.lib.core.constant.Params;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.core.utils.BaseUtil;
import com.zclcs.cloud.lib.core.utils.FileUtil;
import com.zclcs.common.web.starter.utils.WebUtil;
import com.zclcs.platform.system.api.bean.entity.Column;
import com.zclcs.platform.system.api.bean.entity.Menu;
import com.zclcs.platform.system.api.bean.entity.Table;
import com.zclcs.platform.system.api.bean.ao.GenerateAo;
import com.zclcs.platform.system.api.bean.ao.MenuAo;
import com.zclcs.platform.system.api.bean.vo.GeneratorConfigVo;
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
import java.util.Locale;
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
        String tableName = name;
        if (GeneratorConfigVo.TRIM_YES.equals(generatorConfigVo.getIsTrim())) {
            tableName = StrUtil.removePrefix(name, generatorConfigVo.getTrimValue());
            className = tableName;
        }
        String underscoreToCamel = BaseUtil.underscoreToCamel(className);
        createMenu(generateAo, underscoreToCamel, tableName);
        generatorConfigVo.setTableName(name);
        generatorConfigVo.setClassName(underscoreToCamel);
        generatorConfigVo.setTableComment(remark);
        // 生成代码到临时目录
        List<Column> columns = this.getColumns(Generator.DATABASE_TYPE, generateAo.getDatasource(), name, StrUtil.split(generatorConfigVo.getExcludeColumns(), StrUtil.COMMA));
        for (Column column : columns) {
            if (column.getIsKey()) {
                generatorConfigVo.setKeyName(column.getName());
            }
            String columnRemark = column.getRemark();
            if (StrUtil.contains(columnRemark, CommonCore.DICT_REMARK)) {
                column.setHasDict(true);
                List<String> strings = StrUtil.splitTrim(columnRemark, CommonCore.DICT_REMARK);
                column.setRemarkDict(strings.get(strings.size() - 1));
            } else {
                column.setHasDict(false);
            }
            column.setIsArray(StrUtil.contains(columnRemark, CommonCore.DICT_ARRAY));
            column.setIsTree(StrUtil.contains(columnRemark, CommonCore.DICT_TREE));
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
            String zipFile = System.currentTimeMillis() + Params.SUFFIX;
            FileUtil.compress(Generator.TEMP_PATH + "src", zipFile);
            // 下载
            WebUtil.download(zipFile, name + Params.SUFFIX, true, response);
            // 删除临时目录
            FileSystemUtils.deleteRecursively(new File(Generator.TEMP_PATH));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void createMenu(GenerateAo generateAo, String className, String tableName) {
        className = StrUtil.lowerFirst(className);
        String isCreateMenuNew = Optional.ofNullable(generateAo.getIsCreateMenu()).filter(StrUtil::isNotBlank).orElse(Dict.YES_NO_0);
        String isCreateDirNew = Optional.ofNullable(generateAo.getIsCreateDir()).filter(StrUtil::isNotBlank).orElse(Dict.YES_NO_0);
        if (Dict.YES_NO_1.equals(isCreateDirNew)) {
            MenuAo dir = new MenuAo();
            setDir(dir, generateAo);
            Menu dirMenu = menuService.createMenu(dir);
            MenuAo menu = new MenuAo();
            setMenu(menu, generateAo, tableName, className, dirMenu.getMenuCode());
            addMenuAndButton(menu, className);
        } else if (Dict.YES_NO_1.equals(isCreateMenuNew)) {
            MenuAo menu = new MenuAo();
            setMenu(menu, generateAo, tableName, className, generateAo.getMenuCode());
            addMenuAndButton(menu, className);
        }
    }

    private void addMenuAndButton(MenuAo menuAo, String className) {
        Menu parentMenu = menuService.createMenu(menuAo);
        for (int i = 0; i < Params.AUTHS.length; i++) {
            String auth = Params.AUTHS[i];
            String parentMenuCode = parentMenu.getMenuCode();
            String menuCode = parentMenuCode + StrUtil.UNDERLINE + auth.toUpperCase(Locale.ROOT);
            MenuAo button = new MenuAo();
            button.setMenuCode(menuCode);
            button.setMenuName(Params.BUTTON_TEXT[i]);
            button.setParentCode(parentMenuCode);
            button.setType(Dict.MENU_TYPE_1);
            button.setPerms(className + StrUtil.COLON + auth);
            menuService.createMenu(button);
        }
    }

    private void setDir(MenuAo dir, GenerateAo generateAo) {
        String menuCode = StrUtil.removePrefix(StrUtil.replace(generateAo.getDirPath(), StrUtil.SLASH, StrUtil.UNDERLINE), StrUtil.UNDERLINE).toUpperCase(Locale.ROOT);
        dir.setMenuCode(menuCode);
        dir.setMenuName(generateAo.getDirName());
        dir.setType(Dict.MENU_TYPE_2);
        dir.setIcon(Params.DEFAULT_MENU_ICON);
        dir.setPath(generateAo.getDirPath());
        dir.setHideMenu(Dict.YES_NO_0);
        dir.setHideBreadcrumb(Dict.YES_NO_0);
        dir.setHideChildrenInMenu(Dict.YES_NO_0);
    }

    private void setMenu(MenuAo menuAo, GenerateAo generateAo, String tableName, String className, String parentCode) {
        String menuCode = parentCode + StrUtil.UNDERLINE + tableName.toUpperCase(Locale.ROOT);
        menuAo.setMenuCode(menuCode);
        menuAo.setMenuName(Optional.ofNullable(generateAo.getMenuName()).filter(StrUtil::isNotBlank).orElse(generateAo.getRemark()));
        menuAo.setKeepAliveName(StrUtil.upperFirst(className));
        menuAo.setParentCode(parentCode);
        menuAo.setType(Dict.MENU_TYPE_0);
        menuAo.setIcon(Params.DEFAULT_MENU_ICON);
        menuAo.setPath(Optional.ofNullable(generateAo.getMenuPath()).filter(StrUtil::isNotBlank).orElse(className));
        menuAo.setComponent(generateAo.getMenuComponent());
        menuAo.setIgnoreKeepAlive(Dict.YES_NO_0);
        menuAo.setHideMenu(Dict.YES_NO_0);
        menuAo.setHideBreadcrumb(Dict.YES_NO_0);
        menuAo.setHideChildrenInMenu(Dict.YES_NO_0);
    }
}
