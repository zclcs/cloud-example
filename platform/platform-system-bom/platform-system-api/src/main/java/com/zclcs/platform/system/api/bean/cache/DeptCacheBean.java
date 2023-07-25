package com.zclcs.platform.system.api.bean.cache;

import com.zclcs.platform.system.api.bean.entity.Dept;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 部门缓存
 *
 * @author zclcs
 * @date 2023-01-10 10:39:10.151
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeptCacheBean implements Serializable {

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

    public static DeptCacheBean convertToDeptCacheBean(Dept item) {
        if (item == null) {
            return null;
        }
        DeptCacheBean result = new DeptCacheBean();
        result.setDeptId(item.getDeptId());
        result.setDeptCode(item.getDeptCode());
        result.setParentCode(item.getParentCode());
        result.setDeptName(item.getDeptName());
        result.setOrderNum(item.getOrderNum());
        return result;
    }

}