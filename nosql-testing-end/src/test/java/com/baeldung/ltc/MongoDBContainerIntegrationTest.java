package com.baeldung.ltc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Testcontainers
class MongoDBContainerIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7");

    static MongoClient client;
    MongoCollection<Document> collection;

    @BeforeAll
    static void setUpClient() {
        client = MongoClients.create(mongoDBContainer.getConnectionString());
    }

    @BeforeEach
    void setUp() {
        MongoDatabase database = client.getDatabase("testdb");
        collection = database.getCollection("students");
        collection.drop();
    }

    @Test
    void givenStudentDocuments_whenCounting_thenReturnsExpectedCount() {
        collection.insertMany(List.of(
            new Document("name", "Alice").append("age", 22),
            new Document("name", "Bob").append("age", 25),
            new Document("name", "Charlie").append("age", 19)
        ));

        long count = collection.countDocuments();

        assertEquals(3, count);
    }

    @Test
    void givenStudentDocuments_whenQueryingByName_thenReturnsCorrectDocument() {
        collection.insertMany(List.of(
            new Document("name", "Alice").append("age", 22),
            new Document("name", "Bob").append("age", 25),
            new Document("name", "Charlie").append("age", 19)
        ));

        Document result = collection.find(Filters.eq("name", "Alice")).first();

        assertEquals("Alice", result.getString("name"));
        assertEquals(22, result.getInteger("age"));
    }

    @AfterAll
    static void tearDown() {
        client.close();
    }
}
