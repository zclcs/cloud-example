package com.zclcs.test.test.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.test.test.api.bean.ao.CompanyAo;
import com.zclcs.test.test.api.bean.entity.Company;
import com.zclcs.test.test.api.bean.vo.CompanyVo;
import com.zclcs.test.test.mapper.CompanyMapper;
import com.zclcs.test.test.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 企业信息 Service实现
 *
 * @author zclcs
 * @since 2023-09-01 16:25:49.782
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
}
