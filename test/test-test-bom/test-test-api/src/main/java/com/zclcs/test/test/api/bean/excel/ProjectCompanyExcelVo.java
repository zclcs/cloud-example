package com.zclcs.test.test.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;


/**
 * 项目参建单位信息数据 ExcelVo
 *
 * @author zclcs
 * @since 2023-09-04 20:04:43.968
 */
@Data
public class ProjectCompanyExcelVo {

    /**
     * 参建单位id
     */
    @ExcelProperty(value = "参建单位id")
    private Long projectCompanyId;

    /**
     * 项目id
     */
    @ExcelProperty(value = "项目id")
    private Long projectId;

    /**
     * 企业id
     */
    @ExcelProperty(value = "企业id")
    private Long companyId;

    /**
     * 参建类型 @@company_role
     */
    @ExcelProperty(value = "参建类型 @@company_role")
    private String companyRole;

    public void setCompanyRole(String companyRole) {
        this.companyRole = DictCacheUtil.getDictTitle("company_role", companyRole);
    }

    /**
     * 项目经理姓名
     */
    @ExcelProperty(value = "项目经理姓名")
    private String managerName;

    /**
     * 项目经理证件类型 @@id_card_type
     */
    @ExcelProperty(value = "项目经理证件类型 @@id_card_type")
    private String managerIdCardType;

    public void setManagerIdCardType(String managerIdCardType) {
        this.managerIdCardType = DictCacheUtil.getDictTitle("id_card_type", managerIdCardType);
    }

    /**
     * 项目经理证件号码
     */
    @ExcelProperty(value = "项目经理证件号码")
    private String managerIdCardNumber;

    /**
     * 项目经理联系电话
     */
    @ExcelProperty(value = "项目经理联系电话")
    private String managerPhone;


}