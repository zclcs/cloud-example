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
public class NacosServiceVo {

    /**
     * 服务名
     */
    @Schema(description = "总数")
    private Long count;

    /**
     * 服务名
     */
    @Schema(description = "数据")
    private List<Service> serviceList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Schema(title = "Service对象", description = "NacosService")
    public static class Service {
        /**
         * 服务名
         */
        @Schema(description = "服务名")
        private String name;
        /**
         * 服务名
         */
        @Schema(description = "分组名称")
        private String groupName;
        /**
         * 服务名
         */
        @Schema(description = "集群数量")
        private Integer clusterCount;
        /**
         * 服务名
         */
        @Schema(description = "实例数")
        private String ipCount;
        /**
         * 服务名
         */
        @Schema(description = "健康实例数")
        private String healthyInstanceCount;
        /**
         * 服务名
         */
        @Schema(description = "触发保护阈值")
        private String triggerFlag;
    }
}
