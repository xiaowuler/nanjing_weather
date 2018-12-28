package com.nanjing.weather.service;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Temperatures;

public interface TemperatureService {

    /**
     * 返回ContourResult
     */
    ContourResult<Temperatures> findAllBySomeTerm(String parmOne, String parmTwo, String time);
}
