package com.zclcs.test.test.service;

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.test.test.api.bean.ao.CompanyAo;
import com.zclcs.test.test.api.bean.entity.Company;
import com.zclcs.test.test.api.bean.vo.CompanyVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 企业信息 Service接口
 *
 * @author zclcs
 * @since 2023-09-08 15:00:09.827
 */
public interface CompanyService extends IService<Company> {

    /**
     * 查询（分页）
     *
     * @param basePageAo {@link BasePageAo}
     * @param companyVo {@link CompanyVo}
     * @return {@link CompanyVo}
     */
    BasePage<CompanyVo> findCompanyPage(BasePageAo basePageAo, CompanyVo companyVo);

    /**
     * 查询（所有）
     *
     * @param companyVo {@link CompanyVo}
     * @return {@link CompanyVo}
     */
    List<CompanyVo> findCompanyList(CompanyVo companyVo);

    /**
     * 查询（单个）
     *
     * @param companyVo {@link CompanyVo}
     * @return {@link CompanyVo}
     */
    CompanyVo findCompany(CompanyVo companyVo);

    /**
     * 统计
     *
     * @param companyVo {@link CompanyVo}
     * @return 统计值
     */
    Long countCompany(CompanyVo companyVo);

    /**
     * 新增
     *
     * @param companyAo {@link CompanyAo}
     * @return {@link Company}
     */
    Company createCompany(CompanyAo companyAo);

    /**
     * 修改
     *
     * @param companyAo {@link CompanyAo}
     * @return {@link Company}
     */
    Company updateCompany(CompanyAo companyAo);

    /**
     * 新增或修改
     *
     * @param companyAo {@link CompanyAo}
     * @return {@link Company}
     */
    Company createOrUpdateCompany(CompanyAo companyAo);

    /**
     * 批量新增
     *
     * @param companyAos {@link CompanyAo}
     * @return {@link Company}
     */
    List<Company> createCompanyBatch(List<CompanyAo> companyAos);

    /**
     * 批量修改
     *
     * @param companyAos {@link CompanyAo}
     * @return {@link Company}
     */
    List<Company> updateCompanyBatch(List<CompanyAo> companyAos);

    /**
     * 批量新增或修改
     * id为空则新增，不为空则修改
     * 可以自行重写
     *
     * @param companyAos {@link CompanyAo}
     * @return {@link Company}
     */
    List<Company> createOrUpdateCompanyBatch(List<CompanyAo> companyAos);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void deleteCompany(List<Long> ids);

    /**
     * excel导出
     *
     * @param companyVo {@link CompanyVo}
     */
    void exportExcel(CompanyVo companyVo);

    /**
     * excel导入
     *
     * @param multipartFile excel文件
     */
    void importExcel(MultipartFile multipartFile);

}
