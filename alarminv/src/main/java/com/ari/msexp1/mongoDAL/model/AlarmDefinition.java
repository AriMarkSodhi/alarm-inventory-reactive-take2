package com.ari.msexp1.mongoDAL.model;

import org.mongodb.morphia.annotations.Indexed;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.util.Calendar.*;

@Document(collection = "alarminventory")
@CompoundIndexes({
        @CompoundIndex(name = "uniquealarmdefn", unique = true, def = "{'alarmTypeId' : 1, 'alarmTypeQualifier': 1}")
})
public class AlarmDefinition {

    @Id
    private String alarmDefinitionId;
    @NotNull(message = "alarmtypeid must not be null")
    private String alarmTypeId;
    private String alarmTypeQualifier;
    @Indexed(unique = true)
    private String name;
    @Indexed
    private Severity severity;
    private String text;
    private Boolean hasClear;
    private List<String> resources;

    public AlarmDefinition() {
    }

    @PersistenceConstructor
    public AlarmDefinition(String alarmTypeId, String alarmTypeQualifier, String name, Severity severity, String text, Boolean hasClear, List<String> resources) {
        this.alarmTypeId = alarmTypeId;
        this.alarmTypeQualifier = alarmTypeQualifier;
        this.name = name;
        this.severity = severity;
        this.text = text;
        this.hasClear = hasClear;
        this.resources = resources;
    }

    public String getAlarmDefinitionId() {
        return alarmDefinitionId;
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

    public Severity getSeverity() {
        return severity;
    }

    public String getText() {
        return text;
    }

    public Boolean getHasClear() {
        return hasClear;
    }

    public List<String> getResources() {
        return resources;
    }

    @Override
    public String toString() {
        return String.format("AlarmDefinition{AlarmId='%s', name='%s', typeId=%s, qualifier=%s, severity=%s, text=%s}\n",
                alarmDefinitionId, name, alarmTypeId, alarmTypeQualifier, severity, text);
    }
}