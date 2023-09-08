package ${basePackage}.${controllerPackage};

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.bean.ValidatedList;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.ValidGroups;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import ${basePackage}.${aoPackage}.${className}Ao;
import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${voPackage}.${className}Vo;
import ${basePackage}.${servicePackage}.${className}Service;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ${tableComment}
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@RestController
@RequestMapping("/${className?uncap_first}")
@RequiredArgsConstructor
public class ${className}Controller {

    private final ${className}Service ${className?uncap_first}Service;

    /**
     * ${tableComment}查询（分页）
     * 权限: ${className?uncap_first}:page
     *
     * @see ${className}Service#find${className}Page(BasePageAo, ${className}Vo)
     */
    @GetMapping
    @SaCheckPermission("${className?uncap_first}:page")
    public BaseRsp<BasePage<${className}Vo>> find${className}Page(@Validated BasePageAo basePageAo, @Validated ${className}Vo ${className?uncap_first}Vo) {
        BasePage<${className}Vo> page = this.${className?uncap_first}Service.find${className}Page(basePageAo, ${className?uncap_first}Vo);
        return RspUtil.data(page);
    }

    /**
     * ${tableComment}查询（集合）
     * 权限: ${className?uncap_first}:list
     *
     * @see ${className}Service#find${className}List(${className}Vo)
     */
    @GetMapping("/list")
    @SaCheckPermission("${className?uncap_first}:list")
    public BaseRsp<List<${className}Vo>> find${className}List(@Validated ${className}Vo ${className?uncap_first}Vo) {
        List<${className}Vo> list = this.${className?uncap_first}Service.find${className}List(${className?uncap_first}Vo);
        return RspUtil.data(list);
    }

    /**
     * ${tableComment}查询（单个）
     * 权限: ${className?uncap_first}:one
     *
     * @see ${className}Service#find${className}(${className}Vo)
     */
    @GetMapping("/one")
    @SaCheckPermission("${className?uncap_first}:one")
    public BaseRsp<${className}Vo> find${className}(@Validated ${className}Vo ${className?uncap_first}Vo) {
        ${className}Vo ${className?uncap_first} = this.${className?uncap_first}Service.find${className}(${className?uncap_first}Vo);
        return RspUtil.data(${className?uncap_first});
    }

    /**
     * 新增${tableComment}
     * 权限: ${className?uncap_first}:add
     *
     * @see ${className}Service#create${className}(${className}Ao)
     */
    @PostMapping
    @SaCheckPermission("${className?uncap_first}:add")
    @ControllerEndpoint(operation = "新增${tableComment}")
    public BaseRsp<${className}> add${className}(@Validated @RequestBody ${className}Ao ${className?uncap_first}Ao) {
        return RspUtil.data(this.${className?uncap_first}Service.create${className}(${className?uncap_first}Ao));
    }

    /**
     * 修改${tableComment}
     * 权限: ${className?uncap_first}:update
     *
     * @see ${className}Service#update${className}(${className}Ao)
     */
    @PutMapping
    @SaCheckPermission("${className?uncap_first}:update")
    @ControllerEndpoint(operation = "修改${tableComment}")
    public BaseRsp<${className}> update${className}(@Validated({ValidGroups.Crud.Update.class}) @RequestBody ${className}Ao ${className?uncap_first}Ao) {
        return RspUtil.data(this.${className?uncap_first}Service.update${className}(${className?uncap_first}Ao));
    }

    /**
     * 新增或修改${tableComment}
     * 权限: ${className?uncap_first}:createOrUpdate
     *
     * @see ${className}Service#createOrUpdate${className}(${className}Ao)
     */
    @PostMapping("/createOrUpdate")
    @SaCheckPermission("${className?uncap_first}:createOrUpdate")
    @ControllerEndpoint(operation = "新增或修改${tableComment}")
    public BaseRsp<${className}> createOrUpdate${className}(@RequestBody @Validated ${className}Ao ${className?uncap_first}Ao) {
        return RspUtil.data(this.${className?uncap_first}Service.createOrUpdate${className}(${className?uncap_first}Ao));
    }

