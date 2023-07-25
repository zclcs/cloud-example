package com.zclcs.platform.maintenance.bean.vo;

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

    /**
     * 服务名
     */
    @Schema(description = "总数")
    private Long totalCount;

    /**
     * 服务名
     */
    @Schema(description = "页码")
    private Long pageNumber;

    /**
     * 服务名
     */
    @Schema(description = "总页数")
    private Long pagesAvailable;

    /**
     * 服务名
     */
    @Schema(description = "数据")
    private List<ConfigVo> pageItems;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Schema(title = "ConfigVo对象", description = "NacosConfig")
    public static class ConfigVo {
        /**
         * 服务名
         */
        @Schema(description = "id")
        private Long id;
        /**
         * 服务名
         */
        @Schema(description = "dataId")
        private String dataId;
        /**
         * 服务名
         */
        @Schema(description = "group")
        private String group;
        /**
         * 服务名
         */
        @Schema(description = "内容")
        private String content;
        /**
         * 服务名
         */
        @Schema(description = "md5")
        private String md5;
        /**
         * 服务名
         */
        @Schema(description = "encryptedDataKey")
        private String encryptedDataKey;
        /**
         * 服务名
         */
        @Schema(description = "tenant")
        private String tenant;
        /**
         * 服务名
         */
        @Schema(description = "appName")
        private String appName;
        /**
         * 服务名
         */
        @Schema(description = "type")
        private String type;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Schema(title = "ConfigDetailVo对象", description = "NacosConfig")
    public static class ConfigDetailVo {
        /**
         * 服务名
         */
        @Schema(description = "id")
        private Long id;
        /**
         * 服务名
         */
        @Schema(description = "dataId")
        private String dataId;
        /**
         * 服务名
         */
        @Schema(description = "group")
        private String group;
        /**
         * 服务名
         */
        @Schema(description = "内容")
        private String content;
        /**
         * 服务名
         */
        @Schema(description = "md5")
        private String md5;
        /**
         * 服务名
         */
        @Schema(description = "encryptedDataKey")
        private String encryptedDataKey;
        /**
         * 服务名
         */
        @Schema(description = "tenant")
        private String tenant;
        /**
         * 服务名
         */
        @Schema(description = "appName")
        private String appName;
        /**
         * 服务名
         */
        @Schema(description = "type")
        private String type;
        /**
         * 服务名
         */
        @Schema(description = "创建时间")
        private Long createTime;
        /**
         * 服务名
         */
        @Schema(description = "修改时间")
        private Long modifyTime;
        /**
         * 服务名
         */
        @Schema(description = "创建用户")
        private String createUser;
        /**
         * 服务名
         */
        @Schema(description = "创建IP")
        private String createIp;
        /**
         * 服务名
         */
        @Schema(description = "desc")
        private String desc;
        /**
         * 服务名
         */
        @Schema(description = "use")
        private String use;
        /**
         * 服务名
         */
        @Schema(description = "effect")
        private String effect;
        /**
         * 服务名
         */
        @Schema(description = "schema")
        private String schema;
        /**
         * 服务名
         */
        @Schema(description = "configTags")
        private String configTags;
    }
}
