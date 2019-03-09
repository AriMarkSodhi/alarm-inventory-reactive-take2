package com.ari.msexp1.mongoDAL.DAL;

import com.ari.msexp1.mongoDAL.model.AlarmDefinition;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AlarmDefinitionDALImpl implements AlarmDefinitionDAL {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    @Autowired
    public AlarmDefinitionDALImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }
    @Override
    public Mono<AlarmDefinition> save(Mono<AlarmDefinition> alarmDefinitionMono) {
        return Mono.from(alarmDefinitionMono).doOnNext(alarmDefn -> reactiveMongoTemplate.save(alarmDefn));
    }
    @Override
    public Flux<AlarmDefinition> save(Publisher<AlarmDefinition> alarmDefinitionPublisher) {
        return Flux.from(alarmDefinitionPublisher).doOnNext(alarmDefn -> reactiveMongoTemplate.save(alarmDefn));
    }
    @Override
    public Flux<AlarmDefinition> findAll() {
        return reactiveMongoTemplate.findAll(AlarmDefinition.class);
    }

    @Override
    public Flux<AlarmDefinition> findAll(Pageable pageable) {
        Query query = new Query();
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.limit(pageable.getPageSize());
        return reactiveMongoTemplate.find(query, AlarmDefinition.class);
    }
    @Override
    public Mono<AlarmDefinition> findOneByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return reactiveMongoTemplate.findOne(query, AlarmDefinition.class);
    }

    @Override
    public Mono<AlarmDefinition> findOneById(String alarmDefinitionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("alarmDefinitionId").is(alarmDefinitionId));
        return reactiveMongoTemplate.findOne(query, AlarmDefinition.class);
    }

    @Override
    public Flux<AlarmDefinition> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return reactiveMongoTemplate.find(query, AlarmDefinition.class);
    }
    @Override
    public Flux<AlarmDefinition> findByResource(String resource) {
        Query query = new Query();
        query.addCriteria(Criteria.where("resources").in(resource));
        return reactiveMongoTemplate.find(query, AlarmDefinition.class);
    }
    @Override
    public Mono<AlarmDefinition> updateOneAlarm(Mono<AlarmDefinition> alarmDefinitionMono) {
        return Mono.from(alarmDefinitionMono).doOnNext(alarmDefn -> reactiveMongoTemplate.save(alarmDefn));
    }

    @Override
    public void deleteAlarm(AlarmDefinition Alarm) {
        reactiveMongoTemplate.remove(Alarm);
    }
}
