package com.nanjing.weather.service;

import com.nanjing.wContour.bean.LegendLevel;

import java.util.List;

public interface LegendLevelservice {

    public List<LegendLevel> findAll(String type);
}
