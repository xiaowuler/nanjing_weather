package com.nanjing.weather.entity;

import java.util.List;

public class RainFall {
    private String stationId;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public List<RainFallCenter> getRainFallCenter() {
        return rainFallCenter;
    }

    public void setRainFallCenter(List<RainFallCenter> rainFallCenter) {
        this.rainFallCenter = rainFallCenter;
    }

    private List<RainFallCenter> rainFallCenter;
}