    /**
     * 删除${tableComment}
     * 权限: ${className?uncap_first}:delete
     *
     * @param ${className?uncap_first}Ids ${tableComment}id集合(,分隔)
     * @see ${className}Service#delete${className}(List)
     */
    @DeleteMapping("/{${className?uncap_first}Ids}")
    @SaCheckPermission("${className?uncap_first}:delete")
    @ControllerEndpoint(operation = "删除${tableComment}")
    public BaseRsp<String> delete${className}(@NotBlank(message = "{required}") @PathVariable String ${className?uncap_first}Ids) {
        List<Long> ids = Arrays.stream(${className?uncap_first}Ids.split(Strings.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.${className?uncap_first}Service.delete${className}(ids);
        return RspUtil.message("删除成功");
    }

    /**
     * 批量新增${tableComment}
     * 权限: ${className?uncap_first}:add:batch
     *
     * @see ${className}Service#create${className}Batch(List)
     */
    @PostMapping("/batch")
    @SaCheckPermission("${className?uncap_first}:add:batch")
    @ControllerEndpoint(operation = "批量新增${tableComment}")
    public BaseRsp<List<${className}>> create${className}Batch(@RequestBody @Validated ValidatedList<${className}Ao> ${className?uncap_first}Aos) {
        return RspUtil.data(this.${className?uncap_first}Service.create${className}Batch(${className?uncap_first}Aos));
    }

    /**
     * 批量修改${tableComment}
     * 权限: ${className?uncap_first}:update:batch
     *
     * @see ${className}Service#createOrUpdate${className}Batch(List)
     */
    @PutMapping("/batch")
    @SaCheckPermission("${className?uncap_first}:update:batch")
    @ControllerEndpoint(operation = "批量修改${tableComment}")
    public BaseRsp<List<${className}>> update${className}Batch(@RequestBody @Validated({ValidGroups.Crud.Update.class}) ValidatedList<${className}Ao> ${className?uncap_first}Aos) {
        return RspUtil.data(this.${className?uncap_first}Service.update${className}Batch(${className?uncap_first}Aos));
    }

    /**
     * 批量新增或修改${tableComment}
     * id为空则新增，不为空则修改
     * 权限: ${className?uncap_first}:createOrUpdate:batch
     *
     * @see ${className}Service#createOrUpdate${className}Batch(List)
     */
    @PostMapping("/createOrUpdate/batch")
    @SaCheckPermission("${className?uncap_first}:createOrUpdate:batch")
    @ControllerEndpoint(operation = "批量新增或修改${tableComment}")
    public BaseRsp<List<${className}>> createOrUpdate${className}Batch(@RequestBody @Validated ValidatedList<${className}Ao> ${className?uncap_first}Aos) {
        return RspUtil.data(this.${className?uncap_first}Service.createOrUpdate${className}Batch(${className?uncap_first}Aos));
    }

    /**
     * 导出${tableComment}
     * 权限: ${className?uncap_first}:export
     *
     * @see ${className}Service#exportExcel(${className}Vo)
     */
    @GetMapping("/export/excel")
    @SaCheckPermission("${className?uncap_first}:export")
    public void exportExcel(@Validated ${className}Vo ${className?uncap_first}Vo) {
        ${className?uncap_first}Service.exportExcel(${className?uncap_first}Vo);
    }

    /**
     * 导入${tableComment}
     * 权限: ${className?uncap_first}:import
     *
     * @see ${className}Service#importExcel(MultipartFile)
     */
    @PostMapping("/import/excel")
    @SaCheckPermission("${className?uncap_first}:import")
    public BaseRsp<String> importExcel(@NotNull(message = "{required}") MultipartFile file) {
        ${className?uncap_first}Service.importExcel(file);
        return RspUtil.message("导入成功");
    }
}