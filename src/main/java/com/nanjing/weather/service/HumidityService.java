package com.nanjing.weather.service;

import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Humidities;

import java.util.List;

public interface HumidityService {

    /**
     * 返回wContour结果集
     */
    ContourResult<Humidities> findAllBySomeTerm(String parmOne, String parmTwo, String time);
}

