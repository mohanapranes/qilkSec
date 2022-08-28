package com.grootan.moduleCallers;

import com.grootan.config.KafkaTopicConfig;
import com.grootan.kafka.CriticalPortsOutputProducer;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class CriticalPorts {

    @Autowired
    private CriticalPortsOutputProducer outputProducer = new CriticalPortsOutputProducer();
    KafkaTopicConfig kafkaTopicConfig= new KafkaTopicConfig();
    NewTopic newTopic = kafkaTopicConfig.newTopicConfig();
    public void dockerCaller(String url) throws IOException, DockerException, InterruptedException, DockerCertificateException {
        final DockerClient docker = DefaultDockerClient.fromEnv().build();
        ImageInfo imageInfo = docker.inspectImage("50b4ae8567eb");
        final ContainerCreation container = docker.createContainer(ContainerConfig.builder().image(imageInfo.id()).cmd("grootan.com").build());
        docker.startContainer(container.id());
        final List<Container> containers = docker.listContainers();
        containers.forEach(System.out::println);
        String volumeContainer = containers.get(0).id();



        String logs;
        try (LogStream stream = docker.attachContainer(volumeContainer,
                DockerClient.AttachParameter.LOGS, DockerClient.AttachParameter.STDOUT,
                DockerClient.AttachParameter.STDERR, DockerClient.AttachParameter.STREAM)) {
            logs = stream.readFully();
            outputProducer.addResultInTopic(logs);
            System.out.println(logs);
        }
    }
}
