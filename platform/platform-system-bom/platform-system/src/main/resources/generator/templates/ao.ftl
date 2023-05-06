package ${basePackage}.${entityPackage};

import strategy.validate.com.zclcs.cloud.lib.UpdateStrategy;
import annotation.json.com.zclcs.cloud.lib.DictValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
 * ${tableComment} Ao
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
@Schema(title = "${className}Ao对象", description = "${tableComment}")
public class ${className}Ao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

<#if columns??>
    <#list columns as column>
    <#if column.hasDict = true>
    @DictValid(value = "${column.remarkDict}", message = "{dict}")
    </#if>
    <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
    || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
    || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char' || column.type = 'json') && column.isCharMaxLength = true>
    @Size(max = ${column.charMaxLength}, message = "{noMoreThan}")
    </#if>
    <#if column.isKey = true>
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(title = "${column.remark}")
    </#if>
    <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
    || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
    || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char' || column.type = 'json')>
        <#if column.isNullable = false && column.isKey = false>
    @NotBlank(message = "{required}")
    @Schema(title = "${column.remark}", requiredMode = Schema.RequiredMode.REQUIRED)
        </#if>
    <#else>
        <#if column.isNullable = false && column.isKey = false>
    @NotNull(message = "{required}")
    @Schema(title = "${column.remark}", requiredMode = Schema.RequiredMode.REQUIRED)
        </#if>
    </#if>
    <#if column.isNullable = true && column.isKey = false>
    @Schema(title = "${column.remark}")
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