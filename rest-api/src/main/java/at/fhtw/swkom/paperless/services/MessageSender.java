package at.fhtw.swkom.paperless.services;

import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Log
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.ocrWorkerInputQueue}")
    private String routingKey;

    public void sendToOCRWorker(String filename) {
        rabbitTemplate.convertAndSend(routingKey, filename);
        log.info("Sent "+filename+" to OCR worker");
    }
}
