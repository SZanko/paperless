package at.fhtw.swkom.paperless.services;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.*;
import io.minio.errors.*;
import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

@Service
@Log
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MinioService {

    private final Random random = new Random();
    private final MinioClient minioClient;
    @Value("${minio.bucket}")
    private String bucketName;


    private String getValidFileName(@Nonnull MultipartFile file) {
        if (file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank()) {
            return file.getOriginalFilename();
        } else {
            return file.getName();
        }
    }

    public String uploadFile(MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        final int tenMB = 10 * 1024 * 1024;
        final String newFileName = UUID.randomUUID() + getValidFileName(file);
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

    public boolean isObjectExist(String name) {
        try {
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(name).build());
            return true;
        } catch (ErrorResponseException e) {
            log.warning(e.getMessage());
            return false;
        } catch (Exception e) {
            log.warning(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
