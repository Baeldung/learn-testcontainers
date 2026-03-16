package com.baeldung.ltc.rest.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.baeldung.ltc.web.dto.CampaignDto;

@SpringBootTest
public class CampaignRestAPILiveTest {

    private static final String BASE_URL = "http://localhost:8080/campaigns";
    private static final Random random = new Random();

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void givenCampaignExists_whenGet_thenSuccess() {
        ResponseEntity<CampaignDto> response = restTemplate.getForEntity(BASE_URL + "/1", CampaignDto.class);

        assertSame(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }

    @Test
    public void givenNewCampaign_whenCreated_thenSuccess() {
        int index = random.nextInt(10000);
        String code = "C" +  index;
        String name = "Campaign " + index;
        String description = "Description of Campaign " + index;
        CampaignDto newCampaign = new CampaignDto(null, code, name, description);
        ResponseEntity<Void> response = restTemplate.postForEntity(BASE_URL, newCampaign, Void.class);

        assertSame(HttpStatus.CREATED, response.getStatusCode());
    }

}
