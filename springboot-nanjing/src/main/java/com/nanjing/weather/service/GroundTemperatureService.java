package com.nanjing.weather.service;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.GroundTemperatures;

public interface GroundTemperatureService {

    ContourResult<GroundTemperatures> findAllByTerm(String value);

    ContourResult<GroundTemperatures> findAllBySomeTerm(String parmOne, String parmTwo, String time);
}
