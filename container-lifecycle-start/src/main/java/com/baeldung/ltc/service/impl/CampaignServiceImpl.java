package com.baeldung.ltc.service.impl;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.ltc.persistence.model.Campaign;
import com.baeldung.ltc.persistence.repository.CampaignRepository;
import com.baeldung.ltc.service.CampaignService;

@Service
public class CampaignServiceImpl implements CampaignService {
    private CampaignRepository campaignRepository;

    public CampaignServiceImpl(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public Optional<Campaign> findById(Long id) {
        return campaignRepository.findById(id);
    }

    @Override
    public Campaign save(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    @Override
    public Iterable<Campaign> findAll() {
        return campaignRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        campaignRepository.deleteById(id);
    }

    @Override
    public Iterable<Campaign> findByName(String name) {
        return campaignRepository.findByNameContaining(name);
    }

}
