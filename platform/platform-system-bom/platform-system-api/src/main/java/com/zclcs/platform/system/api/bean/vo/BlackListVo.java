package com.zclcs.platform.system.api.bean.vo;

import com.zclcs.cloud.lib.core.base.BaseEntity;
import com.zclcs.cloud.lib.dict.json.annotation.DictText;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 黑名单 Vo
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
@Schema(title = "BlackListVo对象", description = "黑名单")
public class BlackListVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "黑名单id")
    private Long blackId;

    @Schema(description = "黑名单id集合")
    private List<Long> blackIds;

    @Schema(description = "黑名单ip")
    private String blackIp;

    @Schema(description = "请求uri（支持通配符）")
    private String requestUri;

    @Schema(description = "请求方法，如果为ALL则表示对所有方法生效")
    private String requestMethod;

    @Schema(description = "限制时间起")
    private String limitFrom;

    @Schema(description = "限制时间止")
    private String limitTo;

    @Schema(description = "ip对应地址")
    private String location;

    @Schema(description = "黑名单状态 默认 1 @@enable_disable")
    @DictText(value = "enable_disable")
    private String blackStatus;


}