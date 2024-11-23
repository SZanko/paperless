package at.fhtw.swkom.paperless.services;

import at.fhtw.swkom.paperless.persistence.repositories.DocumentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.MinioClient;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log
public class MessageListener {

    private final ObjectMapper objectMapper;
    private final DocumentRepository documentRepository;

    @RabbitListener(queues = "${rabbitmq.ocrWorkerOutputQueue}")
    public void receiveFilename(String content) {
        final OCRMessage ocrMessage;
        try {
            ocrMessage = objectMapper.readValue(content, OCRMessage.class);
        } catch (JsonProcessingException e) {
            log.severe("Can't parse ocrMessage \n" + e.getMessage());
            throw new RuntimeException(e);
        }
        int result = documentRepository.updateContentByContentNullAndFileNameBucketLike(ocrMessage.getText(), ocrMessage.getMinioFilename());
    }
}
