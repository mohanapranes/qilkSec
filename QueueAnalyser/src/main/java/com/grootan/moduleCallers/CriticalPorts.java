package com.grootan.moduleCallers;

import com.grootan.kafka.CriticalPortsOutputProducer;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class CriticalPorts {

    @Autowired
    private final CriticalPortsOutputProducer outputProducer = new CriticalPortsOutputProducer();

    public void dockerCaller(String url) throws IOException, DockerException, InterruptedException, DockerCertificateException {
        final DockerClient docker = DefaultDockerClient.fromEnv().build();

        final ContainerCreation container = docker.createContainer(ContainerConfig.builder().image("50b4ae8567eb").cmd(url).build());
        docker.startContainer(container.id());
        String volumeContainer = container.id();
        String logs;
        try (LogStream stream = docker.attachContainer(volumeContainer,
                DockerClient.AttachParameter.LOGS, DockerClient.AttachParameter.STDOUT,
                DockerClient.AttachParameter.STDERR, DockerClient.AttachParameter.STREAM)) {
            logs = stream.readFully();
         //   outputProducer.addResultInTopic(logs);
            System.out.println(logs);
        }
    }

}
