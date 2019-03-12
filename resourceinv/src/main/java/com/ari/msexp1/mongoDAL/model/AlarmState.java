package com.ari.msexp1.mongoDAL.model;

public enum AlarmState {
    unknown(0),
    underrepair(1),
    critical(2),
    major(3),
    minor(4),
    warning(5),
    indeterminate(6),;

    private final int alarmState;

    private AlarmState(int alarmState) {
        this.alarmState = alarmState;
    }

    public int getOperatorState() {
        return this.alarmState;
    }

}
