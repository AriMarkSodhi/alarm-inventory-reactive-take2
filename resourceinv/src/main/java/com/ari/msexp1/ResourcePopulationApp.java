package com.ari.msexp1;


import com.ari.msexp1.mongoDAL.DAL.ResourceDAL;
import com.ari.msexp1.mongoDAL.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@SpringBootApplication
public class ResourcePopulationApp implements CommandLineRunner {
    private static final Logger LOG = Logger.getLogger(ResourcePopulationApp.class);
    private final ResourceDAL resourceDAL;
    @Autowired
    org.springframework.data.mongodb.core.ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public ResourcePopulationApp(ResourceDAL resourceDAL) {
        this.resourceDAL = resourceDAL;
    }

    public static void main(String[] args) {
        SpringApplication.run(ResourcePopulationApp.class, args);
    }

    @Override
    public void run(String... args) {
        try {

            // cleanup any existing data
            if (mongoTemplate.getMongoDatabase() != null) {
                LOG.info("dropping db :" + mongoTemplate.getMongoDatabase().getName());
                mongoTemplate.getMongoDatabase().drop();
            }

            Flux<Resource> resources = Flux.generate(() -> 0,
                    (i, sink) -> {
                        if (i == 100) sink.complete();
                        sink.next(new Resource(i.toString(), "res" + 1, (i - 1 > 0) ? i.toString() : "0", new ArrayList<>(), new Date(), "desc",
                                EquipmentClass.values()[new Random().nextInt(EquipmentClass.values().length)],
                                1, "rev1", "v1.0", "v1.0", "Mfg", "model",
                                new Date(new Date().getTime() - (10 * 24 * 60 * 60 * 1000)),
                                Boolean.FALSE, OperatorState.values()[new Random().nextInt(OperatorState.values().length)],
                                UsageState.values()[new Random().nextInt(UsageState.values().length)],
                                AlarmState.values()[new Random().nextInt(AlarmState.values().length)]));
                        return i + 1;
                    });

            LOG.info("Saving resource data from MongoDB); ");
            resourceDAL.save(resources);
        } catch (Exception e) {
            LOG.error("Failed to Save resources", e);
        }

        resourceDAL.findAll().parallel(2).runOn(Schedulers.parallel()).subscribe(value -> LOG.info(value.getName()));
        resourceDAL.findAll(PageRequest.of(0, 2)).subscribe(value -> LOG.info(value.getName()));
    }
}


