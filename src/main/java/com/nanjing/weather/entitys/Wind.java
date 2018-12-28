package com.nanjing.weather.entitys;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Wind {
    private String stationId;
    private BigDecimal speed;
    private BigDecimal direction;
    private BigDecimal speed2Min;
    private BigDecimal direction2Min;
    private BigDecimal speed10Min;
    private BigDecimal direction10Min;
    private Timestamp routineTime;
    private Timestamp createTime;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public BigDecimal getDirection() {
        return direction;
    }

    public void setDirection(BigDecimal direction) {
        this.direction = direction;
    }

    public BigDecimal getSpeed2Min() {
        return speed2Min;
    }

    public void setSpeed2Min(BigDecimal speed2Min) {
        this.speed2Min = speed2Min;
    }

    public BigDecimal getDirection2Min() {
        return direction2Min;
    }

    public void setDirection2Min(BigDecimal direction2Min) {
        this.direction2Min = direction2Min;
    }

    public BigDecimal getSpeed10Min() {
        return speed10Min;
    }

    public void setSpeed10Min(BigDecimal speed10Min) {
        this.speed10Min = speed10Min;
    }

    public BigDecimal getDirection10Min() {
        return direction10Min;
    }

    public void setDirection10Min(BigDecimal direction10Min) {
        this.direction10Min = direction10Min;
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
