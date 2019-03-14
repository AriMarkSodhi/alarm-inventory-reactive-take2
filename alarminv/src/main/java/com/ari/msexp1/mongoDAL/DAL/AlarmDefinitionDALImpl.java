package com.ari.msexp1.mongoDAL.DAL;

import com.ari.msexp1.mongoDAL.model.AlarmDefinition;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AlarmDefinitionDALImpl implements AlarmDefinitionDAL {
    private static final Logger logger = LoggerFactory.getLogger(AlarmDefinitionDALImpl.class);
    private static final String COLLECTION_NAME = "alarmdefinition";
    private static final String NAME = "name";
    private static final String ALARM_DEFINITION_ID = "alarmDefinitionId";
    private static final String RESOURCES = "resources";
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public AlarmDefinitionDALImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }
    @Override
    public Mono<AlarmDefinition> save(Mono<AlarmDefinition> alarmDefinitionMono) {
        return Mono.from(alarmDefinitionMono).doOnNext(alarmDefn -> reactiveMongoTemplate.save(alarmDefn, COLLECTION_NAME));
    }
    @Override
    public Flux<AlarmDefinition> save(Publisher<AlarmDefinition> alarmDefinitionPublisher) {
        List<AlarmDefinition> alarmDefnList = new ArrayList<>();
        Flux.from(alarmDefinitionPublisher).subscribe(
                alarm -> {
                    alarmDefnList.add(reactiveMongoTemplate.save(alarm, COLLECTION_NAME).block());
                },
                err -> logger.error("AlarmDef received error"+ err), //--> onError
                () -> logger.info("AlarmDef got Completed event")    //--> onComplete
        );
        return Flux.fromIterable(alarmDefnList);
    }
    @Override
    public Flux<AlarmDefinition> findAll() {
        return reactiveMongoTemplate.findAll(AlarmDefinition.class, COLLECTION_NAME);
    }

    @Override
    public Flux<AlarmDefinition> findAll(Pageable pageable) {
        Query query = new Query();
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.limit(pageable.getPageSize());
        return reactiveMongoTemplate.find(query, AlarmDefinition.class, COLLECTION_NAME);
    }
    @Override
    public Mono<AlarmDefinition> findOneByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where(NAME).is(name));
        return reactiveMongoTemplate.findOne(query, AlarmDefinition.class, COLLECTION_NAME);
    }

    @Override
    public Mono<AlarmDefinition> findOneById(String alarmDefinitionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(ALARM_DEFINITION_ID).is(alarmDefinitionId));
        return reactiveMongoTemplate.findOne(query, AlarmDefinition.class, COLLECTION_NAME);
    }

    @Override
    public Flux<AlarmDefinition> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where(NAME).is(name));
        return reactiveMongoTemplate.find(query, AlarmDefinition.class, COLLECTION_NAME);
    }
    @Override
    public Flux<AlarmDefinition> findByResource(String resource) {
        Query query = new Query();
        query.addCriteria(Criteria.where(RESOURCES).in(resource));
        return reactiveMongoTemplate.find(query, AlarmDefinition.class, COLLECTION_NAME);
    }
    @Override
    public Mono<AlarmDefinition> updateOneAlarm(Mono<AlarmDefinition> alarmDefinitionMono) {
        return Mono.from(alarmDefinitionMono).doOnNext(alarmDefn -> reactiveMongoTemplate.save(alarmDefn, COLLECTION_NAME).block());
    }

    @Override
    public void deleteAlarm(AlarmDefinition Alarm) {
        reactiveMongoTemplate.remove(Alarm, COLLECTION_NAME).block();
    }
}
