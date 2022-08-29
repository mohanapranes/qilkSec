package com.grootan.kafka;

import com.grootan.moduleCallers.CriticalPorts;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CriticalPortsConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CriticalPortsConsumer.class);
    @Autowired
    CriticalPorts criticalPorts;
    @KafkaListener(topics = "critical_ports_topic", groupId = "url_receiver_topics")
    public void consumer(String url) throws DockerException, IOException, DockerCertificateException, InterruptedException {
        LOGGER.info(String.format("URL received ->%s",url));
        String dockerImageName = "iammpw/first_repo:qliksec_critical_ports";
        criticalPorts.dockerCaller(dockerImageName,url);
    }
}
