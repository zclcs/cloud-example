package com.zclcs.test.test.service.impl;

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
import com.zclcs.test.test.api.bean.ao.CompanyAo;
import com.zclcs.test.test.api.bean.entity.Company;
import com.zclcs.test.test.api.bean.excel.CompanyExcelVo;
import com.zclcs.test.test.api.bean.vo.CompanyVo;
import com.zclcs.test.test.mapper.CompanyMapper;
import com.zclcs.test.test.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 企业信息 Service实现
 *
 * @author zclcs
 * @since 2023-09-04 17:20:33.823
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Override
    public BasePage<CompanyVo> findCompanyPage(BasePageAo basePageAo, CompanyVo companyVo) {
        QueryWrapper queryWrapper = getQueryWrapper(companyVo);
        Page<CompanyVo> page = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, CompanyVo.class);
        return new BasePage<>(page);
    }

    @Override
    public List<CompanyVo> findCompanyList(CompanyVo companyVo) {
        QueryWrapper queryWrapper = getQueryWrapper(companyVo);
        return this.mapper.selectListByQueryAs(queryWrapper, CompanyVo.class);
    }

    @Override
    public CompanyVo findCompany(CompanyVo companyVo) {
        QueryWrapper queryWrapper = getQueryWrapper(companyVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, CompanyVo.class);
    }

    @Override
    public Long countCompany(CompanyVo companyVo) {
        QueryWrapper queryWrapper = getQueryWrapper(companyVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(CompanyVo companyVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(

        );
        // TODO 设置公共查询条件
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Company createCompany(CompanyAo companyAo) {
        Company company = new Company();
        BeanUtil.copyProperties(companyAo, company);
        this.save(company);
        return company;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Company updateCompany(CompanyAo companyAo) {
        Company company = new Company();
        BeanUtil.copyProperties(companyAo, company);
        this.updateById(company);
        return company;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCompany(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public void exportExcel(CompanyVo companyVo) {
        SimpleExportListener<CompanyVo, CompanyExcelVo> simpleExportListener = new SimpleExportListener<>(new ExportExcelService<>() {
            @Override
            public Long count(CompanyVo companyVo) {
                return countCompany(companyVo);
            }

            @Override
            public List<CompanyExcelVo> getDataPaginateAs(CompanyVo companyVo, Long pageNum, Long pageSize, Long totalRows) {
                QueryWrapper queryWrapper = getQueryWrapper(companyVo);
                Page<CompanyExcelVo> excelVoPage = mapper.paginateAs(pageNum, pageSize, totalRows, queryWrapper, CompanyExcelVo.class);
                return excelVoPage.getRecords();
            }
        });
    }

    @SneakyThrows
    @Override
    public void importExcel(MultipartFile multipartFile) {
        SimpleImportListener<Company> simpleImportListener = new SimpleImportListener<>(new ImportExcelService<>() {

            @Override
            public Company toBean(Map<String, String> cellData) {
                Company company = new Company();
                company.setCompanyId(Long.valueOf(cellData.get("companyId")));
                company.setCompanyCode(cellData.get("companyCode"));
                company.setCompanyAttachment(cellData.get("companyAttachment"));
                company.setCompanyName(cellData.get("companyName"));
                company.setCompanyType(cellData.get("companyType"));
                company.setLicenseNum(cellData.get("licenseNum"));
                company.setAreaCode(cellData.get("areaCode"));
                company.setAddress(cellData.get("address"));
                company.setZipCode(cellData.get("zipCode"));
                company.setLegalMan(cellData.get("legalMan"));
                company.setLegalManPhone(cellData.get("legalManPhone"));
                company.setLegalManDuty(cellData.get("legalManDuty"));
                company.setLegalManProTitle(cellData.get("legalManProTitle"));
                company.setLegalManIdCardType(cellData.get("legalManIdCardType"));
                company.setLegalManIdCardNumber(cellData.get("legalManIdCardNumber"));
                company.setRegCapital(cellData.get("regCapital"));
                company.setFactRegCapital(cellData.get("factRegCapital"));
                company.setCapitalCurrencyType(cellData.get("capitalCurrencyType"));
                company.setRegisterDate(LocalDate.parse(cellData.get("registerDate"), DatePattern.NORM_DATE_FORMATTER));
                company.setEstablishDate(LocalDate.parse(cellData.get("establishDate"), DatePattern.NORM_DATE_FORMATTER));
                company.setOfficePhone(cellData.get("officePhone"));
                company.setFaxNumber(cellData.get("faxNumber"));
                company.setLinkMan(cellData.get("linkMan"));
                company.setLinkDuty(cellData.get("linkDuty"));
                company.setLinkPhone(cellData.get("linkPhone"));
                company.setEmail(cellData.get("email"));
                company.setWebSite(cellData.get("webSite"));
                company.setRemark(cellData.get("remark"));
                return company;
            }

            @Override
            public void saveBeans(List<Company> t) {
                saveBatch(t);
            }
        }, CompanyExcelVo.class.getDeclaredFields());
        EasyExcel.read(multipartFile.getInputStream(), simpleImportListener).sheet().doRead();
        Map<Integer, CellData<?>> error = simpleImportListener.getError();
        if (CollectionUtil.isNotEmpty(error)) {
            throw new ExcelReadException("导入发生错误", error);
        }
    }
}
