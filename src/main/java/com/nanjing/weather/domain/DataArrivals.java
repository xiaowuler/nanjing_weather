package com.nanjing.weather.domain;

import java.sql.Timestamp;

public class DataArrivals {
    private Timestamp routine_Time;//资料时间
    private String product_type_code;//采集的要素
    private String timeliness_code;//采集结果
    private String description;//备注插入的条数
    private Timestamp begin_sync_time;//开始时间
    private Timestamp end_sync_time;//结束时间
    private String routine;
    private String begin;
    private String end;

    public Timestamp getRoutine_Time() {
        return routine_Time;
    }

    public void setRoutine_Time(Timestamp routine_Time) {
        this.routine_Time = routine_Time;
    }

    public String getproduct_type_code() {
        return product_type_code;
    }

    public void setproduct_type_code(String product_type_code) {
        this.product_type_code = product_type_code;
    }

    public String getTimeliness_code() {
        return timeliness_code;
    }

    public void setTimeliness_code(String timeliness_code) {
        this.timeliness_code = timeliness_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getBegin_sync_time() {
        return begin_sync_time;
    }

    public void setBegin_sync_time(Timestamp begin_sync_time) {
        this.begin_sync_time = begin_sync_time;
    }

    public Timestamp getEnd_sync_time() {
        return end_sync_time;
    }

    public void setEnd_sync_time(Timestamp end_sync_time) {
        this.end_sync_time = end_sync_time;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
