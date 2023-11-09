package com.zclcs.test.test.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.exception.FieldException;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import com.zclcs.common.export.excel.starter.kit.ExcelReadException;
import com.zclcs.common.export.excel.starter.listener.SimpleExportListener;
import com.zclcs.common.export.excel.starter.listener.SimpleImportListener;
import com.zclcs.common.export.excel.starter.service.ExportExcelService;
import com.zclcs.common.export.excel.starter.service.ImportExcelService;
import com.zclcs.common.web.starter.utils.WebUtil;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zclcs.test.test.api.bean.entity.table.CompanyTableDef.COMPANY;

/**
 * 企业信息 Service实现
 *
 * @author zclcs
 * @since 2023-11-09 16:48:46.159
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
                COMPANY.COMPANY_ID,
                COMPANY.COMPANY_CODE,
                COMPANY.COMPANY_ATTACHMENT,
                COMPANY.COMPANY_NAME,
                COMPANY.COMPANY_TYPE,
                COMPANY.LICENSE_NUM,
                COMPANY.AREA_CODE,
                COMPANY.ADDRESS,
                COMPANY.ZIP_CODE,
                COMPANY.LEGAL_MAN,
                COMPANY.LEGAL_MAN_PHONE,
                COMPANY.LEGAL_MAN_DUTY,
                COMPANY.LEGAL_MAN_PRO_TITLE,
                COMPANY.LEGAL_MAN_ID_CARD_TYPE,
                COMPANY.LEGAL_MAN_ID_CARD_NUMBER,
                COMPANY.REG_CAPITAL,
                COMPANY.FACT_REG_CAPITAL,
                COMPANY.CAPITAL_CURRENCY_TYPE,
                COMPANY.REGISTER_DATE,
                COMPANY.ESTABLISH_DATE,
                COMPANY.OFFICE_PHONE,
                COMPANY.FAX_NUMBER,
                COMPANY.LINK_MAN,
                COMPANY.LINK_DUTY,
                COMPANY.LINK_PHONE,
                COMPANY.EMAIL,
                COMPANY.WEB_SITE,
                COMPANY.REMARK
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
    public Company createOrUpdateCompany(CompanyAo companyAo) {
        Company company = new Company();
        BeanUtil.copyProperties(companyAo, company);
        this.saveOrUpdate(company);
        return company;
    }

    @Override
    public List<Company> createCompanyBatch(List<CompanyAo> companyAos) {
        List<Company> companyList = new ArrayList<>();
        for (CompanyAo companyAo : companyAos) {
            Company company = new Company();
            BeanUtil.copyProperties(companyAo, company);
            companyList.add(company);
        }
        saveBatch(companyList);
        return companyList;
    }

    @Override
    public List<Company> updateCompanyBatch(List<CompanyAo> companyAos) {
        List<Company> companyList = new ArrayList<>();
        for (CompanyAo companyAo : companyAos) {
            Company company = new Company();
            BeanUtil.copyProperties(companyAo, company);
            companyList.add(company);
        }
        updateBatch(companyList);
        return companyList;
    }

    @Override
    public List<Company> createOrUpdateCompanyBatch(List<CompanyAo> companyAos) {
        List<Company> companyList = new ArrayList<>();
        for (CompanyAo companyAo : companyAos) {
            Company company = new Company();
            BeanUtil.copyProperties(companyAo, company);
            companyList.add(company);
        }
        saveOrUpdateBatch(companyList);
        return companyList;
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
        }, CompanyExcelVo.class.getDeclaredFields());
        simpleExportListener.exportWithEntity(WebUtil.getHttpServletResponse(), "企业信息", CompanyExcelVo.class, companyVo);
    }

    @SneakyThrows
    @Override
    public void importExcel(MultipartFile multipartFile) {
        SimpleImportListener<Company, CompanyExcelVo> simpleImportListener = new SimpleImportListener<>(new ImportExcelService<>() {

            @Override
            public CompanyExcelVo toExcelVo(Map<String, String> cellData) {
                CompanyExcelVo companyExcelVo = new CompanyExcelVo();
                companyExcelVo.setCompanyId(parseLong(cellData.get("companyId")));
                companyExcelVo.setCompanyCode(cellData.get("companyCode"));
                companyExcelVo.setCompanyAttachment(cellData.get("companyAttachment"));
                companyExcelVo.setCompanyName(cellData.get("companyName"));
                companyExcelVo.setCompanyType(cellData.get("companyType"));
                companyExcelVo.setLicenseNum(cellData.get("licenseNum"));
                companyExcelVo.setAreaCode(DictCacheUtil.validateAreaCode(cellData.get("province"), cellData.get("city"), cellData.get("areaCode")));
                companyExcelVo.setAddress(cellData.get("address"));
                companyExcelVo.setZipCode(cellData.get("zipCode"));
                companyExcelVo.setLegalMan(cellData.get("legalMan"));
                companyExcelVo.setLegalManPhone(cellData.get("legalManPhone"));
                companyExcelVo.setLegalManDuty(cellData.get("legalManDuty"));
                companyExcelVo.setLegalManProTitle(cellData.get("legalManProTitle"));
                companyExcelVo.setLegalManIdCardType(cellData.get("legalManIdCardType"));
                companyExcelVo.setLegalManIdCardNumber(cellData.get("legalManIdCardNumber"));
                companyExcelVo.setRegCapital(cellData.get("regCapital"));
                companyExcelVo.setFactRegCapital(cellData.get("factRegCapital"));
                companyExcelVo.setCapitalCurrencyType(cellData.get("capitalCurrencyType"));
                companyExcelVo.setRegisterDate(parseLocalDate(cellData.get("registerDate")));
                companyExcelVo.setEstablishDate(parseLocalDate(cellData.get("establishDate")));
                companyExcelVo.setOfficePhone(cellData.get("officePhone"));
                companyExcelVo.setFaxNumber(cellData.get("faxNumber"));
                companyExcelVo.setLinkMan(cellData.get("linkMan"));
                companyExcelVo.setLinkDuty(cellData.get("linkDuty"));
                companyExcelVo.setLinkPhone(cellData.get("linkPhone"));
                companyExcelVo.setEmail(cellData.get("email"));
                companyExcelVo.setWebSite(cellData.get("webSite"));
                companyExcelVo.setRemark(cellData.get("remark"));
                return companyExcelVo;
            }

            @Override
            public Company toBean(CompanyExcelVo excelVo) {
                Company company = new Company();
                BeanUtil.copyProperties(excelVo, company);
                return company;
            }

            @Override
            public void saveBeans(List<Company> t) {
                saveBatch(t);
            }
        }, CompanyExcelVo.class.getDeclaredFields());
        EasyExcel.read(multipartFile.getInputStream(), simpleImportListener).sheet().doRead();
        Map<Integer, String> error = simpleImportListener.getError();
        if (CollectionUtil.isNotEmpty(error)) {
            throw new ExcelReadException("导入发生错误", error);
        }
    }
}
