package com.zclcs.test.test.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.test.test.api.bean.entity.Company;
import com.zclcs.test.test.api.bean.ao.CompanyAo;
import com.zclcs.test.test.api.bean.vo.CompanyVo;
import com.zclcs.test.test.mapper.CompanyMapper;
import com.zclcs.test.test.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 企业信息 Service实现
 *
 * @author zclcs
 * @date 2023-08-16 14:53:29.133
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Override
    public BasePage<CompanyVo> findCompanyPage(BasePageAo basePageAo, CompanyVo companyVo) {
        BasePage<CompanyVo> basePage = new BasePage<>(basePageAo.getPageNum(), basePageAo.getPageSize());
        QueryWrapper<CompanyVo> queryWrapper = getQueryWrapper(companyVo);
        return this.baseMapper.findPageVo(basePage, queryWrapper);
    }

    @Override
    public List<CompanyVo> findCompanyList(CompanyVo companyVo) {
        QueryWrapper<CompanyVo> queryWrapper = getQueryWrapper(companyVo);
        return this.baseMapper.findListVo(queryWrapper);
    }

    @Override
    public CompanyVo findCompany(CompanyVo companyVo) {
        QueryWrapper<CompanyVo> queryWrapper = getQueryWrapper(companyVo);
        return this.baseMapper.findOneVo(queryWrapper);
    }

    @Override
    public Integer countCompany(CompanyVo companyVo) {
    QueryWrapper<CompanyVo> queryWrapper = getQueryWrapper(companyVo);
        return this.baseMapper.countVo(queryWrapper);
    }

    private QueryWrapper<CompanyVo> getQueryWrapper(CompanyVo companyVo) {
        QueryWrapper<CompanyVo> queryWrapper = new QueryWrapper<>();
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
