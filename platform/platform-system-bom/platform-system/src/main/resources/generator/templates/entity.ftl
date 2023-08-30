package ${basePackage}.${entityPackage};

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
* ${tableComment} Entity
*
* @author ${author}
* @since ${date}
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("${tableName}")
public class ${className} extends BaseEntity implements Serializable {

@Serial
private static final long serialVersionUID = 1L;

<#if columns??>
    <#list columns as column>
        /**
        * ${column.remark}
        */
        <#if column.isKey = true>
            @Id(value = "${column.name}", keyType = KeyType.Auto)
        <#else>
            @Column("${column.name}")
        </#if>
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

    </#list>
</#if>

}