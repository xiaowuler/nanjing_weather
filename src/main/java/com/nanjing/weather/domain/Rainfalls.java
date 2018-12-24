package com.nanjing.weather.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Rainfalls {
    private String station_Id;
    private BigDecimal value;
    private Timestamp routine_Time;
    private Timestamp create_Time;

    public String getStation_Id() {
        return station_Id;
    }

    public void setStation_Id(String station_Id) {
        this.station_Id = station_Id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
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
