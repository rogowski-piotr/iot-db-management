package iotdbmanagement;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@Configuration
@EnableScheduling
@EnableAsync
public class QueueConsumer {
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    private static final String MEASUREMENT_QUEUE_NAME = "measurements_queue";
    private Channel channel;
    private DefaultConsumer consumer;

    @Autowired
    public QueueConsumer() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(MEASUREMENT_QUEUE_NAME, false, false, false, null);
        } catch (IOException | TimeoutException exception) {
            log.info(exception.toString());
        }

        consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(
                    String consumerTag,
                    Envelope envelope,
                    AMQP.BasicProperties properties,
                    byte[] body) throws IOException {

                String message = new String(body, "UTF-8");
                log.info(message);
            }
        };
    }

    private void consume() {
        try {
            channel.basicConsume(MEASUREMENT_QUEUE_NAME, true, consumer);
        } catch (IOException exception) {
            log.info(exception.getMessage());
        }
    }

    @Async
    @Scheduled(cron = "0/10 * * * * ?", zone = "Europe/Warsaw")
    public void runOnePerTenSeconds() {
        consume();
    }
}
