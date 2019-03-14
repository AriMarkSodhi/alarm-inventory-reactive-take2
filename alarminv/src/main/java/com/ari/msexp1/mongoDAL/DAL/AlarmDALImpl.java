package com.ari.msexp1.mongoDAL.DAL;

import com.ari.msexp1.mongoDAL.model.Alarm;
import com.ari.msexp1.mongoDAL.model.AlarmStatusChange;
import com.ari.msexp1.mongoDAL.model.OperatorStateChange;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class AlarmDALImpl implements AlarmDAL {
    private static final Logger logger = LoggerFactory.getLogger(AlarmDALImpl.class);
    private static final String COLLECTION_NAME = "alarm";
    private static final String NAME = "name";
    private static final String TIME_CREATED = "timeCreated";
    private static final String ID = "id";
    private static final String RESOURCE = "resource";
    private static final String IS_CLEARED = "isCleared";
    private static final String PERCEIVED_SEVERITY = "perceivedSeverity";
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    @Autowired
    public AlarmDALImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }
    @Override
    public Mono<Alarm> save(Mono<Alarm> alarmMono) {
        return Mono.from(alarmMono).doOnNext(alarm -> reactiveMongoTemplate.save(alarm).block());
    }
    @Override
    public Flux<Alarm> save(Publisher<Alarm> alarmPublisher) {
        List<Alarm> alarmList = new ArrayList<>();
        Flux.from(alarmPublisher).subscribe(
                alarm -> {
                    alarmList.add(reactiveMongoTemplate.save(alarm).block());
                },
                err -> logger.error("Alarm received error"+ err), //--> onError
                () -> logger.info("Alarm got Completed event")    //--> onComplete
        );
        return Flux.fromIterable(alarmList);
    }
    @Override
    public Flux<Alarm> findAll() {
        return reactiveMongoTemplate.findAll(Alarm.class, COLLECTION_NAME);
    }
    @Override
    public Flux<Alarm> findAll(Pageable pageable) {
        Query query = new Query();
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.limit(pageable.getPageSize());
        return reactiveMongoTemplate.find(query, Alarm.class, COLLECTION_NAME);
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
        return reactiveMongoTemplate.find(query, Alarm.class, COLLECTION_NAME);
    }

    @Override
    public Flux<Alarm> getDefaultSortedAlarmsPaginated(Pageable pageable) {
        Query query = new Query();
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.limit(pageable.getPageSize());
        query.addCriteria(Criteria.where(IS_CLEARED).ne(Boolean.TRUE));
        query.with(new Sort(Direction.DESC, PERCEIVED_SEVERITY)).with(new Sort(Direction.DESC, TIME_CREATED)).with(new Sort(Direction.ASC, RESOURCE));

        return reactiveMongoTemplate.find(query, Alarm.class, COLLECTION_NAME);
    }

    @Override
    public Flux<Alarm> getDefaultSortedAlarmsPaginatedByResource(Pageable pageable, String resource) {
        Query query = new Query();
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.limit(pageable.getPageSize());
        query.addCriteria(Criteria.where(RESOURCE).is(resource));
        query.addCriteria(Criteria.where(IS_CLEARED).ne(Boolean.TRUE));
        query.with(new Sort(Direction.DESC, PERCEIVED_SEVERITY)).with(new Sort(Direction.DESC, TIME_CREATED)).with(new Sort(Direction.ASC, RESOURCE));

        return reactiveMongoTemplate.find(query, Alarm.class, COLLECTION_NAME);
    }

    @Override
    public Mono<Alarm> findOneById(String alarmId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(ID).is(alarmId));
        return reactiveMongoTemplate.findOne(query, Alarm.class, COLLECTION_NAME);
    }
    @Override
    public Mono<Alarm> findOneByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where(NAME).is(name));
        return reactiveMongoTemplate.findOne(query, Alarm.class, COLLECTION_NAME);
    }
    @Override
    public Flux<Alarm> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where(NAME).is(name));
        return reactiveMongoTemplate.find(query, Alarm.class, COLLECTION_NAME);
    }
    @Override
    public Flux<Alarm> findByTime(Date date) {
        Query query = new Query();
        query.addCriteria(Criteria.where(TIME_CREATED).gt(date));
        return reactiveMongoTemplate.find(query, Alarm.class, COLLECTION_NAME);
    }
    @Override
    public Flux<Alarm> findByTimeRange(int lowerBound, int upperBound) {
        Query query = new Query();
        query.addCriteria(Criteria.where(TIME_CREATED).gt(lowerBound)
                .andOperator(Criteria.where(TIME_CREATED).lt(upperBound)));
        return reactiveMongoTemplate.find(query, Alarm.class, COLLECTION_NAME);
    }
    @Override
    public Flux<Alarm> findByResource(String resource) {
        Query query = new Query();
        query.addCriteria(Criteria.where(RESOURCE).in(resource));
        return reactiveMongoTemplate.find(query, Alarm.class, COLLECTION_NAME);
    }
    @Override
    public Mono<Alarm> updateOneAlarm(Alarm Alarm) {
        return reactiveMongoTemplate.save(Alarm, COLLECTION_NAME);
    }
    @Override
    public Mono<Alarm> clearAlarm(String alarmId) {

        Mono<Alarm> alarm = reactiveMongoTemplate.findOne(
                Query.query(Criteria.where(ID).is(alarmId)), Alarm.class, COLLECTION_NAME);
        if (alarm != null) {
            alarm.block().setCleared(Boolean.TRUE);
            alarm.block().setLastChange(new Date());
            reactiveMongoTemplate.save(alarm).block();
        }
        return alarm;
    }
    @Override
    public Mono<Alarm> updateAlarmStatus(String alarmId, AlarmStatusChange statusChange) {

        Mono<Alarm> alarm = reactiveMongoTemplate.findOne(
                Query.query(Criteria.where(ID).is(alarmId)), Alarm.class, COLLECTION_NAME);
        if (alarm != null) {
            alarm.block().updateAlarmStatusChanges(statusChange);
            alarm.block().setLastChange(new Date());
            reactiveMongoTemplate.save(alarm, COLLECTION_NAME).block();
        }
        return alarm;
    }

    @Override
    public Mono<Alarm> updateOperatorState(String alarmId, OperatorStateChange statusChange) {

        Mono<Alarm> alarm = reactiveMongoTemplate.findOne(
                Query.query(Criteria.where(ID).is(alarmId)), Alarm.class, COLLECTION_NAME);
        if (alarm != null) {
            alarm.block().updateOperatorStateChanges(statusChange);
            alarm.block().setLastChange(new Date());
            reactiveMongoTemplate.save(alarm, COLLECTION_NAME).block();
        }
        return alarm;
    }


    @Override
    public void deleteAlarm(Alarm Alarm) {
        reactiveMongoTemplate.remove(Alarm, COLLECTION_NAME).block();
    }
}
