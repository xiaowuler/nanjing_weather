package com.nanjing.weather.service;

import com.nanjing.weather.domain.DataArrivals;
import com.nanjing.weather.domain.DataState;
import com.nanjing.weather.utils.PageResult;

import java.util.List;

public interface DataArrivalsService {
    PageResult<DataArrivals> findByType(Integer page, Integer rows, String type) ;
    //根据时间段查询
    PageResult<DataArrivals> findByType1(String startTime,String endTime,Integer page, Integer rows, String type ) ;

    PageResult<DataArrivals> findByTiming(Integer page,Integer rows, String type);

    PageResult<DataArrivals> findByHalfTime(Integer page,Integer rows, String type);

    List<List<DataState>> findState();
}
