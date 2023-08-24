package com.zclcs.platform.system.api.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import com.zclcs.platform.system.api.bean.vo.UserVo;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class UserExcelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @ExcelProperty(value = "用户名")
    private String username;

    /**
     * 用户昵称
     */
    @ExcelProperty(value = "用户昵称")
    private String realName;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话")
    private String mobile;

    /**
     * 状态 @@system_user.status
     */
    @ExcelProperty(value = "状态")
    private String status;

    /**
     * 性别 @@system_user.gender
     */
    @ExcelProperty(value = "性别")
    private String gender;

    public static UserExcelVo convertToUserExcelVo(UserVo item) {
        if (item == null) {
            return null;
        }
        return UserExcelVo.builder()
                .username(item.getUsername())
                .realName(item.getRealName())
                .email(item.getEmail())
                .mobile(item.getMobile())
                .status(DictCacheUtil.getDictTitle("system_user.status", item.getStatus()))
                .gender(DictCacheUtil.getDictTitle("system_user.gender", item.getGender()))
                .build();
    }

    public static List<UserExcelVo> convertToList(List<UserVo> items) {
        if (items == null) {
            return null;
        }
        List<UserExcelVo> result = new ArrayList<>();
        for (UserVo item : items) {
            UserExcelVo userExcelVo = convertToUserExcelVo(item);
            if (userExcelVo != null) {
                result.add(userExcelVo);
            }
        }
        return result;
    }


}