package ${basePackage}.${entityPackage};

import base.com.zclcs.common.core.BaseEntity;
import annotation.json.com.zclcs.common.dict.DictText;
import annotation.json.com.zclcs.common.dict.Array;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
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
 * ${tableComment} Vo
 *
 * @author ${author}
 * @date ${date}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "${className}Vo对象", description = "${tableComment}")
public class ${className}Vo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

<#if columns??>
    <#list columns as column>
    @Schema(title = "${column.remark}")
    <#if column.hasDict = true && column.isArray = true && column.isTree = false>
    @DictText(value = "${column.remarkDict}", array = @Array)
    </#if>
    <#if column.hasDict = true && column.isArray = false && column.isTree = false>
    @DictText(value = "${column.remarkDict}")
    </#if>
    <#if column.hasDict = true && column.isArray = false && column.isTree = true>
    @DictText(value = "${column.remarkDict}", tree = true)
    </#if>
    <#if column.hasDict = true && column.isArray = true && column.isTree = true>
    @DictText(value = "${column.remarkDict}", tree = true, array = @Array(toText = false))
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