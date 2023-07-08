package com.zclcs.platform.system.api.bean.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 部门 Ao
 *
 * @author zclcs
 * @date 2023-01-10 10:39:10.151
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(title = "NacosServiceAo对象", description = "查询nacos服务列表")
public class NacosServiceAo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "服务名")
    private String serviceNameParam;


}