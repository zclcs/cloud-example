package com.zclcs.common.export.excel.starter.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * 校验错误信息
 *
 * @author zclcs
 * @since 2021/8/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageVo {

    /**
     * 行号
     */
    private Long lineNum;

    /**
     * 错误信息
     */
    private Set<String> errors = new HashSet<>();

    public ErrorMessageVo(Set<String> errors) {
        this.errors = errors;
    }

    public ErrorMessageVo(String error) {
        HashSet<String> objects = new HashSet<>();
        objects.add(error);
        this.errors = objects;
    }

}
