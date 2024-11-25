package at.fhtw.swkom.paperless.services;


import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Inject))
class MessageSenderIntegrationTest {
    @Mock
    private RabbitTemplate rabbitTemplate;

    private final MessageSender messageSender;

    @Test
    void testSendToOCRWorker(){
        final String filename = "testfile.pdf";
        messageSender.sendToOCRWorker(filename);

    }
}
