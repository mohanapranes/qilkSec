package com.grootan.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    private KafkaTemplate <String,User> kafkaTemplate;
    private NewTopic newTopic;

    public KafkaProducer(KafkaTemplate<String, User> kafkaTemplate, NewTopic newTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.newTopic = newTopic;
    }

    public void addUrlInTopic(User user){
        for(int id:user.getId()){
            if(id==1){
                LOGGER.info("User sent to CriticalPortsTopic");
                kafkaTemplate.send("criticalPortsTopic",user);
            }
            else if(id==2){
                LOGGER.info("User sent to CriticalPortsTopic");
                kafkaTemplate.send("ssrfTopic",user);
            }
            else if(id==3){
                LOGGER.info("User sent to CriticalPortsTopic");
                kafkaTemplate.send("ddosTopic",user);
            }
            else if(id==4){
                LOGGER.info("User sent to CriticalPortsTopic");
                kafkaTemplate.send("clickjackTopic",user);
            }

        }
//        LOGGER.info("URL send "+url);
//        kafkaTemplate.send(topicName,url);
    }
}
