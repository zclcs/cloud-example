package com.zclcs.platform.maintenance.bean.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * NacosTokenVo
 *
 * @author zclcs
 * @date 2023-01-10 10:39:49.113
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "XxlJobBaseResultVo对象", description = "XxlJobBaseResultVo对象")
public class XxlJobBaseResultVo<T> {

    /**
     * 服务名
     */
    @Schema(description = "code")
    private Integer code;

    /**
     * 服务名
     */
    @Schema(description = "消息")
    private String msg;

    /**
     * 服务名
     */
    @Schema(description = "内容")
    private T content;

    public boolean success() {
        return this.code == 200;
    }

    public boolean failure() {
        return this.code != 200;
    }
}
