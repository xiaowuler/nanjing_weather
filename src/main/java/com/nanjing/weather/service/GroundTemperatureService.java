package com.nanjing.weather.service;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.GroundTemperatures;
import com.nanjing.weather.entitys.GroundTemperature;

public interface GroundTemperatureService {

    ContourResult<GroundTemperature> findAllBySomeTerm(String parmOne, String parmTwo, String time);
}
