package com.baeldung.ltc.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.ltc.persistence.model.Campaign;
import com.baeldung.ltc.persistence.model.Task;
import com.baeldung.ltc.service.CampaignService;
import com.baeldung.ltc.web.dto.CampaignDto;
import com.baeldung.ltc.web.dto.TaskDto;

@RestController
@RequestMapping(value = "/campaigns")
public class CampaignController {

    private CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping(value = "/{id}")
    public CampaignDto findOne(@PathVariable Long id) {
        Campaign entity = campaignService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToDto(entity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CampaignDto create(@RequestBody CampaignDto newCampaign) {
        Campaign entity = convertToEntity(newCampaign);
        Campaign campaign = this.campaignService.save(entity);
        return this.convertToDto(campaign);
    }

    @GetMapping
    public Collection<CampaignDto> findAll(@RequestParam(name = "name", required = false) String name) {
        Iterable<Campaign> allCampaigns = (name != null && !name.isBlank())
            ? this.campaignService.findByName(name)
            : this.campaignService.findAll();
        List<CampaignDto> campaignDtos = new ArrayList<>();
        allCampaigns.forEach(p -> campaignDtos.add(convertToDto(p)));
        return campaignDtos;
    }

    @PutMapping("/{id}")
    public CampaignDto updateCampaign(@PathVariable("id") Long id, @RequestBody CampaignDto updatedCampaign) {
        Campaign campaignEntity = convertToEntity(updatedCampaign);
        return this.convertToDto(this.campaignService.save(campaignEntity));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCampaign(@PathVariable("id") Long id) {
        campaignService.delete(id);
    }

    protected CampaignDto convertToDto(Campaign entity) {
        return new CampaignDto(entity.getId(), entity.getCode(), entity.getName(), entity.getDescription(),
            entity.getTasks()
                .stream()
                .map(t -> convertTaskToDto(t))
                .collect(Collectors.toSet()));
    }

    protected Campaign convertToEntity(CampaignDto dto) {
        Campaign campaign = new Campaign(dto.code(), dto.name(), dto.description());
        if (!Objects.isNull(dto.id())) {
            campaign.setId(dto.id());
        }
        return campaign;
    }

    protected TaskDto convertTaskToDto(Task entity) {
        Campaign campaign = entity.getCampaign();
        CampaignDto campaignDto = new CampaignDto(campaign.getId(), campaign.getCode(), campaign.getName(), campaign.getDescription());
        return new TaskDto(entity.getId(), entity.getName(), entity.getDescription(), entity.getDueDate(), entity.getStatus(), campaignDto);
    }

    protected Task convertTaskToEntity(TaskDto dto) {
        Campaign campaign = null;
        if(dto.campaign() != null) {
            campaign = new Campaign(dto.campaign().code(), dto.campaign().name(), dto.campaign().description());
            campaign.setId(dto.campaign().id());
        }

        Task task = new Task(dto.name(), dto.description(), dto.dueDate(), campaign, dto.status());
        if (!Objects.isNull(dto.id())) {
            task.setId(dto.id());
        }
        return task;
    }

}