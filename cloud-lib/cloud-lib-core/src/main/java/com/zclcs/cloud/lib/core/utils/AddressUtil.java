package com.zclcs.cloud.lib.core.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.constant.CommonCore;
import lombok.experimental.UtilityClass;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 根据 IP获取地址
 *
 * @author zclcs
 */
@UtilityClass
public class AddressUtil {

    public String getCityInfo(String ip) {
        DbSearcher searcher = null;
        try {
            String dbPath = Objects.requireNonNull(AddressUtil.class.getResource("/ip2region/ip2region.db")).getPath();
            File file = new File(dbPath);
            if (!file.exists()) {
                String tmpDir = System.getProperties().getProperty(CommonCore.JAVA_TEMP_DIR);
                dbPath = tmpDir + "ip.db";
                file = new File(dbPath);
                try (InputStream resourceAsStream = AddressUtil.class.getClassLoader().getResourceAsStream("classpath:ip2region/ip2region.db")) {
                    if (resourceAsStream != null) {
                        IoUtil.copy(resourceAsStream, new FileOutputStream(file));
                    }
                }
            }
            DbConfig config = new DbConfig();
            searcher = new DbSearcher(config, file.getPath());
            Method method = searcher.getClass().getMethod("btreeSearch", String.class);
            DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
            return dataBlock.getRegion();
        } catch (Exception e) {
            return StrUtil.EMPTY;
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (IOException e) {
                }
            }
        }
    }
}