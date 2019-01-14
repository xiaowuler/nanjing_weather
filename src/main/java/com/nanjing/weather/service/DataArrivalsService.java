package com.nanjing.weather.service;

import com.nanjing.weather.domain.DataArrivals;
import com.nanjing.weather.domain.DataState;
import com.nanjing.weather.utils.PageResult;

import java.util.List;

public interface DataArrivalsService {
    //六要素查询
    PageResult<DataArrivals> findAllType(Integer page, Integer rows, String type);
    //六要素根据时间段查询
    PageResult<DataArrivals> findAllType1(String startTime, String endTime, Integer page, Integer rows, String type);


    PageResult<DataArrivals> findByType(Integer page, Integer rows, String type,String regionCode);
    //根据时间段查询
    PageResult<DataArrivals> findByType1(String startTime, String endTime, Integer page, Integer rows, String type,String regionCode);

    PageResult<DataArrivals> findByTiming(Integer page, Integer rows, String type,String regionCode);

    PageResult<DataArrivals> findByHalfTime(Integer page, Integer rows, String type,String regionCode);

    List<List<DataState>> findState();

    List<List<DataState>> findDataState();
}
