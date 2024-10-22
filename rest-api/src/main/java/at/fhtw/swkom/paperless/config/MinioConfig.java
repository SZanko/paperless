package at.fhtw.swkom.paperless.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.credentials.accesskey}")
    private String accessKey;
    @Value("${minio.credentials.secretkey}")
    private String secretKey;
    @Value("${minio.endpoint}")
    private String endpoint;

    @Bean
    public MinioClient minioClient() {
        return new MinioClient
                .Builder()
                .endpoint(endpoint)
                .credentials(secretKey, accessKey)
                .build();
    }
}
