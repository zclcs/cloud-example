package ${basePackage}.${controllerPackage};

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.aop.annotation.ControllerEndpoint;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.strategy.ValidGroups;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${aoPackage}.${className}Ao;
import ${basePackage}.${voPackage}.${className}Vo;
import ${basePackage}.${servicePackage}.${className}Service;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("${className?uncap_first}")
@RequiredArgsConstructor
public class ${className}Controller {

    private final ${className}Service ${className?uncap_first}Service;

    /**
     * ${tableComment}查询（分页）
     * 权限: ${className?uncap_first}:view
     *
     * @see ${className}Service#find${className}Page(BasePageAo, ${className}Vo)
     */
    @GetMapping
    @SaCheckPermission("${className?uncap_first}:view")
    public BaseRsp<BasePage<${className}Vo>> find${className}Page(@Validated BasePageAo basePageAo, @Validated ${className}Vo ${className?uncap_first}Vo) {
        BasePage<${className}Vo> page = this.${className?uncap_first}Service.find${className}Page(basePageAo, ${className?uncap_first}Vo);
        return RspUtil.data(page);
    }

    /**
     * ${tableComment}查询（集合）
     * 权限: ${className?uncap_first}:view
     *
     * @see ${className}Service#find${className}List(${className}Vo)
     */
    @GetMapping("list")
    @SaCheckPermission("${className?uncap_first}:view")
    public BaseRsp<List<${className}Vo>> find${className}List(@Validated ${className}Vo ${className?uncap_first}Vo) {
        List<${className}Vo> list = this.${className?uncap_first}Service.find${className}List(${className?uncap_first}Vo);
        return RspUtil.data(list);
    }

    /**
     * ${tableComment}查询（单个）
     * 权限: ${className?uncap_first}:view
     *
     * @see ${className}Service#find${className}(${className}Vo)
     */
    @GetMapping("one")
    @SaCheckPermission("${className?uncap_first}:view")
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
}