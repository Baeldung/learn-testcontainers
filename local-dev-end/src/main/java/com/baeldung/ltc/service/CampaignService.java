package com.baeldung.ltc.service;

import com.baeldung.ltc.persistence.model.Campaign;
import java.util.Optional;

public interface CampaignService {

    Optional<Campaign> findById(Long id);

    Campaign save(Campaign campaign);

    Iterable<Campaign> findAll();

    void delete(Long id);

    Iterable<Campaign> findByName(String name);
}
