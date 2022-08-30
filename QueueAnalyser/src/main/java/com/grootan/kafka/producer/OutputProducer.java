package com.grootan.kafka.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class OutputProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OutputProducer.class);
    private KafkaTemplate<String, String> kafkaTemplate;
    public OutputProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void addResultInTopic(String topicName,String event){
        LOGGER.info(String.format("Result %s is sent to %s topic",event,topicName));
        kafkaTemplate.send(topicName,event);
    }
}
