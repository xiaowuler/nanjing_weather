package com.nanjing.weather.service;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Temperatures;
import com.nanjing.weather.entitys.Temperature;

public interface TemperatureService {

    /**
     * 返回ContourResult
     */
    ContourResult<Temperature> findAllBySomeTerm(String parmOne, String parmTwo, String time);
}
