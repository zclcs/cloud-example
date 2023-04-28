package com.zclcs.platform.system.configure;

import com.zclcs.platform.system.properties.MinioProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zclcs
 */
@Configuration
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
