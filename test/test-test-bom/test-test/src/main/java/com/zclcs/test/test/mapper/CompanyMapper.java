package com.zclcs.test.test.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.test.test.api.entity.Company;
import com.zclcs.test.test.api.entity.vo.CompanyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 企业信息 Mapper
 *
 * @author zclcs
 * @date 2023-04-12 15:19:44.653
 */
public interface CompanyMapper extends BaseMapper<Company> {

    /**
     * 分页
     *
     * @param basePage {@link BasePage}
     * @param ew       {@link CompanyVo}
     * @return {@link CompanyVo}
     */
    BasePage<CompanyVo> findPageVo(BasePage<CompanyVo> basePage, @Param(Constants.WRAPPER) Wrapper<CompanyVo> ew);

    /**
     * 查找集合
     *
     * @param ew {@link CompanyVo}
     * @return {@link CompanyVo}
     */
    List<CompanyVo> findListVo(@Param(Constants.WRAPPER) Wrapper<CompanyVo> ew);

    /**
     * 查找单个
     *
     * @param ew {@link CompanyVo}
     * @return {@link CompanyVo}
     */
    CompanyVo findOneVo(@Param(Constants.WRAPPER) Wrapper<CompanyVo> ew);

    /**
     * 统计
     *
     * @param ew {@link CompanyVo}
     * @return 统计值
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<CompanyVo> ew);

}