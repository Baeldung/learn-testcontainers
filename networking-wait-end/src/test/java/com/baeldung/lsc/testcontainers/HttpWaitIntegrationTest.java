package com.baeldung.lsc.testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

// Requires a running Docker environment
@Testcontainers
class HttpWaitIntegrationTest {

    @Container
    static GenericContainer<?> nginx = new GenericContainer<>("nginx:alpine")
      .withExposedPorts(80)
      .waitingFor(Wait.forHttp("/"));

    @Test
    void givenHttpWait_whenContainerStarts_thenNginxIsReady() {
        assertTrue(nginx.isRunning());
    }
}
