package com.ari.msexp1.mongoDAL.model;

import java.util.Date;

public class OperatorStateChange {

    private Date time;
    private String operator;
    private OperatorState operatorState;
    private String text;

    public OperatorStateChange() {
    }

    public OperatorStateChange(String operator, OperatorState operatorState, String text) {
        this.time = new Date();
        this.operator = operator;
        this.operatorState = operatorState;
        this.text = text;
    }

// standard getters and setters


    public Date getTime() {
        return time;
    }

    public String getOperator() {
        return operator;
    }

    public OperatorState getOperatorState() {
        return operatorState;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return String.format("OperatorState{Time='%s', operator='%s', operatorState='%s', text=%s}\n",
                time, operator, operatorState, text);
    }
}