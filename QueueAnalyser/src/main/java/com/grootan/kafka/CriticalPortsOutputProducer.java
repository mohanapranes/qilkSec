package com.grootan.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Properties;

public class CriticalPortsOutputProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CriticalPortsOutputProducer.class);

    public void addResultInTopic(String url){
        Properties props = new Properties();

        //Assign localhost id
        props.put("bootstrap.servers","localhost:9092");
        props.put("acks","all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer
                <String, String>(props);
        String topicName = "critical_ports_output_topic";
            producer.send(new ProducerRecord<String, String>(topicName,url));
        LOGGER.info("Message sent successfully");
        producer.close();
    }

}
