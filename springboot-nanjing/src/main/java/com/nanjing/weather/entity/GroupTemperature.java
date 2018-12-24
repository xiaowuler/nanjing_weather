package com.nanjing.weather.entity;

import java.util.List;

public class GroupTemperature {
    private String stationId;
    private List<GroupTemperatureCenter> groupTemperatureCenter;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public List<GroupTemperatureCenter> getGroupTemperatureCenter() {
        return groupTemperatureCenter;
    }

    public void setGroupTemperatureCenter(List<GroupTemperatureCenter> groupTemperatureCenter) {
        this.groupTemperatureCenter = groupTemperatureCenter;
    }
}
