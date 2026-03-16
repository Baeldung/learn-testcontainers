package com.baeldung.ltc.service;

import java.util.Optional;

import com.baeldung.ltc.persistence.model.Campaign;

public interface CampaignService {
    Optional<Campaign> findById(Long id);

    Campaign save(Campaign campaign);

    Iterable<Campaign> findAll();

    void delete(Long id);

    Iterable<Campaign> findByName(String name);
}
