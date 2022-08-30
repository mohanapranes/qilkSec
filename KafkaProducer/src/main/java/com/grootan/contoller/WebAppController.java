package com.grootan.contoller;

import com.grootan.kafka.KafkaProducer;
import com.grootan.kafka.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ApiController")
public class WebAppController {
    @Autowired
    private KafkaProducer kafkaProducer;

    private KafkaTemplate<String,User> kafkaTemplate;

    @PostMapping("/post")
    public String postWebName(@RequestBody User user){

        kafkaProducer.addUrlInTopic(user);
        return "value added in kafka successfully ";
    }
}
