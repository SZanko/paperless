package at.fhtw.swkom.paperless.services;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MinioService {

    private final MinioClient minioClient;
    @Value("${minio.bucket}")
    private String bucketName;

    public String uploadFile(MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        final int tenMB = 10 * 1024 * 1024;
        final ObjectWriteResponse response = minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(file.getName())
                        .contentType(file.getContentType())
                        .stream(file.getInputStream(), -1, tenMB)
                        .build()
        );

        return "Dummy";
    }
}
