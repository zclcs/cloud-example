package ${basePackage}.${servicePackage};

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
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
     * @see BasePageAo
     * @see ${className}Vo
     * @param basePageAo BasePageAo
     * @param ${className?uncap_first}Vo ${className}Vo
     * @return BasePage<${className}Vo>
     */
    BasePage<${className}Vo> find${className}Page(BasePageAo basePageAo, ${className}Vo ${className?uncap_first}Vo);

    /**
     * 查询（所有）
     *
     * @see ${className}Vo
     * @param ${className?uncap_first}Vo ${className}Vo
     * @return List<${className}Vo>
     */
    List<${className}Vo> find${className}List(${className}Vo ${className?uncap_first}Vo);

    /**
     * 查询（单个）
     *
     * @see ${className}Vo
     * @param ${className?uncap_first}Vo ${className}Vo
     * @return ${className}Vo
     */
    ${className}Vo find${className}(${className}Vo ${className?uncap_first}Vo);

    /**
     * 统计
     *
     * @see ${className}Vo
     * @param ${className?uncap_first}Vo ${className}Vo
     * @return ${className}Vo
     */
    Integer count${className}(${className}Vo ${className?uncap_first}Vo);

    /**
     * 新增
     *
     * @see ${className}Vo
     * @param ${className?uncap_first}Vo ${className}Vo
     * @return ${className}
     */
     ${className} create${className}(${className}Ao ${className?uncap_first}Ao);

    /**
     * 修改
     *
     * @see ${className}Vo
     * @param ${className?uncap_first}Vo ${className}Vo
     * @return ${className}
     */
     ${className} update${className}(${className}Ao ${className?uncap_first}Ao);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void delete${className}(List<Long> ids);

}
