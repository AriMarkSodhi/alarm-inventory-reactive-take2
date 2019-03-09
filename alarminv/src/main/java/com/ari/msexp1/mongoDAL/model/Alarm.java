package com.ari.msexp1.mongoDAL.model;

import org.mongodb.morphia.annotations.Indexed;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "alarm")
//@CompoundIndexes({
//        @CompoundIndex(name = "uniquealarminstance", unique = true, def = "{'alarmTypeId' : 1, 'alarmTypeQualifier': 1, 'resource' : 1}")
//})

public class Alarm {

    @Id
    private String alarmId;
    private String resource;
    @Indexed
    private String alarmTypeId;
    private String alarmTypeQualifier;
    @Indexed
    private String name;
    private String impactedResource;
    private String rootCauseResource;
    @Indexed
    private Date timeCreated;
    private Boolean isCleared;
    private Date lastChange;
    @Indexed
    private Severity perceivedSeverity;
    private String text;
    private List<AlarmStatusChange> alarmStatusChanges;
    private List<OperatorStateChange> operatorState;

    public Alarm() {
    }

    @PersistenceConstructor
    public Alarm(String alarmId, String resource, String alarmTypeId, String alarmTypeQualifier, String name, String impactedResource,
                 String rootCauseResource,  Boolean isCleared, Date lastChange, Severity perceivedSeverity, String text,
                 List<AlarmStatusChange> alarmStatusChanges, List<OperatorStateChange> operatorState) {
        this.alarmId = alarmId;
        this.resource = resource;
        this.alarmTypeId = alarmTypeId;
        this.alarmTypeQualifier = alarmTypeQualifier;
        this.name = name;
        this.impactedResource = impactedResource;
        this.rootCauseResource = rootCauseResource;
        this.timeCreated = new Date();
        this.isCleared = isCleared;
        this.lastChange = lastChange;
        this.perceivedSeverity = perceivedSeverity;
        this.text = text;
        this.alarmStatusChanges = alarmStatusChanges;
        this.operatorState = operatorState;
    }

// standard getters and setters


    public String getAlarmId() {
        return alarmId;
    }

    public String getResource() {
        return resource;
    }

    public String getAlarmTypeId() {
        return alarmTypeId;
    }

    public String getAlarmTypeQualifier() {
        return alarmTypeQualifier;
    }

    public String getName() {
        return name;
    }

    public String getImpactedResource() {
        return impactedResource;
    }

    public String getRootCauseResource() {
        return rootCauseResource;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public Boolean getCleared() {
        return isCleared;
    }

    public void setCleared(Boolean cleared) {
        isCleared = cleared;
    }

    public Date getLastChange() {
        return lastChange;
    }


    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    public Severity getPerceivedSeverity() {
        return perceivedSeverity;
    }

    public void setPerceivedSeverity(Severity perceivedSeverity) {
        this.perceivedSeverity = perceivedSeverity;
    }

    public String getText() {
        return text;
    }

    public List<AlarmStatusChange> getAlarmStatusChanges() {
        return alarmStatusChanges;
    }

    public void updateAlarmStatusChanges(AlarmStatusChange change) {
        alarmStatusChanges.add(change);
    }

    public List<OperatorStateChange> getOperatorState() {
        return operatorState;
    }

    public void updateOperatorStateChanges(OperatorStateChange change) {
        operatorState.add(change);
    }

    @Override
    public String toString() {
        return String.format("Alarm{AlarmId='%s', name='%s', typeId=%s, qualifier=%s, resource=%s, severity=%s, text=%s}\n",
                alarmId, name, alarmTypeId, alarmTypeQualifier, resource, perceivedSeverity, text);

    }
}