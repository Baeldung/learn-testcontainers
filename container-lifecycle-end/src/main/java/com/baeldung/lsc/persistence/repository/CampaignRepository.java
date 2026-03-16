package com.baeldung.lsc.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.baeldung.lsc.persistence.model.Campaign;

public interface CampaignRepository extends PagingAndSortingRepository<Campaign, Long>, CrudRepository<Campaign, Long> {
    Iterable<Campaign> findByNameContaining(String name);

}
