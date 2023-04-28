package ${basePackage}.${mapperPackage};

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import base.com.zclcs.common.core.BasePage;
import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${voPackage}.${className}Vo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${tableComment} Mapper
 *
 * @author ${author}
 * @date ${date}
 */
public interface ${className}Mapper extends BaseMapper<${className}> {

    /**
     * 分页
     *
     * @param basePage {@link BasePage}
     * @param ew       {@link ${className}Vo}
     * @return {@link ${className}Vo}
     */
    BasePage<${className}Vo> findPageVo(BasePage<${className}Vo> basePage, @Param(Constants.WRAPPER) Wrapper<${className}Vo> ew);

    /**
     * 查找集合
     *
     * @param ew {@link ${className}Vo}
     * @return {@link ${className}Vo}
     */
    List<${className}Vo> findListVo(@Param(Constants.WRAPPER) Wrapper<${className}Vo> ew);

    /**
     * 查找单个
     *
     * @param ew {@link ${className}Vo}
     * @return {@link ${className}Vo}
     */
    ${className}Vo findOneVo(@Param(Constants.WRAPPER) Wrapper<${className}Vo> ew);

    /**
     * 统计
     *
     * @param ew {@link ${className}Vo}
     * @return 统计值
     */
    Integer countVo(@Param(Constants.WRAPPER) Wrapper<${className}Vo> ew);

}