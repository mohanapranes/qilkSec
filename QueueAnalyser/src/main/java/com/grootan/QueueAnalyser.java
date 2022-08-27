package com.grootan;

import com.grootan.moduleCallers.CriticalPorts;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;


public class QueueAnalyser {
    public static void main(String[] args) throws DockerException, IOException, DockerCertificateException, InterruptedException {
        Logger logger = LoggerFactory.getLogger(QueueAnalyser.class);
        String bootstrapServers = "127.0.0.1:9092";
        String grp_id = "urlTopics";
        String topic = "critical_ports_topic";
        //Creating consumer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, grp_id);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        //creating consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        //Subscribing
        consumer.subscribe(Arrays.asList(topic));
        //polling

        CriticalPorts criticalPorts = new CriticalPorts();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                logger.info("Key: " + record.key() + ", Value:" + record.value());
                logger.info("Partition:" + record.partition() + ",Offset:" + record.offset());
                criticalPorts.dockerCaller(record.value());
            }

        }
    }
}
