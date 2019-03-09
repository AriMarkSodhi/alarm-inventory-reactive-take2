package com.ari.msexp1;


import com.ari.msexp1.mongoDAL.DAL.AlarmDAL;
import com.ari.msexp1.mongoDAL.DAL.AlarmDefinitionDAL;
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

import java.util.*;

@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@SpringBootApplication
public class AlarmPopulationApp implements CommandLineRunner {
    private static final Logger LOG = Logger.getLogger(AlarmPopulationApp.class);
    private final AlarmDefinitionDAL alarmDefinitionDAL;
    private final AlarmDAL alarmDAL;

    @Autowired
    public AlarmPopulationApp(AlarmDefinitionDAL alarmDefinitionDAL, AlarmDAL alarmDAL) {
        this.alarmDefinitionDAL = alarmDefinitionDAL;
        this.alarmDAL = alarmDAL;
    }

    @Autowired
    org.springframework.data.mongodb.core.ReactiveMongoTemplate mongoTemplate;


    public static void main(String[] args) {
        SpringApplication.run(AlarmPopulationApp.class, args);
    }

    @Override
    public void run(String... args) {

        // cleanup any existing data
        if (mongoTemplate.getMongoDatabase() != null)
            mongoTemplate.getMongoDatabase().drop();
        /**
         *
         * The following is somewhat artifical - using curl to rest REST API - this is to populate the service with data. Using existing repositories to persist the test data.
         */
//
//        for (int i =0; i<100; i++) {
//            alarmDefinitionDAL.save(Mono.just(new AlarmDefinition(
//                    "AlarmType_"+i, "", "Alarm_"+i, Severity.values()[new Random().nextInt(Severity.values().length)],
//                    "Alarm text", Boolean.TRUE, Arrays.asList("1/1/eth1","1/1/eth2"))));
//        }
        Flux<AlarmDefinition> alarmDefns = Flux.generate(() -> 0,
                (i, sink) -> {
                    if (i == 100) sink.complete();
                    sink.next(new AlarmDefinition(
                            "AlarmType_"+i, "", "Alarm_"+i, Severity.values()[new Random().nextInt(Severity.values().length)],
                            "Alarm text", Boolean.TRUE, Arrays.asList("1/1/eth1","1/1/eth2")));
                    return i + 1;
                });

        alarmDefinitionDAL.save(alarmDefns);

        LOG.info("Getting all alarmdefinition data from MongoDB: \n{}");
        alarmDefinitionDAL.findAll().parallel(2).runOn(Schedulers.parallel()).subscribe(value -> LOG.info(value));
        LOG.info("Getting paginated alarmdefinition data from MongoDB: \n{}");
        alarmDefinitionDAL.findAll(PageRequest.of(0, 2)).subscribe(value -> LOG.info(value));

//        List<Alarm> alarms = new ArrayList<>();
//        for (int i =0; i<100; i++) {
//            alarms.add(new Alarm("alarm_id_"+i, "1/1/eth1", "AlarmType_"+i, "", "Alarm_"+i, "",
//                    "", Boolean.FALSE, new Date(), Severity.values()[new Random().nextInt(Severity.values().length)],
//                    "Alarm text", Arrays.asList(new AlarmStatusChange(Severity.values()[new Random().nextInt(Severity.values().length)], "AlarmText-New"))
//                    , Arrays.asList(new OperatorStateChange("Op1", OperatorState.values()[new Random().nextInt(OperatorState.values().length)], "Update"))));
//        }
//        alarmDAL.save(Flux.fromStream(alarms.stream()));

        Flux<Alarm> alarms = Flux.generate(() -> 0,
                (i, sink) -> {
                    if (i == 100) sink.complete();
                    sink.next(new Alarm("alarm_id_"+i, "1/1/eth1", "AlarmType_"+i, "", "Alarm_"+i, "",
                            "", Boolean.FALSE, new Date(), Severity.values()[new Random().nextInt(Severity.values().length)],
                            "Alarm text", Arrays.asList(new AlarmStatusChange(Severity.values()[new Random().nextInt(Severity.values().length)], "AlarmText-New"))
                            , Arrays.asList(new OperatorStateChange("Op1", OperatorState.values()[new Random().nextInt(OperatorState.values().length)], "Update"))));
                    return i + 1;
                });

        alarmDAL.save(alarms);

        LOG.info("Getting all alarm data from MongoDB: \n{}); ");
        alarmDAL.findAll().parallel(2).runOn(Schedulers.parallel()).subscribe(value -> LOG.info(value));
        LOG.info("Getting paginated alarm data from MongoDB: \n{}");
        alarmDAL.findAll(PageRequest.of(0, 2)).subscribe(value -> LOG.info(value));
    }
}


