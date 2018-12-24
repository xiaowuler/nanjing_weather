package com.nanjing.weather.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Winds {
    private String station_Id;
    private BigDecimal avg_Speed;
    private BigDecimal avg_Speed_Direction;
    private Timestamp routine_Time;
    private Timestamp create_Time;

    public String getStation_Id() {
        return station_Id;
    }

    public void setStation_Id(String station_Id) {
        this.station_Id = station_Id;
    }

    public BigDecimal getAvg_Speed() {
        return avg_Speed;
    }

    public void setAvg_Speed(BigDecimal avg_Speed) {
        this.avg_Speed = avg_Speed;
    }

    public BigDecimal getAvg_Speed_Direction() {
        return avg_Speed_Direction;
    }

    public void setAvg_Speed_Direction(BigDecimal avg_Speed_Direction) {
        this.avg_Speed_Direction = avg_Speed_Direction;
    }

    public Timestamp getRoutine_Time() {
        return routine_Time;
    }

    public void setRoutine_Time(Timestamp routine_Time) {
        this.routine_Time = routine_Time;
    }

    public Timestamp getCreate_Time() {
        return create_Time;
    }

    public void setCreate_Time(Timestamp create_Time) {
        this.create_Time = create_Time;
    }
}
