package com.baeldung.ltc.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.ltc.AbstractIntegrationTest;
import com.baeldung.ltc.persistence.model.Campaign;
import com.baeldung.ltc.persistence.model.Task;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CampaignRepository campaignRepository;

    @Test
    public void givenNewTask_whenSaved_thenSuccess() {
        Campaign newCampaign = campaignRepository.save(new Campaign("CTEST-3", "Test Campaign 3", "Description for campaign CTEST-3"));
        Task newTask = new Task("First Task", "First Task description", LocalDate.now(), newCampaign);
        assertThat(taskRepository.save(newTask)).isNotNull();
    }

    @Test
    public void givenTaskCreated_whenFindById_thenSuccess() {
        Campaign newCampaign = campaignRepository.save(new Campaign("CTEST-4", "Test Campaign 4", "Description for campaign CTEST-4"));
        Task newTask = new Task("Second Task", "Second Task description", LocalDate.now(), newCampaign);
        taskRepository.save(newTask);

        Optional<Task> retreivedTask = taskRepository.findById(newTask.getId());
        assertThat(retreivedTask.get()).isEqualTo(newTask);
    }
}
