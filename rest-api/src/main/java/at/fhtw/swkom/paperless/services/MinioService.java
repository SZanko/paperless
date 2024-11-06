package at.fhtw.swkom.paperless.services;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
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
import java.util.Random;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MinioService {

    private final Random random = new Random();
    private final MinioClient minioClient;
    @Value("${minio.bucket}")
    private String bucketName;


    public String uploadFile(MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        final int tenMB = 10 * 1024 * 1024;
        final String newFileName = random.nextInt(tenMB) + "-" + file.getName();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(newFileName)
                        .contentType("application/pdf")
                        .stream(file.getInputStream(), -1, tenMB)
                        .build()
        );

        return newFileName;
    }

    public boolean deleteFile(String fileNameInBucket) {

        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileNameInBucket)
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            return false;
            //throw new RuntimeException(e);
        }
        return true;
    }
}
