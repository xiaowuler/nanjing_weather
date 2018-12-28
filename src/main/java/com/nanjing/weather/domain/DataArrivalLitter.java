package com.nanjing.weather.domain;

import java.sql.Timestamp;
import java.util.List;

public class DataArrivalLitter {
    private Timestamp routineTime;
    private String time;
    private List<CenterDataArrival> centerDataArrival;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Timestamp getRoutineTime() {
        return routineTime;
    }

    public void setRoutineTime(Timestamp routineTime) {
        this.routineTime = routineTime;
    }

    public List<CenterDataArrival> getCenterDataArrival() {
        return centerDataArrival;
    }

    public void setCenterDataArrival(List<CenterDataArrival> centerDataArrival) {
        this.centerDataArrival = centerDataArrival;
    }
}
