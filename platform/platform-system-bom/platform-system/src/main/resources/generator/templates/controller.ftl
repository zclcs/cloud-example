package ${basePackage}.${controllerPackage};

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.common.core.validate.strategy.UpdateStrategy;
import com.zclcs.common.logging.starter.annotation.ControllerEndpoint;
import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${aoPackage}.${className}Ao;
import ${basePackage}.${voPackage}.${className}Vo;
import ${basePackage}.${servicePackage}.${className}Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ${tableComment} Controller
 *
 * @author ${author}
 * @date ${date}
 */
@Slf4j
@RestController
@RequestMapping("${className?uncap_first}")
@RequiredArgsConstructor
@Tag(name = "${tableComment}")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class ${className}Controller {

    private final ${className}Service ${className?uncap_first}Service;

    @GetMapping
    @PreAuthorize("hasAuthority('${className?uncap_first}:view')")
    @Operation(summary = "${tableComment}查询（分页）")
    public BaseRsp<BasePage<${className}Vo>> find${className}Page(@Validated BasePageAo basePageAo, @Validated ${className}Vo ${className?uncap_first}Vo) {
        BasePage<${className}Vo> page = this.${className?uncap_first}Service.find${className}Page(basePageAo, ${className?uncap_first}Vo);
        return RspUtil.data(page);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('${className?uncap_first}:view')")
    @Operation(summary = "${tableComment}查询（集合）")
    public BaseRsp<List<${className}Vo>> find${className}List(@Validated ${className}Vo ${className?uncap_first}Vo) {
        List<${className}Vo> list = this.${className?uncap_first}Service.find${className}List(${className?uncap_first}Vo);
        return RspUtil.data(list);
    }

    @GetMapping("one")
    @PreAuthorize("hasAuthority('${className?uncap_first}:view')")
    @Operation(summary = "${tableComment}查询（单个）")
    public BaseRsp<${className}Vo> find${className}(@Validated ${className}Vo ${className?uncap_first}Vo) {
        ${className}Vo ${className?uncap_first} = this.${className?uncap_first}Service.find${className}(${className?uncap_first}Vo);
        return RspUtil.data(${className?uncap_first});
    }

    @PostMapping
    @PreAuthorize("hasAuthority('${className?uncap_first}:add')")
    @ControllerEndpoint(operation = "新增${tableComment}")
    @Operation(summary = "新增${tableComment}")
    public BaseRsp<${className}> add${className}(@RequestBody @Validated ${className}Ao ${className?uncap_first}Ao) {
        return RspUtil.data(this.${className?uncap_first}Service.create${className}(${className?uncap_first}Ao));
    }

    @DeleteMapping("/{${className?uncap_first}Ids}")
    @PreAuthorize("hasAuthority('${className?uncap_first}:delete')")
    @ControllerEndpoint(operation = "删除${tableComment}")
    @Operation(summary = "删除${tableComment}")
    @Parameters({
        @Parameter(name = "${className?uncap_first}Ids", description = "${tableComment}id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public BaseRsp<String> delete${className}(@NotBlank(message = "{required}") @PathVariable String ${className?uncap_first}Ids) {
        List<Long> ids = Arrays.stream(${className?uncap_first}Ids.split(StringConstant.COMMA)).map(Long::valueOf).collect(Collectors.toList());
        this.${className?uncap_first}Service.delete${className}(ids);
        return RspUtil.message("删除成功");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('${className?uncap_first}:update')")
    @ControllerEndpoint(operation = "修改${tableComment}")
    @Operation(summary = "修改${tableComment}")
    public BaseRsp<${className}> update${className}(@RequestBody @Validated(UpdateStrategy.class) ${className}Ao ${className?uncap_first}Ao) {
        return RspUtil.data(this.${className?uncap_first}Service.update${className}(${className?uncap_first}Ao));
    }
}