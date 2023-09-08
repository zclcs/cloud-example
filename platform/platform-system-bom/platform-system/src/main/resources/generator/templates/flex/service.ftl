package ${basePackage}.${servicePackage};

import com.mybatisflex.core.service.IService;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import ${basePackage}.${aoPackage}.${className}Ao;
import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${voPackage}.${className}Vo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ${tableComment} Service接口
 *
 * @author ${author}
 * @since ${date}
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
    Long count${className}(${className}Vo ${className?uncap_first}Vo);

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
     * 新增或修改
     *
     * @param ${className?uncap_first}Ao {@link ${className}Ao}
     * @return {@link ${className}}
     */
    ${className} createOrUpdate${className}(${className}Ao ${className?uncap_first}Ao);

    /**
     * 批量新增
     *
     * @param ${className?uncap_first}Aos {@link ${className}Ao}
     * @return {@link ${className}}
     */
    List<${className}> create${className}Batch(List<${className}Ao> ${className?uncap_first}Aos);

    /**
     * 批量修改
     *
     * @param ${className?uncap_first}Aos {@link ${className}Ao}
     * @return {@link ${className}}
     */
    List<${className}> update${className}Batch(List<${className}Ao> ${className?uncap_first}Aos);

    /**
     * 批量新增或修改
     * id为空则新增，不为空则修改
     * 可以自行重写
     *
     * @param ${className?uncap_first}Aos {@link ${className}Ao}
     * @return {@link ${className}}
     * @see IService#saveOrUpdateBatch(Collection)
     */
    List<${className}> createOrUpdate${className}Batch(List<${className}Ao> ${className?uncap_first}Aos);

    /**
     * 删除
     *
     * @param ids 表id集合
     */
    void delete${className}(List<Long> ids);

    /**
     * excel导出
     *
     * @param ${className?uncap_first}Vo {@link ${className}Vo}
     */
    void exportExcel(${className}Vo ${className?uncap_first}Vo);

    /**
     * excel导入
     *
     * @param multipartFile excel文件
     */
    void importExcel(MultipartFile multipartFile);

}
