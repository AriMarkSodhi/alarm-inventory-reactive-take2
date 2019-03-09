package com.ari.msexp1.mongoDAL.model;

public enum OperatorState {
    none(1),
    ack(2),
    closed(3),
    shelved(4),
    unshelved(5),;

    private final int operatorState;

    private OperatorState(int operatorState) {
        this.operatorState = operatorState;
    }

    public int getOperatorState() {
        return this.operatorState;
    }

}
