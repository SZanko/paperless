package at.fhtw.swkom.paperless.services;

import at.fhtw.swkom.paperless.persistence.repositories.DocumentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class MessageListenerTest {

    @InjectMocks
    private MessageListener messageListener; // Replace with your class name

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private Channel channel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReceiveFilename_Success() throws Exception {
        String content = "{\"text\":\"Sample Text\",\"minioFilename\":\"sample-file.txt\"}";
        long tag = 123L;
        OCRMessage ocrMessage = new OCRMessage("Sample Text", "sample-file.txt");

        when(objectMapper.readValue(content, OCRMessage.class)).thenReturn(ocrMessage);
        when(documentRepository.updateContentByContentNullAndFileNameBucketLike(anyString(), anyString())).thenReturn(1);

        messageListener.receiveFilename(content, channel, tag);

        verify(objectMapper).readValue(content, OCRMessage.class);
        verify(channel).basicAck(tag, false);
    }

    @Test
    void testReceiveFilename_JsonProcessingException() throws Exception {
        String content = "Invalid JSON";
        long tag = 123L;

        when(objectMapper.readValue(content, OCRMessage.class)).thenThrow(JsonProcessingException.class);

        messageListener.receiveFilename(content, channel, tag);

        verify(objectMapper).readValue(content, OCRMessage.class);
        verify(channel).basicNack(tag, false, false);
    }

    @Test
    void testReceiveFilename_UnexpectedException() throws Exception {
        String content = "{\"text\":\"Sample Text\",\"minioFilename\":\"sample-file.txt\"}";
        long tag = 123L;
        OCRMessage ocrMessage = new OCRMessage("Sample Text", "sample-file.txt");

        when(objectMapper.readValue(content, OCRMessage.class)).thenReturn(ocrMessage);
        when(documentRepository.updateContentByContentNullAndFileNameBucketLike(anyString(), anyString())).thenThrow(RuntimeException.class);

        messageListener.receiveFilename(content, channel, tag);

        verify(objectMapper).readValue(content, OCRMessage.class);
        verify(channel).basicNack(tag, false, true);
    }
}
