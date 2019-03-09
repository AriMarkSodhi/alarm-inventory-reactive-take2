package com.ari.msexp1.mongoDAL.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

public class AlarmStatusChange {

    private Date time;
    private Severity perceivedSeverity;
    private String text;

    public AlarmStatusChange() {
    }

    public AlarmStatusChange(Severity perceivedSeverity, String text) {
        this.time = new Date();
        this.perceivedSeverity = perceivedSeverity;
        this.text = text;
    }

// standard getters and setters

    public Date getTime() {
        return time;
    }

    public Severity getPerceivedSeverity() {
        return perceivedSeverity;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return String.format("AlarmStatus{Time='%s', severity='%s', text=%s}\n",
                time, perceivedSeverity, text);
    }
}