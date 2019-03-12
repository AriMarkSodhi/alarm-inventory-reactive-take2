package com.ari.msexp1.mongoDAL.model;

public enum OperatorState {
    unknown(1),
    disabled(2),
    enabled(3),
    testing(4),;

    private final int operatorState;

    private OperatorState(int operatorState) {
        this.operatorState = operatorState;
    }

    public int getOperatorState() {
        return this.operatorState;
    }

}
