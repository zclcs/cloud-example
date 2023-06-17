package com.zclcs.nacos.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author zclcs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "NacosBaseDataVo", description = "nacos")
public class NacosBaseDataVo<T> {

    @Schema(description = "code")
    private Integer code;

    @Schema(description = "返回消息")
    private String message;

    @Schema(description = "返回数据")
    public T data;

    public boolean success() {
        return this.code == 200;
    }
}
