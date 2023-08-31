package com.zclcs.cloud.lib.mybatis.flex.listener;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.DesensitizedUtil;
import com.mybatisflex.annotation.SetListener;
import com.zclcs.cloud.lib.mybatis.flex.holder.DataDesensitizationHolder;
import com.zclcs.cloud.lib.mybatis.flex.holder.FieldPermissionHolder;

import java.util.List;

/**
 * 查询数据属性监听
 *
 * @author zclcs
 * @since 2023-08-31
 */
public class MySetListener implements SetListener {

    @Override
    public Object onSet(Object entity, String property, Object value) {
        if (value == null) {
            return value;
        }
        value = setFieldPermission(property, value);
        value = desensitization(property, value);
        return value;
    }

    private Object desensitization(String property, Object value) {
        if (value == null) {
            return value;
        }
        String[] desensitizationFields = DataDesensitizationHolder.getInstance().getDesensitizationFields();
        if (ArrayUtil.isNotEmpty(desensitizationFields)) {
            List<String> fields = List.of(desensitizationFields);
            if (fields.contains(property)) {
                String propertyValue = (String) value;
                switch (property) {
                    case "mobile" ->
                            value = DesensitizedUtil.desensitized(propertyValue, DesensitizedUtil.DesensitizedType.MOBILE_PHONE);
                    case "idCareNumber" ->
                            value = DesensitizedUtil.desensitized(propertyValue, DesensitizedUtil.DesensitizedType.ID_CARD);
                    case "address" ->
                            value = DesensitizedUtil.desensitized(propertyValue, DesensitizedUtil.DesensitizedType.ADDRESS);
                    case "chineseName" ->
                            value = DesensitizedUtil.desensitized(propertyValue, DesensitizedUtil.DesensitizedType.CHINESE_NAME);
                    case "email" ->
                            value = DesensitizedUtil.desensitized(propertyValue, DesensitizedUtil.DesensitizedType.EMAIL);
                    case "carLicense" ->
                            value = DesensitizedUtil.desensitized(propertyValue, DesensitizedUtil.DesensitizedType.CAR_LICENSE);
                    case "bankCard" ->
                            value = DesensitizedUtil.desensitized(propertyValue, DesensitizedUtil.DesensitizedType.BANK_CARD);
                    default -> {
                    }
                }
            }
        }
        return value;
    }

    private Object setFieldPermission(String property, Object value) {
        String[] permissionFields = FieldPermissionHolder.getInstance().getPermissionFields();
        if (ArrayUtil.isNotEmpty(permissionFields)) {
            List<String> fields = List.of(permissionFields);
            if (fields.contains(property)) {
                return null;
            }
        }
        return value;
    }

}
