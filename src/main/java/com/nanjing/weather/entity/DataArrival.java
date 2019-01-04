package com.nanjing.weather.entity;

import java.sql.Timestamp;

public class DataArrival {

    private Timestamp routineTime;//资料时间
    private String productTypeCode;//采集的要素
    private String productCategoryCode;
    private String productRegionCode;
    private String timelinessCode;//采集结果
    private String description;//备注插入的条数
    private Timestamp beginSyncTime;//开始时间
    private Timestamp endSyncTime;//结束时间

    public Timestamp getRoutineTime() {
        return routineTime;
    }

    public void setRoutineTime(Timestamp routineTime) {
        this.routineTime = routineTime;
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    public String getProductCategoryCode() {
        return productCategoryCode;
    }

    public void setProductCategoryCode(String productCategoryCode) {
        this.productCategoryCode = productCategoryCode;
    }

    public String getProductRegionCode() {
        return productRegionCode;
    }

    public void setProductRegionCode(String productRegionCode) {
        this.productRegionCode = productRegionCode;
    }

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
