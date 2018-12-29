package com.nanjing.weather.service;

import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Rainfalls;
import com.nanjing.weather.entitys.Rainfall;

import java.util.List;

public interface RainfallService {
    /**
     * 返回ContourResult
     */
    ContourResult<Rainfall> findAllBySomeTerm(String parmOne, String parmTwo, String time);

    ContourResult<Rainfall> findSomeByTerm(String parmOne, String parmTwo, String time);
}
