package szoeke.bence.kafkaconsumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class MyKafkaConsumer {

    private final Logger logger;
    private static final String TOPIC_NAME = "streams-output";
    private final Properties properties;

    public MyKafkaConsumer() {
        logger = LoggerFactory.getLogger(MyKafkaConsumer.class);
        properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "MyConsumerGroup");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
//        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    }

    public void consumeRecords() {
        try (KafkaConsumer<Long, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Collections.singleton(TOPIC_NAME));
            doConsuming(consumer);
        }
    }

    private void doConsuming(KafkaConsumer<Long, String> consumer) {
        while (true) {
            ConsumerRecords<Long, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<Long, String> record : records) {
                logger.info(String.format("Record read successfully with key: %s", record.key()));
            }
        }
    }
}
