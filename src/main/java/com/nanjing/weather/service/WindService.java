package com.nanjing.weather.service;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Winds;

public interface WindService {

    /**
     * 返回wContour结果集
     */
    ContourResult<Winds> findAllBySomeTerm(String parmsOne, String parmsTwo, String time);
}
