package com.zclcs.cloud.lib.mybatis.flex.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.BiPredicate;

/**
 * @author zclcs
 */
public class PredicateUtil {

    private PredicateUtil() {
    }

    public static BiPredicate<LocalDateTime, LocalDateTime> localDateTimeBothNotNull = (from, to) -> (from != null && to != null);

    public static BiPredicate<LocalDate, LocalDate> localDateBothNotNull = (from, to) -> (from != null && to != null);

}
