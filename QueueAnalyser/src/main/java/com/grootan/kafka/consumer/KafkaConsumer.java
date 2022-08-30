package com.grootan.kafka.consumer;

import com.grootan.moduleCallers.DockerImages;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    @Autowired
    DockerImages dockerImages;
    @KafkaListener(topics = "critical_ports_topic", groupId = "url_receiver_topics")
    public void criticalPortsConsumer(String url) throws DockerException, IOException, DockerCertificateException, InterruptedException {
        LOGGER.info(String.format("URL received  from critical ports topic->%s",url));
        String dockerImageName = "iammpw/first_repo:qliksec_critical_ports";
        dockerImages.dockerCaller(dockerImageName,url);
    }
    @KafkaListener(topics = "ddosTopic",groupId = "url_receiver-topics")
    public void ddosConsumer(String url) throws DockerException, IOException, DockerCertificateException, InterruptedException {
        LOGGER.info(String.format("URL received from ddos_topics->%s",url));
        String dockerImageName = "thivakarv/qliksecmodules1:ddos";
        dockerImages.dockerCaller(dockerImageName,url);
    }
    @KafkaListener(topics = "ssrfTopic",groupId = "url_receiver-topics")
    public void ssrfConsumer(String url) throws DockerException, IOException, DockerCertificateException, InterruptedException {
        LOGGER.info(String.format("URL received from ssrf_topic->%s",url));
        String dockerImageName = "thivakarv/qliksecmodules1:ssrf";
        dockerImages.dockerCaller(dockerImageName,url);
    }
    @KafkaListener(topics = "clickjackTopic",groupId = "url_receiver-topics")
    public void clickjackConsumer(String url) throws DockerException, IOException, DockerCertificateException, InterruptedException {
        LOGGER.info(String.format("URL received from clickjack_topic->%s",url));
        String dockerImageName = "thivakarv/qliksecmodules1:clickjack";
        dockerImages.dockerCaller(dockerImageName,url);
    }
}
