package com.nanjing.weather.dto;

import java.util.List;

public class Category {
    private String name;
    private String code;
    private List<Region> regions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
}
