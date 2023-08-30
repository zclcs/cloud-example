package com.zclcs.platform.maintenance.bean.vo;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * nacos服务列表
 *
 * @author zclcs
 * @since 2023-01-10 10:39:49.113
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NacosServiceVo {

    /**
     * 总数
     */
    private Long count;

    /**
     * 数据
     */
    private List<Service> serviceList;

    /**
     * 服务详情
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class Service {

        /**
         * 服务名
         */
        private String name;

        /**
         * 分组名称
         */
        private String groupName;

        /**
         * 集群数量
         */
        private Integer clusterCount;

        /**
         * 实例数
         */
        private String ipCount;

        /**
         * 健康实例数
         */
        private String healthyInstanceCount;

        /**
         * 触发保护阈值
         */
        private String triggerFlag;

    }
}
