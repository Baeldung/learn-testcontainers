package com.baeldung.ltc.web.dto;

import com.baeldung.ltc.persistence.model.TaskStatus;
import java.time.LocalDate;

public record TaskDto(
                      Long id,
                      String name,
                      String description,
                      LocalDate dueDate,
                      TaskStatus status,
                      CampaignDto campaign) {
}
