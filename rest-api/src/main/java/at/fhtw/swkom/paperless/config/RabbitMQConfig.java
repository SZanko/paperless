package at.fhtw.swkom.paperless.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "";

    @Value("${rabbitmq.ocrWorkerOutputQueue}")
    private String ocrWorkerOutputQueueName;
    @Value("${rabbitmq.ocrWorkerInputQueue}")
    private String ocrWorkerInputQueueNAME;

    public static final String ECHO_MESSAGE_COUNT_PROPERTY_NAME = "MessageCount";

    @Bean
    public Queue ocrWorkerOutputQueue() {
        return new Queue(ocrWorkerOutputQueueName, true); // Durable queue
    }

    @Bean
    public Queue ocrWorkerInputQueue() {
        return new Queue(ocrWorkerInputQueueNAME, true); // Durable queue
    }


    //@Bean
    //public ConnectionFactory connectionFactory() {
    //    CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
    //    connectionFactory.setUsername("guest");
    //    connectionFactory.setPassword("guest");
    //    return connectionFactory;
    //}

    //@Bean
    //public RabbitTemplate rabbitTemplate() {
    //    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
    //    rabbitTemplate.setDefaultReceiveQueue(ECHO_IN_QUEUE_NAME);
    //    return rabbitTemplate;
    //}

}
