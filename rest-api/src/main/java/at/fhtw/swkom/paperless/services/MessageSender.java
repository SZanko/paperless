package at.fhtw.swkom.paperless.services;

import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendFilename(String filename) {
        rabbitTemplate.convertAndSend("files.queue", filename);
    }
}
