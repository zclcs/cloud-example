package com.zclcs.cloud.lib.fegin.configure;

import com.zclcs.cloud.lib.fegin.handler.MySentinelInvocationHandler;
import feign.Contract;
import feign.Feign;
import feign.InvocationHandlerFactory;
import feign.Target;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 支持自动降级注入
 *
 * @author zclcs
 * @since 2020/6/9
 */
public final class MySentinelFeign {

    private MySentinelFeign() {

    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends Feign.Builder implements ApplicationContextAware {

        private ApplicationContext applicationContext;

        private FeignClientFactory feignClientFactory;

        @Override
        public Feign.Builder invocationHandlerFactory(InvocationHandlerFactory invocationHandlerFactory) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Builder contract(Contract contract) {
            this.contract = contract;
            return this;
        }

        @Override
        public Feign build() {
            super.invocationHandlerFactory(new InvocationHandlerFactory() {
                @Override
                public InvocationHandler create(Target target, Map<Method, MethodHandler> dispatch) {

                    // 查找 FeignClient 上的 降级策略
                    FeignClient feignClient = AnnotationUtils.findAnnotation(target.type(), FeignClient.class);
                    Class<?> fallback = null;
                    if (feignClient != null) {
                        fallback = feignClient.fallback();
                    }
                    Class<?> fallbackFactory = null;
                    if (feignClient != null) {
                        fallbackFactory = feignClient.fallbackFactory();
                    }

                    String beanName = null;
                    if (feignClient != null) {
                        beanName = feignClient.contextId();
                    }
                    if (!StringUtils.hasText(beanName)) {
                        if (feignClient != null) {
                            beanName = feignClient.name();
                        }
                    }

                    Object fallbackInstance;
                    FallbackFactory<?> fallbackFactoryInstance;
                    if (void.class != fallback) {
                        fallbackInstance = getFromContext(beanName, "fallback", fallback, target.type());
                        return new MySentinelInvocationHandler(target, dispatch,
                                new FallbackFactory.Default<>(fallbackInstance));
                    }

                    if (void.class != fallbackFactory) {
                        fallbackFactoryInstance = (FallbackFactory<?>) getFromContext(beanName, "fallbackFactory",
                                fallbackFactory, FallbackFactory.class);
                        return new MySentinelInvocationHandler(target, dispatch, fallbackFactoryInstance);
                    }
                    return new MySentinelInvocationHandler(target, dispatch);
                }

                private Object getFromContext(String name, String type, Class<?> fallbackType, Class<?> targetType) {
                    Object fallbackInstance = feignClientFactory.getInstance(name, fallbackType);
                    if (fallbackInstance == null) {
                        throw new IllegalStateException(String.format(
                                "No %s instance of type %s found for feign client %s", type, fallbackType, name));
                    }

                    if (!targetType.isAssignableFrom(fallbackType)) {
                        throw new IllegalStateException(String.format(
                                "Incompatible %s instance. Fallback/fallbackFactory of type %s is not assignable to %s for feign client %s",
                                type, fallbackType, targetType, name));
                    }
                    return fallbackInstance;
                }
            });

            return super.build();
        }

        private Object getFieldValue(Object instance, String fieldName) {
            Field field = ReflectionUtils.findField(instance.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
            }
            try {
                if (field != null) {
                    return field.get(instance);
                }
            } catch (IllegalAccessException e) {
                // ignore
            }
            return null;
        }

        @Override
        public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
            this.feignClientFactory = (FeignClientFactory) this.applicationContext.getBean(FeignClientFactory.class);
        }

    }

}