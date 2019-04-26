package com.nanjing.weather.dao;

import com.nanjing.weather.domain.DataArrivals;
import com.nanjing.weather.domain.DataState;
import com.nanjing.weather.entity.DataArrival;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface DataArrivalsMapper {

    List<DataArrivals> findAllType(Map map);//

    List<DataArrivals> findAllTypeAndTime(Map map);//

    List<DataArrivals> findByType(Map map);

    Long findCount(String type);

    List<DataArrivals> findByTypeAndTime(Map map);

    Long findCount1(Map map);


    List<DataArrivals> findByTiming(Map map);


    List<DataArrivals> findByHalfTime(Map map);

    List<DataState> findState(String maxTime);

    List<DataState> findNotRoutine(String maxTime);

    List<DataState> findRoutineData(String maxTime);

    List<DataState> findWindData(String maxTime);

    List<DataArrival> findMaxTime();

    DataArrival findMaxByProductCategory(String productCategory);

}
