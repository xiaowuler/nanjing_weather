package com.nanjing.weather.entity;

import java.util.List;

public class Pressure {
    private String stationId;
    private List<PressureCenter> pressureCenter;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public List<PressureCenter> getPressureCenter() {
        return pressureCenter;
    }

    public void setPressureCenter(List<PressureCenter> pressureCenter) {
        this.pressureCenter = pressureCenter;
    }
}
