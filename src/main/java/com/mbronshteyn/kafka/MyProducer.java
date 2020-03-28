package com.mbronshteyn.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import reactor.core.publisher.Flux;

import java.util.Properties;

public class MyProducer {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("enable.auto.commit", "true");

        Producer<String, String> producer = new KafkaProducer<>(props);

        Flux.range(0, 100)
                .parallel()
                .subscribe(i -> {
                    producer.send(
                            new ProducerRecord<String, String>(
                                    "my-topic", "abc", Integer.toString(i)));
                });

        producer.close();
    }
}
