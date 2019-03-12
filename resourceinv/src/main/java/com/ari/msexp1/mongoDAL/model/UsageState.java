package com.ari.msexp1.mongoDAL.model;

public enum UsageState {
    unknown(1),
    idle(2),
    active(3),
    busy(4),;

    private final int usageState;

    private UsageState(int usageState) {
        this.usageState = usageState;
    }

    public int getOperatorState() {
        return this.usageState;
    }

}
