package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.UpdateStrategy;
import com.zclcs.cloud.lib.dict.json.annotation.DictValid;
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
public class BlackListAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 黑名单id
     */
    @NotNull(message = "{required}", groups = UpdateStrategy.class)
    private Long blackId;

    /**
     * 黑名单ip
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String blackIp;

    /**
     * 请求uri（支持通配符）
     */
    @Size(max = 200, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String requestUri;

    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     */
    @Size(max = 10, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String requestMethod;

    /**
     * 限制时间起
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String limitFrom;

    /**
     * 限制时间止
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String limitTo;

    /**
     * ip对应地址
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String location;

    /**
     * 黑名单状态 默认 1 @@enable_disable
     */
    @Size(max = 40, message = "{noMoreThan}")
    @DictValid(value = "enable_disable", message = "{dict}")
    private String blackStatus;


}