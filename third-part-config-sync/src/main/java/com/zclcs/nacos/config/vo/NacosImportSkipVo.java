package com.zclcs.nacos.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zclcs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "NacosImportSkipVo", description = "nacos")
public class NacosImportSkipVo {

    @Schema(description = "成功数量")
    private Integer succCount;

    @Schema(description = "跳过配置详情")
    private List<SkipData> skipData;

    @Schema(description = "跳过数量")
    private Integer skipCount;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Schema(title = "SkipData", description = "nacos")
    public static class SkipData {
        private String dataId;
        private String group;
    }
}
