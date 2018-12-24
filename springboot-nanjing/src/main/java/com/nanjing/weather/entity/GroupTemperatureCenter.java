package com.nanjing.weather.entity;

import java.math.BigDecimal;

public class GroupTemperatureCenter {
    private BigDecimal value;
    private BigDecimal valueFivecm;
    private BigDecimal valueTencm;
    private BigDecimal valueFifcm;
    private BigDecimal valueTwecm;
    private BigDecimal valueForcm;
    private String routineTime;
    private String createTime;

    public String getRoutineTime() {
        return routineTime;
    }

    public void setRoutineTime(String routineTime) {
        this.routineTime = routineTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValueFivecm() {
        return valueFivecm;
    }

    public void setValueFivecm(BigDecimal valueFivecm) {
        this.valueFivecm = valueFivecm;
    }

    public BigDecimal getValueTencm() {
        return valueTencm;
    }

    public void setValueTencm(BigDecimal valueTencm) {
        this.valueTencm = valueTencm;
    }

    public BigDecimal getValueFifcm() {
        return valueFifcm;
    }

    public void setValueFifcm(BigDecimal valueFifcm) {
        this.valueFifcm = valueFifcm;
    }

    public BigDecimal getValueTwecm() {
        return valueTwecm;
    }

    public void setValueTwecm(BigDecimal valueTwecm) {
        this.valueTwecm = valueTwecm;
    }

    public BigDecimal getValueForcm() {
        return valueForcm;
    }

    public void setValueForcm(BigDecimal valueForcm) {
        this.valueForcm = valueForcm;
    }
}
