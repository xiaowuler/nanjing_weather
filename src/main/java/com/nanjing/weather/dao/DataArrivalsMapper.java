package com.nanjing.weather.dao;

import com.nanjing.weather.domain.DataArrivals;
import com.nanjing.weather.domain.DataState;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataArrivalsMapper {
    List<DataArrivals> findByType(Map map);


    Long findCount(String type);

    List<DataArrivals> findByTypeAndTime(Map map);

    Long findCount1(Map map);


    List<DataArrivals> findByTiming(Map map);


    List<DataArrivals> findByHalfTime(Map map);

    List<DataState> findState();

    List<DataState> findRoutine();

    List<DataState> findWindData();
}
