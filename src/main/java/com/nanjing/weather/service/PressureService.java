package com.nanjing.weather.service;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Pressures;

public interface PressureService {

    ContourResult<Pressures> findAllBySomeTerm(String parmOne, String parmTwo, String time);
}
