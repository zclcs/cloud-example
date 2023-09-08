package com.zclcs.test.test.api.bean.excel;

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


/**
 * 项目参建单位信息数据 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-08 16:48:43.853
 */
@Data
public class ProjectCompanyExcelVo {

    /**
     * 参建单位id
     */
    @NotNull(message = "{required}")
    @ExcelProperty(value = "参建单位id")
    private Long projectCompanyId;

    /**
     * 项目id
     */
    @NotNull(message = "{required}")
    @ExcelProperty(value = "项目id")
    private Long projectId;

    /**
     * 企业id
     */
    @NotNull(message = "{required}")
    @ExcelProperty(value = "企业id")
    private Long companyId;

    /**
     * 参建类型@@company_role
     */
    @DictExcelValid(value = "company_role")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "company_role")
    @NotBlank(message = "{required}")
    @ExcelProperty(value = "参建类型")
    private String companyRole;
    
    public void setCompanyRole(String companyRole) {
        this.companyRole = DictCacheUtil.getDictTitle("company_role", companyRole);
    }

    /**
     * 项目经理姓名
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "项目经理姓名")
    private String managerName;

    /**
     * 项目经理证件类型@@id_card_type
     */
    @DictExcelValid(value = "id_card_type")
    @ExcelSelect(handler = DynamicSelectDictHandler.class, parameter = "id_card_type")
    @ExcelProperty(value = "项目经理证件类型")
    private String managerIdCardType;
    
    public void setManagerIdCardType(String managerIdCardType) {
        this.managerIdCardType = DictCacheUtil.getDictTitle("id_card_type", managerIdCardType);
    }

    /**
     * 项目经理证件号码
     */
    @Size(max = 30, message = "{noMoreThan}")
    @ExcelProperty(value = "项目经理证件号码")
    private String managerIdCardNumber;

    /**
     * 项目经理联系电话
     */
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelProperty(value = "项目经理联系电话")
    private String managerPhone;


}