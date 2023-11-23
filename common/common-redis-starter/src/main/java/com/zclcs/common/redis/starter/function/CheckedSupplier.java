package com.zclcs.common.redis.starter.function;

import jakarta.annotation.Nullable;

import java.io.Serializable;

/**
 * 受检的 Supplier
 *
 * @author L.cm
 */
@FunctionalInterface
public interface CheckedSupplier<T> extends Serializable {

    /**
     * Run the Supplier
     *
     * @return T
     * @throws Throwable CheckedException
     */
    @Nullable
    T get() throws Throwable;

}
