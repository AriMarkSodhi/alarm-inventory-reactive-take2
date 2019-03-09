package com.ari.msexp1.mongoDAL.DAL;

import com.ari.msexp1.mongoDAL.model.Alarm;
import com.ari.msexp1.mongoDAL.model.AlarmStatusChange;
import com.ari.msexp1.mongoDAL.model.OperatorStateChange;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Repository
public class AlarmDALImpl implements AlarmDAL {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    @Autowired
    public AlarmDALImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }
    @Override
    public Mono<Alarm> save(Mono<Alarm> alarmMono) {
        return Mono.from(alarmMono).doOnNext(alarm -> reactiveMongoTemplate.save(alarm));
    }
    @Override
    public Flux<Alarm> save(Publisher<Alarm> alarmPublisher) {
        return Flux.from(alarmPublisher).doOnNext(alarm -> reactiveMongoTemplate.save(alarm));
    }
    @Override
    public Flux<Alarm> findAll() {
        return reactiveMongoTemplate.findAll(Alarm.class);
    }
    @Override
    public Flux<Alarm> findAll(Pageable pageable) {
        Query query = new Query();
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.limit(pageable.getPageSize());
        return reactiveMongoTemplate.find(query, Alarm.class);
    }
    @Override
    public Flux<Alarm> getAllAlarmByDefnPaginated(String alarmTypeId, String alarmTypeQualifier,
                                                  Pageable pageable)
    {
//        MatchOperation matchStage = Aggregation.match(new Criteria("alarmTypeId").is(alarmTypeId).andOperator(new Criteria("alarmTypeQualifier").is(alarmTypeQualifier)));
//        ProjectionOperation projectStage = project("alarm", "bar.baz");
//
//        TypedAggregation<Alarm> aggregation
//                = newAggregation(Alarm.class, project().
//        );
//
//        AggregationResults<Alarm> output
//                = reactiveMongoTemplate.aggregate(aggregation,  Alarm.class);

        Query query = new Query();
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.limit(pageable.getPageSize());
        return reactiveMongoTemplate.find(query, Alarm.class);
    }

    @Override
    public Flux<Alarm> getDefaultSortedAlarmsPaginated(Pageable pageable) {
        Query query = new Query();
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.limit(pageable.getPageSize());
        query.addCriteria(Criteria.where("isCleared").ne(Boolean.TRUE));
        query.with(new Sort(Direction.DESC, "perceivedSeverity")).with(new Sort(Direction.DESC, "timeCreated")).with(new Sort(Direction.ASC,"resource"));

        return reactiveMongoTemplate.find(query, Alarm.class);
    }

    @Override
    public Flux<Alarm> getDefaultSortedAlarmsPaginatedByResource(Pageable pageable, String resource) {
        Query query = new Query();
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.limit(pageable.getPageSize());
        query.addCriteria(Criteria.where("resource").is(resource));
        query.addCriteria(Criteria.where("isCleared").ne(Boolean.TRUE));
        query.with(new Sort(Direction.DESC, "perceivedSeverity")).with(new Sort(Direction.DESC, "timeCreated")).with(new Sort(Direction.ASC,"resource"));

        return reactiveMongoTemplate.find(query, Alarm.class);
    }

    @Override
    public Mono<Alarm> findOneById(String alarmId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(alarmId));
        return reactiveMongoTemplate.findOne(query, Alarm.class);
    }
    @Override
    public Mono<Alarm> findOneByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return reactiveMongoTemplate.findOne(query, Alarm.class);
    }
    @Override
    public Flux<Alarm> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return reactiveMongoTemplate.find(query, Alarm.class);
    }
    @Override
    public Flux<Alarm> findByTime(Date date) {
        Query query = new Query();
        query.addCriteria(Criteria.where("timeCreated").gt(date));
        return reactiveMongoTemplate.find(query, Alarm.class);
    }
    @Override
    public Flux<Alarm> findByTimeRange(int lowerBound, int upperBound) {
        Query query = new Query();
        query.addCriteria(Criteria.where("timeCreated").gt(lowerBound)
                .andOperator(Criteria.where("timeCreated").lt(upperBound)));
        return reactiveMongoTemplate.find(query, Alarm.class);
    }
    @Override
    public Flux<Alarm> findByResource(String resource) {
        Query query = new Query();
        query.addCriteria(Criteria.where("resource").in(resource));
        return reactiveMongoTemplate.find(query, Alarm.class);
    }
    @Override
    public Mono<Alarm> updateOneAlarm(Alarm Alarm) {
        return reactiveMongoTemplate.save(Alarm);
    }
    @Override
    public Mono<Alarm> clearAlarm(String alarmId) {

        Mono<Alarm> alarm = reactiveMongoTemplate.findOne(
                Query.query(Criteria.where("id").is(alarmId)), Alarm.class);
        if (alarm != null) {
            alarm.block().setCleared(Boolean.TRUE);
            alarm.block().setLastChange(new Date());
            return reactiveMongoTemplate.save(alarm);
        }
        return alarm;
    }
    @Override
    public Mono<Alarm> updateAlarmStatus(String alarmId, AlarmStatusChange statusChange) {

        Mono<Alarm> alarm = reactiveMongoTemplate.findOne(
                Query.query(Criteria.where("id").is(alarmId)), Alarm.class);
        if (alarm != null) {
            alarm.block().updateAlarmStatusChanges(statusChange);
            alarm.block().setLastChange(new Date());
            reactiveMongoTemplate.save(alarm);
        }
        return alarm;
    }

    @Override
    public Mono<Alarm> updateOperatorState(String alarmId, OperatorStateChange statusChange) {

        Mono<Alarm> alarm = reactiveMongoTemplate.findOne(
                Query.query(Criteria.where("id").is(alarmId)), Alarm.class);
        if (alarm != null) {
            alarm.block().updateOperatorStateChanges(statusChange);
            alarm.block().setLastChange(new Date());
            reactiveMongoTemplate.save(alarm);
        }
        return alarm;
    }


    @Override
    public void deleteAlarm(Alarm Alarm) {
        reactiveMongoTemplate.remove(Alarm);
    }
}
