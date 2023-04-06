package com.zclcs.platform.system.biz.configure;

import com.zclcs.common.core.properties.GlobalProperties;
import com.zclcs.platform.system.biz.properties.MinioProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zclcs
 */
@Configuration
@EnableConfigurationProperties(GlobalProperties.class)
@RequiredArgsConstructor
public class MinioConfigure {

    private final MinioProperties minioProperties;

    /**
     * 获取MinioClient
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getRootUser(), minioProperties.getRootPassword())
                .build();
    }

}
