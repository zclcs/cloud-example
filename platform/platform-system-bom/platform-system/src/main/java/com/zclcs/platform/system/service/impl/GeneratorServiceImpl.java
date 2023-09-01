package com.zclcs.platform.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
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
import com.zclcs.platform.system.api.bean.ao.GenerateAo;
import com.zclcs.platform.system.api.bean.ao.MenuAo;
import com.zclcs.platform.system.api.bean.entity.ColumnInfo;
import com.zclcs.platform.system.api.bean.entity.Menu;
import com.zclcs.platform.system.api.bean.entity.TableInfo;
import com.zclcs.platform.system.api.bean.vo.GeneratorConfigVo;
import com.zclcs.platform.system.mapper.ColumnInfoMapper;
import com.zclcs.platform.system.mapper.GeneratorMapper;
import com.zclcs.platform.system.mapper.TableInfoMapper;
import com.zclcs.platform.system.service.GeneratorConfigService;
import com.zclcs.platform.system.service.GeneratorService;
import com.zclcs.platform.system.service.MenuService;
import com.zclcs.platform.system.utils.GeneratorUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.mybatisflex.core.query.QueryMethods.case_;
import static com.zclcs.platform.system.api.bean.entity.table.ColumnInfoTableDef.COLUMN_INFO;
import static com.zclcs.platform.system.api.bean.entity.table.TableInfoTableDef.TABLE_INFO;

/**
 * @author zclcs
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    private final GeneratorMapper generatorMapper;
    private final TableInfoMapper tableInfoMapper;
    private final ColumnInfoMapper columnInfoMapper;
    private final GeneratorConfigService generatorConfigService;
    private final MenuService menuService;

    @Override
    public List<String> getDatabases(String databaseType) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(TABLE_INFO.DATASOURCE)
                .groupBy(TABLE_INFO.DATASOURCE)
                .where(TABLE_INFO.DATASOURCE.notIn("information_schema", "mysql", "sys", "performance_schema"))
        ;
        return tableInfoMapper.selectListByQueryAs(queryWrapper, String.class);
    }

    @Override
    public BasePage<TableInfo> getTables(String tableName, BasePageAo request, String databaseType, String schemaName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        TABLE_INFO.CREATE_AT,
                        TABLE_INFO.UPDATE_AT,
                        TABLE_INFO.DATA_ROWS,
                        TABLE_INFO.DATASOURCE,
                        TABLE_INFO.NAME,
                        TABLE_INFO.REMARK,
                        TABLE_INFO.TABLE_COLLATION
                )
                .where(TABLE_INFO.DATASOURCE.notIn("information_schema", "mysql", "sys", "performance_schema"))
                .and(TABLE_INFO.DATASOURCE.eq(schemaName, If::hasText))
                .and(TABLE_INFO.NAME.eq(tableName, If::hasText))
                .orderBy(TABLE_INFO.CREATE_AT.desc(), TABLE_INFO.UPDATE_AT.desc())
        ;
        Page<TableInfo> paginate = tableInfoMapper.paginate(request.getPageNum(), request.getPageSize(), queryWrapper);
        return new BasePage<>(paginate);
    }

    @Override
    public List<ColumnInfo> getColumns(String databaseType, String schemaName, String tableName, List<String> excludeColumns) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                        COLUMN_INFO.NAME,
                        COLUMN_INFO.KEY,
                        case_()
                                .when(COLUMN_INFO.KEY.eq("PRI")).then("true")
                                .else_("false")
                                .end().as("isKey"),
                        COLUMN_INFO.NULLABLE,
                        case_()
                                .when(COLUMN_INFO.NULLABLE.eq("YES")).then("true")
                                .else_("false")
                                .end().as("isNullable"),
                        COLUMN_INFO.TYPE,
                        case_()
                                .when(COLUMN_INFO.CHAR_MAX_LENGTH.isNotNull()).then("true")
                                .else_("false")
                                .end().as("isCharMaxLength"),
                        COLUMN_INFO.CHAR_MAX_LENGTH,
                        COLUMN_INFO.REMARK,
                        case_()
                                .when(COLUMN_INFO.DEFAULT_VALUE.isNull()).then("")
                                .else_(COLUMN_INFO.DEFAULT_VALUE)
                                .end().as("COLUMN_DEFAULT")
                )
                .where(COLUMN_INFO.TABLE_SCHEMA.eq(schemaName, If::hasText))
                .and(COLUMN_INFO.TABLE_NAME.eq(tableName, If::hasText))
                .and(COLUMN_INFO.NAME.notIn(excludeColumns, If::isNotEmpty))
        ;
        return columnInfoMapper.selectListByQuery(queryWrapper);
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
        if (Dict.YES_NO_1.equals(generatorConfigVo.getIsTrim())) {
            tableName = StrUtil.removePrefix(name, generatorConfigVo.getTrimValue());
            className = tableName;
        }
        String underscoreToCamel = BaseUtil.underscoreToCamel(className);
        createMenu(generateAo, underscoreToCamel, tableName);
        generatorConfigVo.setTableName(name);
        generatorConfigVo.setClassName(underscoreToCamel);
        generatorConfigVo.setTableComment(remark);
        // 生成代码到临时目录
        List<ColumnInfo> columnInfos = this.getColumns(Generator.DATABASE_TYPE, generateAo.getDatasource(), name, StrUtil.split(generatorConfigVo.getExcludeColumns(), StrUtil.COMMA));
        for (ColumnInfo columnInfo : columnInfos) {
            if (columnInfo.getIsKey()) {
                generatorConfigVo.setKeyName(columnInfo.getName());
            }
            String columnRemark = columnInfo.getRemark();
            if (StrUtil.contains(columnRemark, CommonCore.DICT_REMARK)) {
                columnInfo.setHasDict(true);
                List<String> strings = StrUtil.splitTrim(columnRemark, CommonCore.DICT_REMARK);
                columnInfo.setRemarkDict(strings.get(strings.size() - 1));
            } else {
                columnInfo.setHasDict(false);
            }
            columnInfo.setIsArray(StrUtil.contains(columnRemark, CommonCore.DICT_ARRAY));
            columnInfo.setIsTree(StrUtil.contains(columnRemark, CommonCore.DICT_TREE));
        }
        try {
            GeneratorUtil.generateEntityFile(columnInfos, generatorConfigVo);
            GeneratorUtil.generateAoFile(columnInfos, generatorConfigVo);
            GeneratorUtil.generateVoFile(columnInfos, generatorConfigVo);
            GeneratorUtil.generateMapperFile(columnInfos, generatorConfigVo);
            GeneratorUtil.generateMapperXmlFile(columnInfos, generatorConfigVo);
            GeneratorUtil.generateServiceFile(columnInfos, generatorConfigVo);
            GeneratorUtil.generateServiceImplFile(columnInfos, generatorConfigVo);
            GeneratorUtil.generateControllerFile(columnInfos, generatorConfigVo);
            if ("02".equals(generatorConfigVo.getGenVersion())) {
                GeneratorUtil.generateCacheVoFile(columnInfos, generatorConfigVo);
                GeneratorUtil.generateExcelVoFile(columnInfos, generatorConfigVo);
            }
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
