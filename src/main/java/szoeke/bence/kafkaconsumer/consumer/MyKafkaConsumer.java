package szoeke.bence.kafkaconsumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class MyKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyKafkaConsumer.class);
    private static final String BOOTSTRAP_SERVER_ENV_VAR = "BOOTSTRAP_SERVER";
    private static final String TOPIC_NAME_ENV_VAR = "TOPIC_NAME";
    private static final String TOPIC_NAME = System.getenv(TOPIC_NAME_ENV_VAR);
    private final Properties properties;
    private long sequenceNumber = 0L;

    public MyKafkaConsumer() {
        properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv(BOOTSTRAP_SERVER_ENV_VAR));
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "MyConsumerGroup");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    }

    public void consumeRecords() {
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Collections.singleton(TOPIC_NAME));
            doConsuming(consumer);
        }
    }

    private void doConsuming(KafkaConsumer<String, String> consumer) {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                doLogging(record);
            }
        }
    }

    private void doLogging(ConsumerRecord<String, String> record) {
        if (record.value().length() < 10) {
            LOGGER.info(String.format("Record read successfully with sequenceNuber: %d", ++sequenceNumber));
        } else {
            LOGGER.info(String.format("Record read successfully with sequenceNuber: %d", ++sequenceNumber));//FIXME
        }
    }
}
