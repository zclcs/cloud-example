package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 部门 CacheVo
 *
 * @author zclcs
 * @since 2023-09-01 19:53:38.826
 */
@Data
public class DeptCacheVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 上级部门编码
     */
    private String parentCode;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 排序
     */
    private Double orderNum;


}