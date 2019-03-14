package com.ari.msexp1;

import com.ari.msexp1.mongoDAL.DAL.AlarmDAL;
import com.ari.msexp1.mongoDAL.DAL.AlarmDefinitionDAL;
import com.ari.msexp1.mongoDAL.model.Alarm;
import com.ari.msexp1.mongoDAL.model.AlarmDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("alarminventory")
public class AlarmInventoryRESTController {
    private final AlarmDefinitionDAL alarmDefinitionDAL;
    private final AlarmDAL alarmDAL;

    @Autowired
    public AlarmInventoryRESTController(AlarmDefinitionDAL alarmDefinitionDAL, AlarmDAL alarmDAL) {
        this.alarmDefinitionDAL = alarmDefinitionDAL;
        this.alarmDAL = alarmDAL;
    }

    @GetMapping("/alarmdefinitions")
    public Flux<AlarmDefinition> getAlarmDefinitions() {
        return alarmDefinitionDAL.findAll();
    }

    @GetMapping(value="/alarmdefinition")
    public Mono<AlarmDefinition> getAlarmDefinition(
            @RequestParam(value = "name",required=true) String name)
    {
        return alarmDefinitionDAL.findOneByName(name);
    }

    @GetMapping(value="/alarmdefinition/{id}")
    public Mono<AlarmDefinition> getAlarmDefinitionById(
            @PathVariable("id") String id)
    {
        return alarmDefinitionDAL.findOneById(id);
    }

    @GetMapping("/alarms")
    public Flux<Alarm> getAlarms() {
        return alarmDAL.findAll();
    }

    @GetMapping(value="/alarm")
    public Flux<Alarm> getAlarm(
            @RequestParam(value = "name",required=true) String name)
    {
        return alarmDAL.findByName(name);
    }

    @GetMapping(value="/alarm/{id}")
    public Mono<Alarm> getAlarmById(
            @PathVariable("id") String id)
    {
        return alarmDAL.findOneById(id);
    }


}
