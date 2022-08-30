package com.grootan.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;

public class OutputProducerHandler {
  @Autowired
  OutputProducer outputProducer;

  public void distributeResultToTopic(String imageName, String result) {
    if (imageName.equals("iammpw/first_repo:qliksec_critical_ports")) {
      String topicName = "critical_ports_output_topic";
      outputProducer.addResultInTopic(topicName,result);
    } else if (imageName.equals("thivakarv/qliksecmodules1:ddos")) {
      String topicName= "ddosOutputTopic";
      outputProducer.addResultInTopic(topicName,result);
    }
    else if(imageName.equals("thivakarv/qliksecmodules1:ssrf")){
      String topicName= "ssrfOutputTopic";
      outputProducer.addResultInTopic(topicName,result);

    } else if (imageName.equals("thivakarv/qliksecmodules1:clickjack")) {
      String topicName= "clickjackOutputTopic";
      outputProducer.addResultInTopic(topicName,result);

    }
  }
}
