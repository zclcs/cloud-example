package ${basePackage}.${excelVoPackage};

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.annotation.DictExcelValid;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import com.zclcs.cloud.lib.excel.handler.DynamicSelectAreaHandler;
import com.zclcs.cloud.lib.excel.handler.DynamicSelectCityHandler;
import com.zclcs.cloud.lib.excel.handler.DynamicSelectDictHandler;
import com.zclcs.cloud.lib.excel.handler.DynamicSelectProvinceHandler;
import com.zclcs.common.export.excel.starter.annotation.ExcelSelect;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    <#if column.isTree = false>
    /**
     * ${column.remark}
     */
    </#if>
    <#if column.hasDict = true && column.isTree = false>
    @DictExcelValid(value = "${column.remarkDict}")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "${column.remarkDict}")
    </#if>
    <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
    || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
    || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char' || column.type = 'json') && column.isCharMaxLength = true && column.hasDict = false>
    @Size(max = ${column.charMaxLength}, message = "{noMoreThan}")
    </#if>
    <#if column.isKey = true>
    @NotNull(message = "{required}")
    </#if>
    <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
    || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
    || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char' || column.type = 'json')>
    <#if column.isNullable = false && column.isKey = false && column.isTree = false>
    @NotBlank(message = "{required}")
    </#if>
    <#else>
    <#if column.isNullable = false && column.isKey = false && column.isTree = false>
    @NotNull(message = "{required}")
    </#if>
    </#if>
    <#if column.hasDict = true && column.isTree = false>
    @ExcelProperty(value = "${column.remarkSpiltDict}")
    </#if>
    <#if column.hasDict = false && column.isTree = false>
    @ExcelProperty(value = "${column.remark}")
    </#if>
    <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
    || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
    || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char' || column.type = 'json') && column.isTree = true && column.field = 'AreaCode'>
    /**
     * 省
     */
    <#if column.isNullable = true>
    @NotBlank(message = "{required}")
    </#if>
    @ExcelSelect(handler = DynamicSelectProvinceHandler.class, parameter = "area_code")
    @ExcelProperty("省")
    private String province;

    /**
     * 市
     */
    <#if column.isNullable = true>
    @NotBlank(message = "{required}")
    </#if>
    @ExcelSelect(parentColumn = "省", handler = DynamicSelectCityHandler.class, parameter = "area_code")
    @ExcelProperty("市")
    private String city;
    
    /**
     * 区/县
     */
    <#if column.isNullable = true>
    @NotBlank(message = "{required}")
    </#if>
    @ExcelSelect(parentColumn = "市", handler = DynamicSelectAreaHandler.class, parameter = "area_code")
    @ExcelProperty(value = "区/县")
    private String areaCode;

    public void setAreaCode(String areaCode) {
        DictItemCacheVo area = DictCacheUtil.getDict("area_code", areaCode);
        if (area != null) {
            this.areaCode = area.getTitle();
            DictItemCacheVo city = DictCacheUtil.getDict("area_code", area.getParentValue());
            if (city != null) {
                this.city = city.getTitle();
                DictItemCacheVo province = DictCacheUtil.getDict("area_code", city.getParentValue());
                if (province != null) {
                    this.province = province.getTitle();
                }
            }
        }
    }
    </#if>
    <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
    || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
    || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char' || column.type = 'json') && column.isTree = false>
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
    <#if column.hasDict = true && column.isTree = false>
    
    public void set${column.field?cap_first}(String ${column.field?uncap_first}) {
        this.${column.field?uncap_first} = DictCacheUtil.getDictTitle("${column.remarkDict}", ${column.field?uncap_first});
    }
    </#if>

    </#list>
</#if>

}