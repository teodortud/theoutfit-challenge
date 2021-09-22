package com.theoutfit.challenge.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {
    @Bean
    public MongoClient mongo() {
        ConnectionString connectionString =
                new ConnectionString("mongodb+srv://teodor:teodor@firstcluster.9yxlu.mongodb.net/ordermetrics?retryWrites=true&w=majority");

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        MongoClient mongoClient = MongoClients.create(mongoClientSettings);

        return mongoClient;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "ordermetrics");
    }
}
