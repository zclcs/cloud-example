package com.zclcs.cloud.lib.mybatis.flex.aspect;

import com.zclcs.cloud.lib.mybatis.flex.annotation.DataPermission;
import com.zclcs.cloud.lib.mybatis.flex.holder.DataPermissionHolder;
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
public class MyDataPermissionAspect implements Ordered {

    @Around("@within(dataPermission) || @annotation(dataPermission)")
    public Object around(ProceedingJoinPoint point, DataPermission dataPermission) throws Throwable {
        if (dataPermission == null) {
            Class<?> clazz = point.getTarget().getClass();
            dataPermission = AnnotationUtils.findAnnotation(clazz, DataPermission.class);
        }
        assert dataPermission != null;
        DataPermissionHolder.getInstance().setPermissionColumn(dataPermission.value());
        Object proceed = point.proceed();
        DataPermissionHolder.getInstance().clear();
        return proceed;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

}
