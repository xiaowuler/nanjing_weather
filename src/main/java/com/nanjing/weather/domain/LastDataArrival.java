package com.nanjing.weather.domain;

import java.sql.Timestamp;

public class LastDataArrival {
    private String timelinessCode;
    private String description;

    public String getTimelinessCode() {
        return timelinessCode;
    }

    public void setTimelinessCode(String timelinessCode) {
        this.timelinessCode = timelinessCode;
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
