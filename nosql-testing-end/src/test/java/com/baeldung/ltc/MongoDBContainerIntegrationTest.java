package com.baeldung.ltc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Testcontainers
class MongoDBContainerIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7");

    static MongoClient client;
    static CampaignMongoRepository repository;
    MongoCollection<Document> collection;

    @BeforeAll
    static void setUpClient() {
        client = MongoClients.create(mongoDBContainer.getConnectionString());
        repository = new CampaignMongoRepository(client);
    }

    @BeforeEach
    void setUp() {
        MongoDatabase database = client.getDatabase("ltc");
        collection = database.getCollection("campaigns");
        collection.drop();
        collection.insertMany(
            List.of(
                new Document("code", "C1").append("name", "Campaign 1")
                    .append("description", "Description of Campaign 1"),
                new Document("code", "C2").append("name", "Campaign 2")
                    .append("description", "About Campaign 2"),
                new Document("code", "C3").append("name", "Campaign 3")
                    .append("description", "About Campaign 3")
            )
        );
    }

    @Test
    void givenCampaignDocuments_whenCounting_thenReturnsExpectedCount() {
        long count = repository.count();

        assertEquals(3, count);
    }

    @Test
    void givenCampaignDocuments_whenFindingByCode_thenReturnsCorrectDocument() {
        Document result = repository.findByCode("C1");

        assertEquals("Campaign 1", result.getString("name"));
        assertEquals("Description of Campaign 1", result.getString("description"));
    }

    @AfterAll
    static void tearDown() {
        client.close();
    }
}
