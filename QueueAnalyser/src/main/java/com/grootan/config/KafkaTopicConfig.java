package com.grootan.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {


    //  @Value("${spring.kafka.topic.name}")
    private String topicName = "critical_ports_output_topic";

    @Bean
    public NewTopic newTopicConfig() {
        return TopicBuilder.name(topicName).build();
    }


}
