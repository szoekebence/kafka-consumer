package szoeke.bence.kafkaconsumer;

import szoeke.bence.kafkaconsumer.consumer.MyKafkaConsumer;

public class KafkaConsumerApplication {

    public static void main(String[] args) {
        MyKafkaConsumer consumer = new MyKafkaConsumer();
        consumer.consumeRecords();
    }
}
