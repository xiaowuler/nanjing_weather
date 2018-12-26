package com.nanjing.weather.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Stations {
    private String id;
    private String name;
    private String province;
    private String city;
    private String county;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String level;
    private Timestamp create_Time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Timestamp getCreate_Time() {
        return create_Time;
    }

    public void setCreate_Time(Timestamp create_Time) {
        this.create_Time = create_Time;
    }
}
