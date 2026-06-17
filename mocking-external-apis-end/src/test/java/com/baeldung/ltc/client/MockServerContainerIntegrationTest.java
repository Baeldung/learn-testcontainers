package com.baeldung.ltc.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.verify.VerificationTimes;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mockserver.MockServerContainer;
import org.testcontainers.utility.DockerImageName;

import com.baeldung.ltc.persistence.model.Campaign;

@Testcontainers
class MockServerContainerIntegrationTest {

    @Container
    static MockServerContainer mockServerContainer = new MockServerContainer(
            DockerImageName.parse("mockserver/mockserver")
                    .withTag(MockServerClient.class.getPackage().getImplementationVersion()));

    static MockServerClient mockServerClient;
    static ExternalNotificationClient notificationClient;

    @BeforeAll
    static void setUpOnce() {
        mockServerClient = new MockServerClient(
                mockServerContainer.getHost(),
                mockServerContainer.getServerPort());
        notificationClient = new ExternalNotificationClient(mockServerContainer.getEndpoint());
    }

    @BeforeEach
    void resetMockServer() {
        mockServerClient.reset();
    }

    @AfterAll
    static void tearDownOnce() {
        if (mockServerClient != null) {
            mockServerClient.close();
        }
    }

    @Test
    void givenSimulatedResponse_whenNotifyCampaignCreated_thenReturnsNotificationId() {
        mockServerClient
                .when(request()
                        .withMethod("POST")
                        .withPath("/notifications/campaigns"))
                .respond(response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(json("{\"notificationId\":\"NOTIF-001\"}")));

        Campaign campaign = new Campaign("CAMP-001", "Summer Sale", "Summer sale campaign");

        String notificationId = notificationClient.notifyCampaignCreated(campaign);

        assertThat(notificationId).isEqualTo("NOTIF-001");
    }

    @Test
    void givenCampaignNotification_whenNotifyCampaignCreated_thenSendsCorrectRequestBody() {
        mockServerClient
                .when(request()
                        .withMethod("POST")
                        .withPath("/notifications/campaigns"))
                .respond(response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(json("{\"notificationId\":\"NOTIF-001\"}")));

        Campaign campaign = new Campaign("CAMP-001", "Summer Sale", "Summer sale campaign");

        notificationClient.notifyCampaignCreated(campaign);

        mockServerClient.verify(
                request()
                        .withMethod("POST")
                        .withPath("/notifications/campaigns")
                        .withBody(json("{\"code\":\"CAMP-001\",\"name\":\"Summer Sale\"}")),
                VerificationTimes.exactly(1));
    }
}
