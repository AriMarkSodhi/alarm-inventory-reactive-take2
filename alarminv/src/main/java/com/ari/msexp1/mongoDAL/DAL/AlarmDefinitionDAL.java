package com.ari.msexp1.mongoDAL.DAL;

import com.ari.msexp1.mongoDAL.model.AlarmDefinition;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlarmDefinitionDAL {
    Mono<AlarmDefinition> save(Mono<AlarmDefinition> alarmDefinitionMono);
    Flux<AlarmDefinition> save(Publisher<AlarmDefinition> alarmDefinitionMono);
    Flux<AlarmDefinition> findAll();
    Flux<AlarmDefinition> findAll(Pageable pageable);
    Mono<AlarmDefinition> findOneByName(String name);
    Mono<AlarmDefinition> findOneById(String alarmDefinitionId);
    Flux<AlarmDefinition> findByName(String name);
    Flux<AlarmDefinition> findByResource(String resource);
    Mono<AlarmDefinition> updateOneAlarm(Mono<AlarmDefinition> alarmDefinitionMono);
    void deleteAlarm(AlarmDefinition Alarm);
}