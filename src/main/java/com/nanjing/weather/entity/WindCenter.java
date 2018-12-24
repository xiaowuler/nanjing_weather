package com.nanjing.weather.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class WindCenter {
    private BigDecimal speed;
    private BigDecimal direction;
    private String routineTime;
    private String createTime;
    private BigDecimal speedTwoMin;
    private BigDecimal directionTwoMin;
    private BigDecimal speedTenMin;
    private BigDecimal directionTenMin;

    public BigDecimal getSpeedTwoMin() {
        return speedTwoMin;
    }

    public void setSpeedTwoMin(BigDecimal speedTwoMin) {
        this.speedTwoMin = speedTwoMin;
    }

    public BigDecimal getDirectionTwoMin() {
        return directionTwoMin;
    }

    public void setDirectionTwoMin(BigDecimal directionTwoMin) {
        this.directionTwoMin = directionTwoMin;
    }

    public BigDecimal getSpeedTenMin() {
        return speedTenMin;
    }

    public void setSpeedTenMin(BigDecimal speedTenMin) {
        this.speedTenMin = speedTenMin;
    }

    public BigDecimal getDirectionTenMin() {
        return directionTenMin;
    }

    public void setDirectionTenMin(BigDecimal directionTenMin) {
        this.directionTenMin = directionTenMin;
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
}
