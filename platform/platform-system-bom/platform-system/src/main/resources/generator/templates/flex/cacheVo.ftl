package ${basePackage}.${cacheVoPackage};

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
<#if hasDate = true>
import java.time.LocalDate;
import java.time.LocalDateTime;
</#if>
<#if hasBigDecimal = true>
import java.math.BigDecimal;
</#if>

/**
* ${tableComment} CacheVo
*
* @author ${author}
* @since ${date}
*/
@Data
public class ${className}CacheVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

<#if columns??>
    <#list columns as column>
    /**
    * ${column.remark}
    */
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

    /**
    * ${column.remark}
    */
    <#if column.hasDict = true && column.isArray = false>
    private String ${column.field?uncap_first}Text;

    public String get${column.field?cap_first}Text() {
        return DictCacheUtil.getDictTitle("${column.remarkDict}", this.${column.field?uncap_first});
    }
    </#if>
    <#if column.hasDict = true && column.isArray = true>
    private String ${column.field?uncap_first}Text;

    public String get${column.field?cap_first}Text() {
        return DictCacheUtil.getDictTitleArray("${column.remarkDict}", this.${column.field?uncap_first});
    }
    </#if>

    </#list>
</#if>

}