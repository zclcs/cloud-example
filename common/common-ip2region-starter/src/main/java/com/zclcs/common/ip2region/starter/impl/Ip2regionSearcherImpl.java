package com.zclcs.common.ip2region.starter.impl;

import com.zclcs.common.ip2region.starter.configuration.Ip2regionProperties;
import com.zclcs.common.ip2region.starter.core.Ip2regionSearcher;
import com.zclcs.common.ip2region.starter.core.IpInfo;
import com.zclcs.common.ip2region.starter.core.IpV6Searcher;
import com.zclcs.common.ip2region.starter.core.Searcher;
import com.zclcs.common.ip2region.starter.utils.IpInfoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * ip2region 初始化
 *
 * @author zclcs
 */
@RequiredArgsConstructor
public class Ip2regionSearcherImpl implements InitializingBean, DisposableBean, Ip2regionSearcher {
    private final ResourceLoader resourceLoader;
    private final Ip2regionProperties properties;
    private Searcher searcher;
    private IpV6Searcher ipV6Searcher;

    @Override
    public IpInfo memorySearch(long ip) {
        try {
            return IpInfoUtil.toIpInfo(searcher.search(ip));
        } catch (IOException e) {
            throw new RuntimeException("查询ip异常");
        }
    }

    @Override
    public IpInfo memorySearch(String ip) {
        // 1. ipv4
        String[] ipV4Part = IpInfoUtil.getIpV4Part(ip);
        if (ipV4Part.length == 4) {
            return memorySearch(Searcher.getIpAdder(ipV4Part));
        }
        // 2. 非 ipv6
        if (!ip.contains(":")) {
            throw new IllegalArgumentException("invalid ip address `" + ip + "`");
        }
        // 3. ipv6
        return ipV6Searcher.query(ip);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Resource resource = resourceLoader.getResource(properties.getDbFileLocation());
        try (InputStream inputStream = resource.getInputStream()) {
            this.searcher = Searcher.newWithBuffer(StreamUtils.copyToByteArray(inputStream));
        }
        Resource ipV6Resource = resourceLoader.getResource(properties.getIpv6dbFileLocation());
        try (InputStream inputStream = ipV6Resource.getInputStream()) {
            this.ipV6Searcher = IpV6Searcher.newWithBuffer(StreamUtils.copyToByteArray(inputStream));
        }
    }

    @Override
    public void destroy() throws Exception {
        if (this.searcher != null) {
            this.searcher.close();
        }
    }

}
