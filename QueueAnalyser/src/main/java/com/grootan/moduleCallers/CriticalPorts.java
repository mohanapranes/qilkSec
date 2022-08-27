package com.grootan.moduleCallers;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ImageInfo;
import java.io.IOException;
import java.util.List;

public class CriticalPorts {
    public void dockerCaller(String url) throws IOException, DockerException, InterruptedException, DockerCertificateException {
        final DockerClient docker = DefaultDockerClient.fromEnv().build();
       // final ContainerCreation container = docker.createContainer(ContainerConfig.builder().image("50b4ae8567eb").cmd("grootan.com").build());
        docker.startContainer(container.id());
        final List<Container> containers = docker.listContainers();
        containers.forEach(System.out::println);
        String volumeContainer = containers.get(0).id();
        String logs;
        try (LogStream stream = docker.attachContainer(volumeContainer,
                DockerClient.AttachParameter.LOGS, DockerClient.AttachParameter.STDOUT,
                DockerClient.AttachParameter.STDERR, DockerClient.AttachParameter.STREAM)) {
            logs = stream.readFully();
            System.out.println(logs);
        }
    }
}
