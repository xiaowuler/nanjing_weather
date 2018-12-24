package com.nanjing.weather.entity;

import java.util.List;

public class Humiditie {
    private String stationId;
    private List<HumiditieCenter> humiditieCenter;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public List<HumiditieCenter> getHumiditieCenter() {
        return humiditieCenter;
    }

    public void setHumiditieCenter(List<HumiditieCenter> humiditieCenter) {
        this.humiditieCenter = humiditieCenter;
    }
}
