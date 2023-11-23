package com.zclcs.cloud.lib.aop.spel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

/**
 * @author zclcs
 */
@Getter
@RequiredArgsConstructor
public class ExpressionRootObject {
    private final Method method;

    private final Object[] args;

    private final Object target;

    private final Class<?> targetClass;

    private final Method targetMethod;
}
