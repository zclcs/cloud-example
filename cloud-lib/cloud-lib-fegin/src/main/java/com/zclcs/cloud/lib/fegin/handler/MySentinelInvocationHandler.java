package com.zclcs.cloud.lib.fegin.handler;

import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import feign.InvocationHandlerFactory;
import feign.Target;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

import static feign.Util.checkNotNull;

/**
 * 支持自动降级注入
 *
 * @author zclcs
 * @since 2020/6/9
 */
@Slf4j
public class MySentinelInvocationHandler implements InvocationHandler {

    public static final String EQUALS = "equals";

    public static final String HASH_CODE = "hashCode";

    public static final String TO_STRING = "toString";

    private final Target<?> target;

    private final Map<Method, InvocationHandlerFactory.MethodHandler> dispatch;

    private FallbackFactory<?> fallbackFactory;

    private Map<Method, Method> fallbackMethodMap;

    public MySentinelInvocationHandler(Target<?> target, Map<Method, InvocationHandlerFactory.MethodHandler> dispatch,
                                       FallbackFactory<?> fallbackFactory) {
        this.target = checkNotNull(target, "target");
        this.dispatch = checkNotNull(dispatch, "dispatch");
        this.fallbackFactory = fallbackFactory;
        this.fallbackMethodMap = toFallbackMethod(dispatch);
    }

    public MySentinelInvocationHandler(Target<?> target, Map<Method, InvocationHandlerFactory.MethodHandler> dispatch) {
        this.target = checkNotNull(target, "target");
        this.dispatch = checkNotNull(dispatch, "dispatch");
    }

    static Map<Method, Method> toFallbackMethod(Map<Method, InvocationHandlerFactory.MethodHandler> dispatch) {
        Map<Method, Method> result = new LinkedHashMap<>();
        for (Method method : dispatch.keySet()) {
            method.setAccessible(true);
            result.put(method, method);
        }
        return result;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        switch (method.getName()) {
            case EQUALS -> {
                try {
                    Object otherHandler = args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;
                    return equals(otherHandler);
                } catch (IllegalArgumentException e) {
                    return false;
                }
            }
            case HASH_CODE -> {
                return hashCode();
            }
            case TO_STRING -> {
                return toString();
            }
        }

        Object result;
        InvocationHandlerFactory.MethodHandler methodHandler = this.dispatch.get(method);
        // only handle by HardCodedTarget
        if (target instanceof Target.HardCodedTarget) {
            Target.HardCodedTarget<?> hardCodedTarget = (Target.HardCodedTarget) target;
            try {
                result = methodHandler.invoke(args);
            } catch (Throwable ex) {
                if (fallbackFactory != null) {
                    try {
                        return fallbackMethodMap.get(method).invoke(fallbackFactory.create(ex), args);
                    } catch (IllegalAccessException e) {
                        // shouldn't happen as method is public due to being an
                        // interface
                        throw new AssertionError(e);
                    } catch (InvocationTargetException e) {
                        throw new AssertionError(e.getCause());
                    }
                } else {
                    // 若是R类型 执行自动降级返回R
                    if (BaseRsp.class == method.getReturnType()) {
                        log.error("feign 服务间调用异常", ex);
                        return RspUtil.message(ex.getLocalizedMessage());
                    } else {
                        throw ex;
                    }
                }
            }
        } else {
            // other target type using default strategy
            result = methodHandler.invoke(args);
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }

    @Override
    public String toString() {
        return target.toString();
    }

}
