package com.baeldung.ltc.persistence.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.baeldung.ltc.persistence.model.Campaign;

public class CampaignJdbcRepository {

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public CampaignJdbcRepository(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public List<Campaign> findCampaignsHavingPendingTasks() throws SQLException {
        String sql = """
            SELECT DISTINCT c.id, c.code, c.name, c.description
            FROM campaign c
            JOIN task t ON t.campaign_id = c.id
            WHERE t.status = 0
            """;
        List<Campaign> results = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Campaign campaign = new Campaign(
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getString("description")
                );
                campaign.setId(rs.getLong("id"));
                results.add(campaign);
            }
        }
        return results;
    }

    public int countTasksForCampaign(String campaignCode) throws SQLException {
        String sql = """
            SELECT COUNT(t.id)
            FROM task t
            JOIN campaign c ON t.campaign_id = c.id
            WHERE c.code = ?
            """;
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, campaignCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }
}
