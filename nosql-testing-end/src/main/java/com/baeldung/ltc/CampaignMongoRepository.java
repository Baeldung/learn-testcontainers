package com.baeldung.ltc;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class CampaignMongoRepository {

    private final MongoCollection<Document> collection;

    public CampaignMongoRepository(MongoClient client) {
        MongoDatabase database = client.getDatabase("ltc");
        this.collection = database.getCollection("campaigns");
    }

    public long count() {
        return collection.countDocuments();
    }

    public Document findByCode(String code) {
        return collection.find(Filters.eq("code", code)).first();
    }
}
