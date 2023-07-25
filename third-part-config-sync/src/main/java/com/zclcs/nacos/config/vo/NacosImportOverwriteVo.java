package com.zclcs.nacos.config.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * nacos导入返回
 *
 * @author zclcs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NacosImportOverwriteVo {

    /**
     * 成功数量
     */
    private Integer succCount;

    /**
     * 失败数量
     */
    private Integer skipCount;
}
