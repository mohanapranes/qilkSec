package com.grootan.kafka;

import com.grootan.moduleCallers.CriticalPorts;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CriticalPortsConsumer {

    @Autowired
    CriticalPorts criticalPorts;
    @KafkaListener(topics = "critical_ports_topic", groupId = "url_receiver_topics")
    public void consumer(String url) throws DockerException, IOException, DockerCertificateException, InterruptedException {
        criticalPorts.dockerCaller(url);
    }
}
