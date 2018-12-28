package com.nanjing.weather.service;

import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Rainfalls;

import java.util.List;

public interface RainfallService {
    /**
     * 返回ContourResult
     */
    ContourResult<Rainfalls> findAllBySomeTerm(String parmOne, String parmTwo, String time);

    ContourResult<Rainfalls> findSomeByTerm(String parmOne, String parmTwo, String time);
}
