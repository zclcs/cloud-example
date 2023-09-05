package com.zclcs.platform.system.api.bean.ao;

import com.zclcs.cloud.lib.core.strategy.ValidGroups;
import com.zclcs.cloud.lib.dict.annotation.DictValid;
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
 * @since 2023-09-01 19:53:59.035
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
     * 默认值：
     */
    @NotNull(message = "{required}", groups = {ValidGroups.Crud.Update.class})
    private Long blackId;

    /**
     * 黑名单ip
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    private String blackIp;

    /**
     * 请求uri（支持通配符）
     * 默认值：
     */
    @Size(max = 200, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String requestUri;

    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     * 默认值：
     */
    @Size(max = 10, message = "{noMoreThan}")
    @NotBlank(message = "{required}")
    private String requestMethod;

    /**
     * 限制时间起
     * 默认值：00:00:00
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String limitFrom;

    /**
     * 限制时间止
     * 默认值：23:59:59
     */
    @Size(max = 20, message = "{noMoreThan}")
    private String limitTo;

    /**
     * ip对应地址
     * 默认值：
     */
    @Size(max = 255, message = "{noMoreThan}")
    private String location;

    /**
     * 黑名单状态 默认 1 @@enable_disable
     * 默认值：1
     */
    @DictValid(value = "enable_disable", message = "{dict}")
    @Size(max = 1, message = "{noMoreThan}")
    private String blackStatus;


}