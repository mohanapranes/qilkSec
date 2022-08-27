package com.grootan.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class CriticalPortsProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CriticalPortsProducer.class);
    private KafkaTemplate <String,String> kafkaTemplate;
    private NewTopic newTopic;

    public CriticalPortsProducer(KafkaTemplate<String, String> kafkaTemplate, NewTopic newTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.newTopic = newTopic;
    }

    public void addUrlInTopic(String url){
        LOGGER.info("URL send "+url);
        kafkaTemplate.send(newTopic.name(),url);
    }
}
