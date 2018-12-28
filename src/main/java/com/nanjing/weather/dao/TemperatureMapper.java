package com.nanjing.weather.dao;

import com.nanjing.weather.entity.Temperature;
import com.nanjing.weather.entity.TemperatureCenter;
import com.nanjing.weather.entity.TemperatureNinMax;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface TemperatureMapper {

    List<Temperature> findAllBySomeDataHH(TemperatureCenter temperatureCenter);

    List<Temperature> findAllBySomeDataHh(TemperatureCenter temperatureCenter);

    List<Temperature> findAllBySomeDatahh(TemperatureNinMax temperatureNinMax);

    List<Temperature> findAllBySomeDatahH(TemperatureNinMax temperatureNinMax);

}
