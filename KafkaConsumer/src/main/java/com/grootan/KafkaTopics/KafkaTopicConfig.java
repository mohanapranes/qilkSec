package com.grootan.KafkaTopics;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public TopicBuilder newWebAppTopicConfig(String topicName){
        return TopicBuilder.name(topicName);
    }
}
