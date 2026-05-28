package com.baeldung.lsc.testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class HttpWaitIntegrationTest {

    @Container
    static GenericContainer<?> nginx = new GenericContainer<>("nginx:alpine")
      .withExposedPorts(80)
      .waitingFor(Wait.forHttp("/"));

    @Test
    void givenHttpWait_whenContainerStarts_thenNginxRespondsWithOK() throws Exception {
        URL url = URI.create("http://" + nginx.getHost() + ":" + nginx.getMappedPort(80) + "/").toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assertEquals(200, connection.getResponseCode());
    }
}
