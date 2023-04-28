package ${basePackage}.${servicePackage};

import com.baomidou.mybatisplus.extension.service.IService;
import base.com.zclcs.common.core.BasePage;
import base.com.zclcs.common.core.BasePageAo;
import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${aoPackage}.${className}Ao;
import ${basePackage}.${voPackage}.${className}Vo;

import java.util.List;

/**
 * ${tableComment} Service接口
 *
 * @author ${author}
 * @date ${date}
 */
public interface ${className}Service extends IService<${className}> {

    /**
     * 查询（分页）
     *
     * @param basePageAo {@link BasePageAo}
     * @param ${className?uncap_first}Vo {@link ${className}Vo}
     * @return {@link ${className}Vo}
     */
    BasePage<${className}Vo> find${className}Page(BasePageAo basePageAo, ${className}Vo ${className?uncap_first}Vo);

    /**
     * 查询（所有）
     *
     * @param ${className?uncap_first}Vo {@link ${className}Vo}
     * @return {@link ${className}Vo}
     */
    List<${className}Vo> find${className}List(${className}Vo ${className?uncap_first}Vo);

    /**
     * 查询（单个）
     *
     * @param ${className?uncap_first}Vo {@link ${className}Vo}
     * @return {@link ${className}Vo}
     */
    ${className}Vo find${className}(${className}Vo ${className?uncap_first}Vo);

    /**
     * 统计
     *
     * @param ${className?uncap_first}Vo {@link ${className}Vo}
     * @return 统计值
     */
    Integer count${className}(${className}Vo ${className?uncap_first}Vo);

    /**
     * 新增
     *
     * @param ${className?uncap_first}Ao {@link ${className}Ao}
     * @return {@link ${className}}
     */
     ${className} create${className}(${className}Ao ${className?uncap_first}Ao);

    /**
     * 修改
     *
     * @param ${className?uncap_first}Ao {@link ${className}Ao}
     * @return {@link ${className}}
     */
     ${className} update${className}(${className}Ao ${className?uncap_first}Ao);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void delete${className}(List<Long> ids);

}
