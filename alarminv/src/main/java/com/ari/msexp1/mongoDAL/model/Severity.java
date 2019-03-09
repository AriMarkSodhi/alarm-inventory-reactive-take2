package com.ari.msexp1.mongoDAL.model;

public enum Severity {
    indeterminate(2),
    minor(3),
    warning(4),
    major(5),
    critical(6),;


    private final int severity;

    private Severity(int severity) {
        this.severity = severity;
    }

    public int getSeverity() {
        return this.severity;
    }

}
