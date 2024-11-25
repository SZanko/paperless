package at.fhtw.swkom.paperless.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MessageSenderTest {
    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private MessageSender messageSender;

    @Value("${rabbitmq.ocrWorkerInputQueue}")
    private String routingKey;

    private Logger logger = Logger.getLogger(MessageSender.class.getName());
    private List<String> logMessages = new ArrayList<>();

    @BeforeEach
    void setUp(){
        Handler testHandler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                logMessages.add(record.getMessage());
            }

            @Override
            public void flush() {
                // No-op
            }

            @Override
            public void close() throws SecurityException {
                // No-op
            }
        };

        logger.addHandler(testHandler);
    }

    @Test
    void testSendToOCRWorker(){
        final String filename = "testfile.pdf";
        messageSender.sendToOCRWorker(filename);

        assertFalse(logMessages.isEmpty());
        assertTrue(logMessages.contains("Sent "+filename+" to OCR worker"));
    }
}
