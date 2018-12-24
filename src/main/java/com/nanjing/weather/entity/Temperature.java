package com.nanjing.weather.entity;

import java.util.List;

public class Temperature {
    private String stationId;
    private List<TemperatureCenter> temperatureCenter;
    private List<TemperatureNinMax> temperatureNinMaxe;

    public List<TemperatureNinMax> getTemperatureNinMaxe() {
        return temperatureNinMaxe;
    }

    public void setTemperatureNinMaxe(List<TemperatureNinMax> temperatureNinMaxe) {
        this.temperatureNinMaxe = temperatureNinMaxe;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public List<TemperatureCenter> getTemperatureCenter() {
        return temperatureCenter;
    }

    public void setTemperatureCenter(List<TemperatureCenter> temperatureCenter) {
        this.temperatureCenter = temperatureCenter;
    }
}
