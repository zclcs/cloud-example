package com.zclcs.platform.maintenance.bean.vo;

import lombok.*;

/**
 * The result object returned by the request
 *
 * @author tjq
 * @since 2020/3/30
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PowerJobResultVo<T> {

    private boolean success;
    private T data;
    private String message;

}
