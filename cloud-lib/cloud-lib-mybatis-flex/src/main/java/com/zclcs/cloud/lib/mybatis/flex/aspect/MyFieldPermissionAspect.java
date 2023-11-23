package com.zclcs.cloud.lib.mybatis.flex.aspect;

import com.zclcs.cloud.lib.mybatis.flex.annotation.FieldPermission;
import com.zclcs.cloud.lib.mybatis.flex.holder.FieldPermissionHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author zclcs
 * <p>
 * 服务间接口不鉴权处理逻辑
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class MyFieldPermissionAspect implements Ordered {

    @Around("@within(fieldPermission) || @annotation(fieldPermission)")
    public Object around(ProceedingJoinPoint point, FieldPermission fieldPermission) throws Throwable {
        if (fieldPermission == null) {
            Class<?> clazz = point.getTarget().getClass();
            fieldPermission = AnnotationUtils.findAnnotation(clazz, FieldPermission.class);
        }
        assert fieldPermission != null;
        FieldPermissionHolder.getInstance().setPermissionFields(fieldPermission.value());
        Object proceed = point.proceed();
        FieldPermissionHolder.getInstance().clear();
        return proceed;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

}
