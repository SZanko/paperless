package at.fhtw.swkom.paperless.services;


import io.minio.errors.*;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

    private boolean compareFileNameAfterHyphen(String expected, String actual) {
        final int hyphenIndex = actual.indexOf('-');
        if (hyphenIndex != -1) {
            String actualAfterHyphen = actual.substring(hyphenIndex + 1);
            return actualAfterHyphen.equals(expected);
        }
        return false;
    }

    @Test
    void testUploadIntegrationTest() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        final String savedAt = minioService.uploadFile(multipartFile);

        Assertions.assertTrue(compareFileNameAfterHyphen("HelloWorld.pdf", savedAt));
    }

    @Test
    void testObjectExist() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Assertions.assertFalse(minioService.isObjectExist("NotExisting.pdf"));
        final String savedAt = minioService.uploadFile(multipartFile);
        Assertions.assertTrue(minioService.isObjectExist(savedAt));
    }

    @Test
    void testDeletionIntegrationTest() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        final String savedAt = minioService.uploadFile(multipartFile);

        Assertions.assertTrue(minioService.deleteFile(savedAt));
        Assertions.assertFalse(minioService.isObjectExist(savedAt));
    }
}
