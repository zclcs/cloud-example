package com.zclcs.cloud.lib.mybatis.flex.aspect;

import com.zclcs.cloud.lib.mybatis.flex.annotation.DataDesensitization;
import com.zclcs.cloud.lib.mybatis.flex.holder.DataDesensitizationHolder;
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
public class MyDataDesensitizationAspect implements Ordered {

    @Around("@within(dataDesensitization) || @annotation(dataDesensitization)")
    public Object around(ProceedingJoinPoint point, DataDesensitization dataDesensitization) throws Throwable {
        if (dataDesensitization == null) {
            Class<?> clazz = point.getTarget().getClass();
            dataDesensitization = AnnotationUtils.findAnnotation(clazz, DataDesensitization.class);
        }
        assert dataDesensitization != null;
        DataDesensitizationHolder.getInstance().setDesensitizationFields(dataDesensitization.value());
        Object proceed = point.proceed();
        DataDesensitizationHolder.getInstance().clear();
        return proceed;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

}
