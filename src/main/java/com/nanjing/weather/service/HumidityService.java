package com.nanjing.weather.service;

import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Humidities;
import com.nanjing.weather.entity.Humiditie;
import com.nanjing.weather.entitys.Humidity;

import java.util.List;

public interface HumidityService {

    /**
     * 返回wContour结果集
     */
    ContourResult<Humidity> findAllBySomeTerm(String parmOne, String parmTwo, String time);
}

