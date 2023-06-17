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
@Schema(title = "NacosImportVo", description = "nacos")
public class NacosImportVo {

    @Schema(description = "成功数量")
    private Integer succCount;

    @Schema(description = "失败数量")
    private Integer skipCount;
}
