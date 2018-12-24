package com.nanjing.wContour.bean;

public class ValuePoint {
    private double Longitude;
    private double Latitude;
    private double Value;
    private String name;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private Double averageDirection;
    private Double instantDirection;

    public Double getAverageDirection() {
        return averageDirection;
    }

    public void setAverageDirection(Double averageDirection) {
        this.averageDirection = averageDirection;
    }

    public Double getInstantDirection() {
        return instantDirection;
    }

    public void setInstantDirection(Double instantDirection) {
        this.instantDirection = instantDirection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }
}
