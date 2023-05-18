package com.zclcs.platform.system.api.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

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
@Schema(title = "NacosConfigVo对象", description = "nacosToken")
public class NacosConfigVo {

    @Schema(description = "总数")
    private Long totalCount;

    @Schema(description = "页码")
    private Long pageNumber;

    @Schema(description = "总页数")
    private Long pagesAvailable;

    @Schema(description = "数据")
    private List<Config> pageItems;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Schema(title = "Config对象", description = "NacosConfig")
    public static class Config {
        @Schema(description = "id")
        private Long id;
        @Schema(description = "dataId")
        private String dataId;
        @Schema(description = "group")
        private String group;
        @Schema(description = "内容")
        private String content;
        @Schema(description = "md5")
        private String md5;
        @Schema(description = "encryptedDataKey")
        private String encryptedDataKey;
        @Schema(description = "tenant")
        private String tenant;
        @Schema(description = "appName")
        private String appName;
        @Schema(description = "type")
        private String type;
    }
}
