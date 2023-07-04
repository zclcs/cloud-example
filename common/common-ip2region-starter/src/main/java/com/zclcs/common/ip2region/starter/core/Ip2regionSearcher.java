package com.zclcs.common.ip2region.starter.core;

import com.zclcs.common.ip2region.starter.utils.IpInfoUtil;
import org.springframework.lang.Nullable;

import java.util.function.Function;

/**
 * ip 搜索器
 *
 * @author zclcs
 */
public interface Ip2regionSearcher {

    /**
     * ip 位置 搜索
     *
     * @param ip ip
     * @return 位置
     */
    @Nullable
    IpInfo memorySearch(long ip);

    /**
     * ip 位置 搜索
     *
     * @param ip ip
     * @return 位置
     */
    @Nullable
    IpInfo memorySearch(String ip);

    /**
     * 读取 ipInfo 中的信息
     *
     * @param ip       ip
     * @param function Function
     * @return 地址
     */
    @Nullable
    default String getInfo(long ip, Function<IpInfo, String> function) {
        return IpInfoUtil.readInfo(memorySearch(ip), function);
    }

    /**
     * 读取 ipInfo 中的信息
     *
     * @param ip       ip
     * @param function Function
     * @return 地址
     */
    @Nullable
    default String getInfo(String ip, Function<IpInfo, String> function) {
        return IpInfoUtil.readInfo(memorySearch(ip), function);
    }

    /**
     * 获取地址信息
     *
     * @param ip ip
     * @return 地址
     */
    @Nullable
    default String getAddress(long ip) {
        return getInfo(ip, IpInfo::getAddress);
    }

    /**
     * 获取地址信息
     *
     * @param ip ip
     * @return 地址
     */
    @Nullable
    default String getAddress(String ip) {
        return getInfo(ip, IpInfo::getAddress);
    }

    /**
     * 获取地址信息包含 isp
     *
     * @param ip ip
     * @return 地址
     */
    @Nullable
    default String getAddressAndIsp(long ip) {
        return getInfo(ip, IpInfo::getAddressAndIsp);
    }

    /**
     * 获取地址信息包含 isp
     *
     * @param ip ip
     * @return 地址
     */
    @Nullable
    default String getAddressAndIsp(String ip) {
        return getInfo(ip, IpInfo::getAddressAndIsp);
    }

}
