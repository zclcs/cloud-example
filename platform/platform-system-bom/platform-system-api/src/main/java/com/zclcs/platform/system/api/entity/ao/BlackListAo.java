package com.zclcs.platform.system.api.entity.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.json.annotation.DictValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 黑名单 Ao
 *
 * @author zclcs
 * @date 2023-01-10 10:40:14.628
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "BlackListAo对象", description = "黑名单")
public class BlackListAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    @Schema(description = "黑名单id")
    private Long blackId;

    @Size(max = 200, message = "{noMoreThan}")
    @Schema(description = "黑名单ip")
    private String blackIp;

    @Size(max = 200, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "请求uri（支持通配符）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String requestUri;

    @Size(max = 10, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    @Schema(description = "请求方法，如果为ALL则表示对所有方法生效", requiredMode = Schema.RequiredMode.REQUIRED)
    private String requestMethod;

    @Size(max = 20, message = "{noMoreThan}")
    @Schema(description = "限制时间起")
    private String limitFrom;

    @Size(max = 20, message = "{noMoreThan}")
    @Schema(description = "限制时间止")
    private String limitTo;

    @Size(max = 255, message = "{noMoreThan}")
    @Schema(description = "ip对应地址")
    private String location;

    @Size(max = 40, message = "{noMoreThan}")
    @Schema(description = "黑名单状态 默认 1 @@enable_disable")
    @DictValid(value = "enable_disable", message = "{dict}")
    private String blackStatus;


}