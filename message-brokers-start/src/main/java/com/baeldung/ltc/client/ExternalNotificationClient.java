package com.baeldung.ltc.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.baeldung.ltc.persistence.model.Campaign;

public class ExternalNotificationClient {

    private final String baseUrl;
    private final HttpClient httpClient;

    public ExternalNotificationClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newHttpClient();
    }

    public int notifyCampaignCreated(Campaign campaign) {
        String body = String.format(
                "{\"code\":\"%s\",\"name\":\"%s\"}",
                campaign.getCode(), campaign.getName());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/notifications/campaigns"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode();
        } catch (Exception e) {
            throw new RuntimeException("Failed to send notification for campaign: " + campaign.getCode(), e);
        }
    }
}
