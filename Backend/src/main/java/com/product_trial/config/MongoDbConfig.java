package com.product_trial.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * Configuration class for setting up MongoDB in the application.
 * This class defines beans for MongoDB properties, MongoTemplate,
 * and the MongoDatabaseFactory, facilitating interaction with the MongoDB database.
 */
@Configuration
public class MongoDbConfig {

    /**
     * Creates a MongoProperties bean for "Product" database.
     *
     * @return a MongoProperties instance containing the MongoDB configuration.
     */
    @Primary
    @Bean(name = "mongoProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.product")
    public MongoProperties getMongoProperties() {
        return new MongoProperties();
    }

    /**
     * Creates a MongoTemplate bean for "Product" database.
     *
     * @return a MongoTemplate instance for MongoDB operations.
     */
    @Primary
    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDatabaseFactory(getMongoProperties()));
    }

    /**
     * Creates a MongoDatabaseFactory bean for "Product" database.
     *
     * @param mongo the MongoProperties used to configure the MongoDatabaseFactory.
     *
     * @return a MongoDatabaseFactory instance for MongoDB connections.
     */
    @Primary
    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(MongoProperties mongo) {
        return new SimpleMongoClientDatabaseFactory(
                mongo.getUri()
        );
    }

}
