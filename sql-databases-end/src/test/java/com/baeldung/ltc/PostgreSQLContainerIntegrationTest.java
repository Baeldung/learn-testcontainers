package com.baeldung.ltc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.baeldung.ltc.persistence.model.Campaign;
import com.baeldung.ltc.persistence.repository.CampaignJdbcRepository;

@Testcontainers
class PostgreSQLContainerIntegrationTest {

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine")
            .withUsername("test_user")
            .withPassword("test_password")
            .withDatabaseName("test_db")
            .withInitScript("init-script.sql");

    @Test
    void givenSeededDatabase_whenFindingCampaignsWithPendingTasks_thenReturnsTwoCampaigns() throws SQLException {
        CampaignJdbcRepository repository = new CampaignJdbcRepository(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword());

        List<Campaign> campaigns = repository.findCampaignsHavingPendingTasks();

        assertEquals(2, campaigns.size());
    }

    @Test
    void givenSeededDatabase_whenCountingTasksForCampaign_thenReturnsCorrectCount() throws SQLException {
        CampaignJdbcRepository repository = new CampaignJdbcRepository(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword());

        int count = repository.countTasksForCampaign("C1");

        assertEquals(3, count);
    }
}
