package ${basePackage}.${excelVoPackage};

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

<#if hasDate = true>
import java.time.LocalDate;
import java.time.LocalDateTime;
</#if>
<#if hasBigDecimal = true>
import java.math.BigDecimal;
</#if>

/**
 * ${tableComment} ExcelVo
 *
 * @author ${author}
 * @since ${date}
 */
@Data
public class ${className}ExcelVo {

<#if columns??>
    <#list columns as column>
    /**
     * ${column.remark}
     */
    @ExcelProperty(value = "${column.remark}")
    <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
    || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
    || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char' || column.type = 'json')>
    private String ${column.field?uncap_first};
    </#if>
    <#if column.type = 'timestamp' || column.type = 'TIMESTAMP'>
    private Long ${column.field?uncap_first};
    </#if>
    <#if column.type = 'date' || column.type = 'DATE'>
    private LocalDate ${column.field?uncap_first};
    </#if>
    <#if column.type = 'datetime' || column.type = 'DATETIME'>
    private LocalDateTime ${column.field?uncap_first};
    </#if>
    <#if column.type = 'int' || column.type = 'smallint'>
    private Integer ${column.field?uncap_first};
    </#if>
    <#if column.type = 'double'>
    private Double ${column.field?uncap_first};
    </#if>
    <#if column.type = 'bigint'>
    private Long ${column.field?uncap_first};
    </#if>
    <#if column.type = 'tinyint'>
    private Byte ${column.field?uncap_first};
    </#if>
    <#if column.type = 'decimal' || column.type = 'numeric'>
    private BigDecimal ${column.field?uncap_first};
    </#if>
    <#if column.hasDict = true && column.isArray = false>

    public void set${column.field?cap_first}(String ${column.field?uncap_first}) {
        this.${column.field?uncap_first} = DictCacheUtil.getDictTitle("${column.remarkDict}", ${column.field?uncap_first});
    }
    </#if>
    <#if column.hasDict = true && column.isArray = true>
    
    public void set${column.field?cap_first}(String ${column.field?uncap_first}) {
        this.${column.field?uncap_first} = DictCacheUtil.getDictTitleArray("${column.remarkDict}", ${column.field?uncap_first});
    }
    </#if>

    </#list>
</#if>

}