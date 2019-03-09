package com.ari.msexp1.mongoDAL.DAL;

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.net.UnknownHostException;

@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableReactiveMongoRepositories(basePackages = "com.ari.msexp1.mongoDAL")
@Configuration
public class AlarmDALDbConfig extends AbstractReactiveMongoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(AlarmDALDbConfig.class);

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    public com.mongodb.reactivestreams.client.MongoClient reactiveMongoClient() {
        String uriStr = System.getenv("MONGODB_URI");
        if (uriStr == null) {
            uriStr = "mongodb";
        }
        logger.info("Creating mongo client - "+uriStr);

        return MongoClients.create(uriStr);
    }

    @Bean
    public ReactiveMongoDatabaseFactory mongoDbFactory() {
        try {
            String uriStr = System.getenv("MONGODB_URI");
            if (uriStr == null) {
                uriStr = "mongodb";
            }
            logger.info("Creating mongo client - "+uriStr);

            return new SimpleReactiveMongoDatabaseFactory(new ConnectionString(uriStr));
        } catch (UnknownHostException e) {
            logger.error(e.toString());
        }
        return null;
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoDbFactory());
    }
}
