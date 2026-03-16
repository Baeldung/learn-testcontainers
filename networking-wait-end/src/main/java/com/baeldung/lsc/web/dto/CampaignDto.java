package com.baeldung.lsc.web.dto;

import java.util.HashSet;
import java.util.Set;

public record CampaignDto(
    Long id,
    String code,
    String name,
    String description,
    Set<TaskDto> tasks) {

    public CampaignDto(Long id, String code, String name, String description) {
        this(id, code, name, description, new HashSet<>());
    }
}
