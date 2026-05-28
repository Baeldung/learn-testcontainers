package com.baeldung.ltc.web.dto;

import java.time.LocalDate;

import com.baeldung.ltc.persistence.model.TaskStatus;

public record TaskDto(
    Long id,
    String name,
    String description,
    LocalDate dueDate,
    TaskStatus status,
    CampaignDto campaign) {
}
