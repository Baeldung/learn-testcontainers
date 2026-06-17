package com.baeldung.ltc.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.ltc.persistence.model.Campaign;
import com.baeldung.ltc.persistence.model.Task;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
public class TaskRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:17"))
        .withInitScript("init-script.sql");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

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
