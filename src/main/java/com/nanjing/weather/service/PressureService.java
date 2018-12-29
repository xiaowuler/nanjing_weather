package com.nanjing.weather.service;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.entitys.Pressure;

public interface PressureService {

    ContourResult<Pressure> findAllBySomeTerm(String parmOne, String parmTwo, String time);
}
