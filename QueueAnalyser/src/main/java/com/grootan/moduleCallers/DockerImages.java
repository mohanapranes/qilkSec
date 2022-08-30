package com.grootan.moduleCallers;

import com.grootan.kafka.producer.OutputProducerHandler;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class DockerImages {

    private static final Logger LOGGER = LoggerFactory.getLogger(DockerImages.class);
    @Autowired
    private OutputProducerHandler outputProducer;

    public void dockerCaller(String dockerImageName,String url) throws IOException, DockerException, InterruptedException, DockerCertificateException {
        final DockerClient docker = DefaultDockerClient.fromEnv().build();
     //   docker.pull("iammpw/first_repo:qliksec_critical_ports");
        final List<Image> allImages = docker.listImages();
        String id = getImageIdIfAvailable(docker,dockerImageName);
        if(id.isEmpty()){
            docker.pull(dockerImageName);
            id = getImageIdIfAvailable(docker,dockerImageName);
        }
        final ContainerCreation container = docker.createContainer(ContainerConfig.builder().image(id).cmd(url).build());
        docker.startContainer(container.id());
        String containerId = container.id();
        String logs;
        try (LogStream stream = docker.attachContainer(containerId,
                DockerClient.AttachParameter.LOGS, DockerClient.AttachParameter.STDOUT,
                DockerClient.AttachParameter.STDERR, DockerClient.AttachParameter.STREAM)) {
            logs = stream.readFully();
            outputProducer.distributeResultToTopic(dockerImageName,logs);
        }
        docker.removeContainer(containerId);
        docker.close();
    }
    private String getImageIdIfAvailable(DockerClient docker,String dockerImageName) throws DockerException, InterruptedException {
        final List<Image> allImages = docker.listImages();
        AtomicReference<String> id = new AtomicReference<>(new String());
        allImages.forEach(itr->{
            if(Objects.requireNonNull(itr.repoTags()).contains(dockerImageName)){
                id.set(itr.id());
            }
        });
        return id.get();
    }

}
