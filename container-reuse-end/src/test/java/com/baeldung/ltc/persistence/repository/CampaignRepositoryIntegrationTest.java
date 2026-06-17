package com.baeldung.ltc.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.baeldung.ltc.AbstractIntegrationTest;
import com.baeldung.ltc.persistence.model.Campaign;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CampaignRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    CampaignRepository campaignRepository;

    @Test
    public void givenNewCampaign_whenSaved_thenSuccess() {
        Campaign newCampaign = new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1");
        assertNotNull(campaignRepository.save(newCampaign));
    }

    @Test
    public void givenCampaignCreated_whenFindById_thenSuccess() {
        Campaign newCampaign = new Campaign("CTEST-2", "Test Campaign 2", "Description for campaign CTEST-2");
        campaignRepository.save(newCampaign);

        Optional<Campaign> retrievedCampaign = campaignRepository.findById(newCampaign.getId());
        assertEquals(newCampaign.getCode(), retrievedCampaign.get().getCode());
    }
}
