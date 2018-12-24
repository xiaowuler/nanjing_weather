package com.nanjing.weather.domain;

import java.sql.Timestamp;

public class CenterDataArrival {
    private String productRegionCode;
    private String timeliessCode;
    private String description;
    private Timestamp beginSyncTime;
    private Timestamp endSyncTime;

    public String getProductRegionCode() {
        return productRegionCode;
    }

    public void setProductRegionCode(String productRegionCode) {
        this.productRegionCode = productRegionCode;
    }

    public String getTimeliessCode() {
        return timeliessCode;
    }

    public void setTimeliessCode(String timeliessCode) {
        this.timeliessCode = timeliessCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getBeginSyncTime() {
        return beginSyncTime;
    }

    public void setBeginSyncTime(Timestamp beginSyncTime) {
        this.beginSyncTime = beginSyncTime;
    }

    public Timestamp getEndSyncTime() {
        return endSyncTime;
    }

    public void setEndSyncTime(Timestamp endSyncTime) {
        this.endSyncTime = endSyncTime;
    }
}
