package com.nanjing.weather.entity;

import java.util.List;

public class Wind {

    private String stationId;
    private List<WindCenter> windCenter;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public List<WindCenter> getWindCenter() {
        return windCenter;
    }

    public void setWindCenter(List<WindCenter> windCenter) {
        this.windCenter = windCenter;
    }
}
