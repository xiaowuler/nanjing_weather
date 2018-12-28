package com.nanjing.weather.dao;

import com.nanjing.weather.entity.Pressure;
import com.nanjing.weather.entity.PressureCenter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PressureMapper {

    List<Pressure> findAllBySomeTerm(PressureCenter pressureCenter);
}
