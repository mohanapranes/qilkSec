package com.grootan.contoller;

import com.grootan.kafka.CriticalPortsProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ApiController")
public class webAppController {
    @Autowired
    private CriticalPortsProducer criticalPortsProducer;
    @PostMapping("/post")
    public String postWebName(@RequestBody String url){
        criticalPortsProducer.addUrlInTopic(url);
        return "value added in kafka successfully ";
    }
}
