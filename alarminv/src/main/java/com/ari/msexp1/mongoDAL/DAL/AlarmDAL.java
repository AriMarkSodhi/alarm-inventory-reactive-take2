package com.ari.msexp1.mongoDAL.DAL;

import com.ari.msexp1.mongoDAL.model.Alarm;
import com.ari.msexp1.mongoDAL.model.AlarmStatusChange;
import com.ari.msexp1.mongoDAL.model.OperatorStateChange;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

public interface AlarmDAL {
    Mono<Alarm> save(Mono<Alarm> Alarm);
    Flux<Alarm> save(Publisher<Alarm> Alarms);
    Flux<Alarm> findAll();

    Flux<Alarm> findAll(Pageable pageable);
    Flux<Alarm> getAllAlarmByDefnPaginated(String alarmTypeId, String alarmTypeQualifier,
                                           Pageable pageable);
    Flux<Alarm> getDefaultSortedAlarmsPaginated(Pageable pageable);
    Flux<Alarm> getDefaultSortedAlarmsPaginatedByResource(Pageable pageable, String resource);

    Mono<Alarm> findOneById(String alarmId);
    Mono<Alarm> findOneByName(String name);
    Flux<Alarm> findByName(String name);
    Flux<Alarm> findByTime(Date date);
    Flux<Alarm> findByTimeRange(int lowerBound, int upperBound);
    Flux<Alarm> findByResource(String resource);
    Mono<Alarm> updateOneAlarm(Alarm Alarm);
    Mono<Alarm> updateAlarmStatus(String alarmId, AlarmStatusChange statusChange);
    Mono<Alarm> updateOperatorState(String alarmId, OperatorStateChange statusChange);
    Mono<Alarm> clearAlarm(String alarmId);

    void deleteAlarm(Alarm Alarm);
}