package com.baeldung.lsc.web.dto;

import java.time.LocalDate;

import com.baeldung.lsc.persistence.model.TaskStatus;

public record TaskDto(
    Long id,
    String name,
    String description,
    LocalDate dueDate,
    TaskStatus status,
    CampaignDto campaign) {
}
