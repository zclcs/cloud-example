package ${basePackage}.${serviceImplPackage};

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.exception.FieldException;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import com.zclcs.common.export.excel.starter.kit.ExcelReadException;
import com.zclcs.common.export.excel.starter.listener.SimpleExportListener;
import com.zclcs.common.export.excel.starter.listener.SimpleImportListener;
import com.zclcs.common.export.excel.starter.service.ExportExcelService;
import com.zclcs.common.export.excel.starter.service.ImportExcelService;
import com.zclcs.common.web.starter.utils.WebUtil;
import ${basePackage}.${aoPackage}.${className}Ao;
import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${excelVoPackage}.${className}ExcelVo;
import ${basePackage}.${voPackage}.${className}Vo;
import ${basePackage}.${mapperPackage}.${className}Mapper;
import ${basePackage}.${servicePackage}.${className}Service;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

<#if hasDate = true>
import java.time.LocalDate;
import java.time.LocalDateTime;
</#if>
<#if hasBigDecimal = true>
import java.math.BigDecimal;
</#if>
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ${basePackage}.${entityPackage}.table.${className}TableDef.${classNameUpperCase};

/**
 * ${tableComment} Service实现
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ${className}ServiceImpl extends ServiceImpl<${className}Mapper, ${className}> implements ${className}Service {

    @Override
    public BasePage<${className}Vo> find${className}Page(BasePageAo basePageAo, ${className}Vo ${className?uncap_first}Vo) {
        QueryWrapper queryWrapper = getQueryWrapper(${className?uncap_first}Vo);
        Page<${className}Vo> page = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, ${className}Vo.class);
        return new BasePage<>(page);
    }

    @Override
    public List<${className}Vo> find${className}List(${className}Vo ${className?uncap_first}Vo) {
        QueryWrapper queryWrapper = getQueryWrapper(${className?uncap_first}Vo);
        return this.mapper.selectListByQueryAs(queryWrapper, ${className}Vo.class);
    }

    @Override
    public ${className}Vo find${className}(${className}Vo ${className?uncap_first}Vo) {
        QueryWrapper queryWrapper = getQueryWrapper(${className?uncap_first}Vo);
        return this.mapper.selectOneByQueryAs(queryWrapper, ${className}Vo.class);
    }

    @Override
    public Long count${className}(${className}Vo ${className?uncap_first}Vo) {
    QueryWrapper queryWrapper = getQueryWrapper(${className?uncap_first}Vo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(${className}Vo ${className?uncap_first}Vo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                <#if columns??>
                <#list columns as column>
                ${classNameUpperCase}.${column.fieldUpperCase}<#if column_has_next>,</#if>
                </#list>
                </#if>
        );
        // TODO 设置公共查询条件
        return queryWrapper;
   }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${className} create${className}(${className}Ao ${className?uncap_first}Ao) {
        ${className} ${className?uncap_first} = new ${className}();
        BeanUtil.copyProperties(${className?uncap_first}Ao, ${className?uncap_first});
        this.save(${className?uncap_first});
        return ${className?uncap_first};
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${className} update${className}(${className}Ao ${className?uncap_first}Ao) {
        ${className} ${className?uncap_first} = new ${className}();
        BeanUtil.copyProperties(${className?uncap_first}Ao, ${className?uncap_first});
        this.updateById(${className?uncap_first});
        return ${className?uncap_first};
    }

    @Override
    public ${className} createOrUpdate${className}(${className}Ao ${className?uncap_first}Ao) {
        ${className} ${className?uncap_first} = new ${className}();
        BeanUtil.copyProperties(${className?uncap_first}Ao, ${className?uncap_first});
        this.saveOrUpdate(${className?uncap_first});
        return ${className?uncap_first};
    }

    @Override
    public List<${className}> create${className}Batch(List<${className}Ao> ${className?uncap_first}Aos) {
        List<${className}> ${className?uncap_first}List = new ArrayList<>();
        for (${className}Ao ${className?uncap_first}Ao : ${className?uncap_first}Aos) {
            ${className} ${className?uncap_first} = new ${className}();
            BeanUtil.copyProperties(${className?uncap_first}Ao, ${className?uncap_first});
            ${className?uncap_first}List.add(${className?uncap_first});
        }
        saveBatch(${className?uncap_first}List);
        return ${className?uncap_first}List;
    }

    @Override
    public List<${className}> update${className}Batch(List<${className}Ao> ${className?uncap_first}Aos) {
        List<${className}> ${className?uncap_first}List = new ArrayList<>();
        for (${className}Ao ${className?uncap_first}Ao : ${className?uncap_first}Aos) {
            ${className} ${className?uncap_first} = new ${className}();
            BeanUtil.copyProperties(${className?uncap_first}Ao, ${className?uncap_first});
            ${className?uncap_first}List.add(${className?uncap_first});
        }
        updateBatch(${className?uncap_first}List);
        return ${className?uncap_first}List;
    }

    @Override
    public List<${className}> createOrUpdate${className}Batch(List<${className}Ao> ${className?uncap_first}Aos) {
        List<${className}> ${className?uncap_first}List = new ArrayList<>();
        for (${className}Ao ${className?uncap_first}Ao : ${className?uncap_first}Aos) {
            ${className} ${className?uncap_first} = new ${className}();
            BeanUtil.copyProperties(${className?uncap_first}Ao, ${className?uncap_first});
            ${className?uncap_first}List.add(${className?uncap_first});
        }
        saveOrUpdateBatch(${className?uncap_first}List);
        return ${className?uncap_first}List;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete${className}(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public void exportExcel(${className}Vo ${className?uncap_first}Vo) {
        SimpleExportListener<${className}Vo, ${className}ExcelVo> simpleExportListener = new SimpleExportListener<>(new ExportExcelService<>() {
            @Override
            public Long count(${className}Vo ${className?uncap_first}Vo) {
                return count${className}(${className?uncap_first}Vo);
            }

            @Override
            public List<${className}ExcelVo> getDataPaginateAs(${className}Vo ${className?uncap_first}Vo, Long pageNum, Long pageSize, Long totalRows) {
                QueryWrapper queryWrapper = getQueryWrapper(${className?uncap_first}Vo);
                Page<${className}ExcelVo> excelVoPage = mapper.paginateAs(pageNum, pageSize, totalRows, queryWrapper, ${className}ExcelVo.class);
                return excelVoPage.getRecords();
            }
        }, ${className}ExcelVo.class.getDeclaredFields());
        simpleExportListener.exportWithEntity(WebUtil.getHttpServletResponse(), "${tableComment}", ${className}ExcelVo.class, ${className?uncap_first}Vo);
    }

    @SneakyThrows
    @Override
    public void importExcel(MultipartFile multipartFile) {
        SimpleImportListener<${className}, ${className}ExcelVo> simpleImportListener = new SimpleImportListener<>(new ImportExcelService<>() {

            @Override
            public ${className}ExcelVo toExcelVo(Map<String, String> cellData) {
                ${className}ExcelVo ${className?uncap_first}ExcelVo = new ${className}ExcelVo();
                <#if columns??>
                <#list columns as column>
                <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
                || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
                || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char' || column.type = 'json') && column.isTree = true && column.field = 'AreaCode'>
                ${className?uncap_first}ExcelVo.setAreaCode(DictCacheUtil.validateAreaCode(cellData.get("province"), cellData.get("city"), cellData.get("areaCode")));
                </#if>
                <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
                || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
                || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char' || column.type = 'json') && column.isTree = false>
                ${className?uncap_first}ExcelVo.set${column.field?cap_first}(cellData.get("${column.field?uncap_first}"));
                </#if>
                <#if column.type = 'timestamp' || column.type = 'TIMESTAMP'>
                ${className?uncap_first}ExcelVo.set${column.field?cap_first}(parseLong(cellData.get("${column.field?uncap_first}")));
                </#if>
                <#if column.type = 'date' || column.type = 'DATE'>
                ${className?uncap_first}ExcelVo.set${column.field?cap_first}(parseLocalDate(cellData.get("${column.field?uncap_first}")));
                </#if>
                <#if column.type = 'datetime' || column.type = 'DATETIME'>
                ${className?uncap_first}ExcelVo.set${column.field?cap_first}(parseLocalDateTime(cellData.get("${column.field?uncap_first}")));
                </#if>
                <#if column.type = 'int' || column.type = 'smallint'>
                ${className?uncap_first}ExcelVo.set${column.field?cap_first}(parseInteger(cellData.get("${column.field?uncap_first}")));
                </#if>
                <#if column.type = 'double'>
                ${className?uncap_first}ExcelVo.set${column.field?cap_first}(parseDouble(cellData.get("${column.field?uncap_first}")));
                </#if>
                <#if column.type = 'bigint'>
                ${className?uncap_first}ExcelVo.set${column.field?cap_first}(parseLong(cellData.get("${column.field?uncap_first}")));
                </#if>
                <#if column.type = 'tinyint'>
                ${className?uncap_first}ExcelVo.set${column.field?cap_first}(parseByte(cellData.get("${column.field?uncap_first}")));
                </#if>
                <#if column.type = 'decimal' || column.type = 'numeric'>
                ${className?uncap_first}ExcelVo.set${column.field?cap_first}(parseBigDecimal(cellData.get("${column.field?uncap_first}")));
                </#if>
                </#list>
                </#if>
                return ${className?uncap_first}ExcelVo;
            }

            @Override
            public ${className} toBean(${className}ExcelVo excelVo) {
                ${className} ${className?uncap_first} = new ${className}();
                BeanUtil.copyProperties(excelVo, ${className?uncap_first});
                return ${className?uncap_first};
            }

            @Override
            public void saveBeans(List<${className}> t) {
                saveBatch(t);
            }
        }, ${className}ExcelVo.class.getDeclaredFields());
        EasyExcel.read(multipartFile.getInputStream(), simpleImportListener).sheet().doRead();
        Map<Integer, String> error = simpleImportListener.getError();
        if (CollectionUtil.isNotEmpty(error)) {
            throw new ExcelReadException("导入发生错误", error);
        }
    }
}
