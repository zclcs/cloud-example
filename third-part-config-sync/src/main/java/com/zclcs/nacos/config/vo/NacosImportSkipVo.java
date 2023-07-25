package com.zclcs.nacos.config.vo;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * nacos配置导入返回
 *
 * @author zclcs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NacosImportSkipVo {

    /**
     * 成功数量
     */
    private Integer succCount;

    /**
     * 跳过配置详情
     */
    private List<SkipData> skipData;

    /**
     * 跳过数量
     */
    private Integer skipCount;

    /**
     * 配置详情
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class SkipData {
        private String dataId;
        private String group;
    }
}
