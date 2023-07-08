package com.zclcs.platform.system.api.bean.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

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
@Schema(title = "XxlJobJobLogDetailVo", description = "XxlJobJobLogDetailVo")
public class XxlJobJobLogDetailVo {

    @Schema(description = "开始行")
    private Integer fromLineNum;

    @Schema(description = "结束行")
    private Integer toLineNum;

    @Schema(description = "日志")
    private String logContent;

    @Schema(description = "是否结束")
    private Boolean end;

}
