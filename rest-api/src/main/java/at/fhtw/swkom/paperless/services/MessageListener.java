package at.fhtw.swkom.paperless.services;

import at.fhtw.swkom.paperless.persistence.repositories.DocumentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import io.minio.MinioClient;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log
public class MessageListener {

    private final ObjectMapper objectMapper;
    private final DocumentRepository documentRepository;

    @RabbitListener(queues = "${rabbitmq.ocrWorkerOutputQueue}")
    public void receiveFilename(String content, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        final OCRMessage ocrMessage;
        try {

            ocrMessage = objectMapper.readValue(content, OCRMessage.class);
            log.info("OCR message received: " + ocrMessage);


            channel.basicAck(tag, false);
            int result = documentRepository.updateContentByContentNullAndFileNameBucketLike(ocrMessage.getText(), ocrMessage.getMinioFilename());
            log.info("Result of Update of content: " + result);
        } catch (JsonProcessingException e) {
            log.severe("Failed to parse OCR message. Content: " + content + " " + Arrays.toString(e.getStackTrace()));


            try {
                channel.basicNack(tag, false, false);
            } catch (IOException ioException) {
                log.severe("Failed to nack message: " + ioException.getMessage() + " " + ioException);
            }
        } catch (Exception e) {
            log.severe("Unexpected error occurred " + e);


            try {
                channel.basicNack(tag, false, true);
            } catch (IOException ioException) {
                log.severe("Failed to nack message: " + ioException.getMessage() + " " + ioException);
            }
        }
    }
}
