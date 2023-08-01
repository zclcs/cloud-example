package com.zclcs.common.minio.starter.configure;

import com.zclcs.common.minio.starter.properties.MinioProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author zclcs
 */
@AutoConfiguration
@EnableConfigurationProperties(MinioProperties.class)
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
