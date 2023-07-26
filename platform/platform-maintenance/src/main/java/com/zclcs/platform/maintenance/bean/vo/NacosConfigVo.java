package com.zclcs.platform.maintenance.bean.vo;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * nacos配置
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
public class NacosConfigVo {

    /**
     * 总数
     */
    private Long totalCount;

    /**
     * 页码
     */
    private Long pageNumber;

    /**
     * 总页数
     */
    private Long pagesAvailable;

    /**
     * 数据
     */
    private List<ConfigVo> pageItems;

    /**
     * 数据详情
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class ConfigVo {

        /**
         * id
         */
        private Long id;

        /**
         * dataId
         */
        private String dataId;

        /**
         * group
         */
        private String group;

        /**
         * 内容
         */
        private String content;

        /**
         * md5
         */
        private String md5;

        /**
         * encryptedDataKey
         */
        private String encryptedDataKey;

        /**
         * tenant
         */
        private String tenant;

        /**
         * appName
         */
        private String appName;

        /**
         * type
         */
        private String type;

    }

    /**
     * 配置详情
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class ConfigDetailVo {

        /**
         * id
         */
        private Long id;

        /**
         * dataId
         */
        private String dataId;

        /**
         * group
         */
        private String group;

        /**
         * 内容
         */
        private String content;

        /**
         * md5
         */
        private String md5;

        /**
         * encryptedDataKey
         */
        private String encryptedDataKey;

        /**
         * tenant
         */
        private String tenant;

        /**
         * appName
         */
        private String appName;

        /**
         * type
         */
        private String type;

        /**
         * 创建时间
         */
        private Long createTime;

        /**
         * 修改时间
         */
        private Long modifyTime;

        /**
         * 创建用户
         */
        private String createUser;

        /**
         * 创建IP
         */
        private String createIp;

        /**
         * desc
         */
        private String desc;

        /**
         * use
         */
        private String use;

        /**
         * effect
         */
        private String effect;

        /**
         * schema
         */
        private String schema;

        /**
         * configTags
         */
        private String configTags;

    }
}
