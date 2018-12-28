package com.nanjing.weather.entitys;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GroundTemperature {
    private String stationId;
    private BigDecimal value;
    private BigDecimal value5Cm;
    private BigDecimal value10Cm;
    private BigDecimal value15Cm;
    private BigDecimal value20Cm;
    private BigDecimal value40Cm;
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

    public BigDecimal getValue5Cm() {
        return value5Cm;
    }

    public void setValue5Cm(BigDecimal value5Cm) {
        this.value5Cm = value5Cm;
    }

    public BigDecimal getValue10Cm() {
        return value10Cm;
    }

    public void setValue10Cm(BigDecimal value10Cm) {
        this.value10Cm = value10Cm;
    }

    public BigDecimal getValue15Cm() {
        return value15Cm;
    }

    public void setValue15Cm(BigDecimal value15Cm) {
        this.value15Cm = value15Cm;
    }

    public BigDecimal getValue20Cm() {
        return value20Cm;
    }

    public void setValue20Cm(BigDecimal value20Cm) {
        this.value20Cm = value20Cm;
    }

    public BigDecimal getValue40Cm() {
        return value40Cm;
    }

    public void setValue40Cm(BigDecimal value40Cm) {
        this.value40Cm = value40Cm;
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
