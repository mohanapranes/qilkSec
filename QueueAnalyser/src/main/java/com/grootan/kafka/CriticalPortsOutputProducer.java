package com.grootan.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class CriticalPortsOutputProducer {
    private final String TOPIC_NAME = "critical_ports_output_topic";
    private static final Logger LOGGER = LoggerFactory.getLogger(CriticalPortsOutputProducer.class);
    private KafkaTemplate<String, String> kafkaTemplate;
    public CriticalPortsOutputProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void addResultInTopic(String event){
        LOGGER.info(String.format("Url sent -> %s",event));
        kafkaTemplate.send(TOPIC_NAME,event);
    }
}
