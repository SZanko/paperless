package at.fhtw.swkom.paperless.services;


import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Inject))
class MinioServiceIntegrationTest {

    private final MinioService minioService;

    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() throws IOException {
        final Resource resource = new ClassPathResource("HelloWorld.pdf");

        multipartFile = new MockMultipartFile(
                Objects.requireNonNull(resource.getFilename()),
                resource.getInputStream()
        );
    }

    @Test
    void testUploadIntegrationTest() {
        System.out.println(multipartFile.getOriginalFilename());
    }
}
