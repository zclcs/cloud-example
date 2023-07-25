package com.zclcs.platform.system.api.bean.vo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户 Vo
 *
 * @author zclcs
 * @date 2023-01-10 10:39:34.182
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserTokenVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户token
     */
    private String token;

    /**
     * 当前会话 token 剩余有效时间（单位: 秒，返回 -1 代表永久有效）
     */
    private Long expire;

    /**
     * 用户信息
     */
    private LoginVo userinfo;


}