package ${basePackage}.${serviceImplPackage};

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.data.CellData;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.common.export.excel.starter.kit.ExcelReadException;
import com.zclcs.common.export.excel.starter.listener.SimpleExportListener;
import com.zclcs.common.export.excel.starter.listener.SimpleImportListener;
import com.zclcs.common.export.excel.starter.service.ExportExcelService;
import com.zclcs.common.export.excel.starter.service.ImportExcelService;
import ${basePackage}.${aoPackage}.${className}Ao;
import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${voPackage}.${className}Vo;
import ${basePackage}.${mapperPackage}.${className}Mapper;
import ${basePackage}.${servicePackage}.${className}Service;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static ${basePackage}.${entityPackage}.table.${className}TableDef.${className.upper_case};

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
                ${classNameUpperCase}.${column.fieldUpperCase}
                <#if column_has_next>,</#if>
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
        });
    }

    @SneakyThrows
    @Override
    public void importExcel(MultipartFile multipartFile) {
        SimpleImportListener<${className}> simpleImportListener = new SimpleImportListener<>(new ImportExcelService<>() {

            @Override
            public ${className} toBean(Map<String, String> cellData) {
                ${className} ${className?uncap_first} = new ${className}();
                <#if columns??>
                <#list columns as column>
                <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
                || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
                || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char' || column.type = 'json')>
                ${className?uncap_first}.set${column.field?cap_first}(cellData.get("${column.field?uncap_first}"));
                </#if>
                <#if column.type = 'timestamp' || column.type = 'TIMESTAMP'>
                ${className?uncap_first}.set${column.field?cap_first}(Long.valueOf(cellData.get("${column.field?uncap_first}")));
                </#if>
                <#if column.type = 'date' || column.type = 'DATE'>
                ${className?uncap_first}.set${column.field?cap_first}(LocalDate.parse(cellData.get("${column.field?uncap_first}"), DatePattern.NORM_DATE_FORMATTER));
                </#if>
                <#if column.type = 'datetime' || column.type = 'DATETIME'>
                ${className?uncap_first}.set${column.field?cap_first}(LocalDateTime.parse(cellData.get("${column.field?uncap_first}"), DatePattern.NORM_DATETIME_FORMATTER));
                </#if>
                <#if column.type = 'int' || column.type = 'smallint'>
                ${className?uncap_first}.set${column.field?cap_first}(Integer.valueOf(cellData.get("${column.field?uncap_first}")));
                </#if>
                <#if column.type = 'double'>
                ${className?uncap_first}.set${column.field?cap_first}(Double.valueOf(cellData.get("${column.field?uncap_first}")));
                </#if>
                <#if column.type = 'bigint'>
                ${className?uncap_first}.set${column.field?cap_first}(Long.valueOf(cellData.get("${column.field?uncap_first}")));
                </#if>
                <#if column.type = 'tinyint'>
                ${className?uncap_first}.set${column.field?cap_first}(Byte.valueOf(cellData.get("${column.field?uncap_first}")));
                </#if>
                <#if column.type = 'decimal' || column.type = 'numeric'>
                ${className?uncap_first}.set${column.field?cap_first}(new BigDecimal(cellData.get("${column.field?uncap_first}")));
                </#if>
                </#list>
                </#if>
                return ${className?uncap_first};
            }

            @Override
            public void saveBeans(List<${className}> t) {
                saveBatch(t);
            }
        }, ${className}ExcelVo.class.getDeclaredFields());
        EasyExcel.read(multipartFile.getInputStream(), simpleImportListener).sheet().doRead();
        Map<Integer, CellData<?>> error = simpleImportListener.getError();
        if (CollectionUtil.isNotEmpty(error)) {
            throw new ExcelReadException("导入发生错误", error);
        }
    }
}
