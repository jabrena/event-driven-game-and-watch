package com.jab.streams;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public abstract class AbstractIntegrationTest {

    static final DockerImageName RABBITMQ_IMAGE = DockerImageName.parse("rabbitmq:3.8.9-management-alpine");

    @Container
    public static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer(RABBITMQ_IMAGE);

    @DynamicPropertySource
    static void rabbitMQProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitMQContainer::getContainerIpAddress);
        registry.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
    }

}

