package com.zclcs.platform.system.api.bean.vo;

import com.mybatisflex.annotation.Column;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典 Vo
 *
 * @author zclcs
 * @since 2023-03-06 10:56:35.591
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DictVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 数据key（唯一值）
     */
    private String name;

    /**
     * 数据key（唯一值）
     */
    @Column(ignore = true)
    private String dictName;


}