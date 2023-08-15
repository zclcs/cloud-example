package com.zclcs.cloud.lib.mybatis.plus.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

/**
 * @author zclcs
 */
@UtilityClass
public class QueryWrapperUtil {

    /**
     * 开始时间
     */
    public static final String BEGIN_OF_DAY = "00:00:00";

    /**
     * 结束时间
     */
    public static final String END_OF_DAY = "23:59:59";

    /**
     * 市行政代码结尾
     */
    public static final String CITY_CODE_END = "00";
    /**
     * 省
     */
    public static final String PROVINCE = "0";
    /**
     * 市
     */
    public static final String CITY = "1";
    /**
     * 县
     */
    public static final String COUNTY = "2";
    /**
     * 省行政代码结尾
     */
    public static final String PROVINCE_CODE_END = "0000";

    public <T> QueryWrapper<T> likeAreaCodeNotBlank(QueryWrapper<T> queryWrapper, String column, String param) {
        Optional.ofNullable(param).filter(StrUtil::isNotBlank).ifPresent(s -> {
            switch (getNameByAreaCode(s)) {
                case PROVINCE -> queryWrapper.likeRight(column, s);
                case CITY -> queryWrapper.like(column, s);
                case COUNTY -> queryWrapper.likeLeft(column, s);
                default -> {
                }
            }
        });
        return queryWrapper;
    }

    /**
     * 模糊查询 （不使用覆盖索引情况下是会走 全表扫描的）
     *
     * @param queryWrapper 条件构造器
     * @param column       表字段
     * @param param        参数
     * @param <T>          泛型
     * @return 条件构造器
     */
    public <T> QueryWrapper<T> likeNotBlank(QueryWrapper<T> queryWrapper, String column, String param) {
        queryWrapper.like(StrUtil.isNotBlank(param), column, param);
        return queryWrapper;
    }

    /**
     * 介于查询 两个参数都不能为空或空字符串
     *
     * @param queryWrapper 条件构造器
     * @param column       表字段
     * @param paramFrom    开始
     * @param paramTo      结束
     * @param <T>          泛型
     * @return 条件构造器
     */
    public <T> QueryWrapper<T> betweenNotBlank(QueryWrapper<T> queryWrapper, String column, String paramFrom, String paramTo) {
        queryWrapper.between(StrUtil.isNotBlank(paramFrom) && StrUtil.isNotBlank(paramTo), column, paramFrom, paramTo);
        return queryWrapper;
    }

    /**
     * 介于时间段查询 两个参数都不能为空或空字符串 默认开始日期加上 00:00:00 结束日期加上 23:59:59
     *
     * @param queryWrapper 条件构造器
     * @param column       表字段
     * @param paramFrom    开始
     * @param paramTo      结束
     * @param <T>          泛型
     * @return 条件构造器
     */
    public <T> QueryWrapper<T> betweenDateAddTimeNotBlank(QueryWrapper<T> queryWrapper, String column, String paramFrom, String paramTo) {
        queryWrapper.between(StrUtil.isNotBlank(paramFrom) && StrUtil.isNotBlank(paramTo), column, paramFrom + StrUtil.SPACE + BEGIN_OF_DAY, paramTo + StrUtil.SPACE + END_OF_DAY);
        return queryWrapper;
    }

    /**
     * 介于时间段查询 两个参数都不能为空或空字符串 默认开始日期加上 00:00:00 结束日期加上 23:59:59
     *
     * @param queryWrapper 条件构造器
     * @param column       表字段
     * @param paramFrom    开始
     * @param paramTo      结束
     * @param <T>          泛型
     * @return 条件构造器
     */
    public <T> QueryWrapper<T> betweenDateAddTimeNotBlank(QueryWrapper<T> queryWrapper, String column, LocalDate paramFrom, LocalDate paramTo) {
        if (paramFrom != null && paramTo != null) {
            queryWrapper.between(column, paramFrom.atTime(LocalTime.MIN),
                    paramTo.atTime(LocalTime.MAX));
        }
        return queryWrapper;
    }

    public <T> QueryWrapper<T> likeNotNull(QueryWrapper<T> queryWrapper, String column, Object param) {
        queryWrapper.like(param != null, column, param);
        return queryWrapper;
    }

    public <T> QueryWrapper<T> likeRightNotBlank(QueryWrapper<T> queryWrapper, String column, String param) {
        queryWrapper.likeRight(StrUtil.isNotBlank(param), column, param);
        return queryWrapper;
    }

    public <T> QueryWrapper<T> likeRightNotNull(QueryWrapper<T> queryWrapper, String column, Object param) {
        queryWrapper.likeRight(param != null, column, param);
        return queryWrapper;
    }

    public <T> QueryWrapper<T> likeLeftNotBlank(QueryWrapper<T> queryWrapper, String column, String param) {
        queryWrapper.likeLeft(StrUtil.isNotBlank(param), column, param);
        return queryWrapper;
    }

    public <T> QueryWrapper<T> likeLeftNotNull(QueryWrapper<T> queryWrapper, String column, Object param) {
        queryWrapper.likeLeft(param != null, column, param);
        return queryWrapper;
    }

    public <T> QueryWrapper<T> eqNotBlank(QueryWrapper<T> queryWrapper, String column, String param) {
        queryWrapper.eq(StrUtil.isNotBlank(param), column, param);
        return queryWrapper;
    }

    public <T> QueryWrapper<T> eqNotNull(QueryWrapper<T> queryWrapper, String column, Object param) {
        queryWrapper.eq(param != null, column, param);
        return queryWrapper;
    }

    public <T> QueryWrapper<T> inNotEmpty(QueryWrapper<T> queryWrapper, String column, Collection<?> param) {
        queryWrapper.in(CollectionUtil.isNotEmpty(param), column, param);
        return queryWrapper;
    }

    /**
     * 根据行政区划代码返回 省 市 县
     *
     * @param areaCode 行政区划代码
     * @return 省 0 市 1 县 2
     */
    public String getNameByAreaCode(String areaCode) {
        if (StrUtil.endWith(areaCode, PROVINCE_CODE_END)) {
            return PROVINCE;
        }
        if (StrUtil.endWith(areaCode, CITY_CODE_END)) {
            return CITY;
        }
        return COUNTY;
    }

}
