package com.grootan.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {


  //  @Value("${spring.kafka.topic.name}")
    private String topicName="critical_ports_topic";
    @Bean
    public NewTopic newWebAppTopicConfig(){
        return TopicBuilder.name(topicName).build();
    }
}
