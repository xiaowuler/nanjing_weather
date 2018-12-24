package com.nanjing.weather.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GroundTemperatures {
    private String stationId;
    private BigDecimal value;
    private BigDecimal valueFiveCM;
    private BigDecimal valueTenCM;
    private BigDecimal valueFifteenCM;
    private BigDecimal valueTwentyCM;
    private BigDecimal valueFortyCM;
    private Timestamp routineTime;
    private Timestamp createTime;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValueFiveCM() {
        return valueFiveCM;
    }

    public void setValueFiveCM(BigDecimal valueFiveCM) {
        this.valueFiveCM = valueFiveCM;
    }

    public BigDecimal getValueTenCM() {
        return valueTenCM;
    }

    public void setValueTenCM(BigDecimal valueTenCM) {
        this.valueTenCM = valueTenCM;
    }

    public BigDecimal getValueFifteenCM() {
        return valueFifteenCM;
    }

    public void setValueFifteenCM(BigDecimal valueFifteenCM) {
        this.valueFifteenCM = valueFifteenCM;
    }

    public BigDecimal getValueTwentyCM() {
        return valueTwentyCM;
    }

    public void setValueTwentyCM(BigDecimal valueTwentyCM) {
        this.valueTwentyCM = valueTwentyCM;
    }

    public BigDecimal getValueFortyCM() {
        return valueFortyCM;
    }

    public void setValueFortyCM(BigDecimal valueFortyCM) {
        this.valueFortyCM = valueFortyCM;
    }

    public Timestamp getRoutineTime() {
        return routineTime;
    }

    public void setRoutineTime(Timestamp routineTime) {
        this.routineTime = routineTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
