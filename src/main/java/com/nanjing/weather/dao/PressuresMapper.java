package com.nanjing.weather.dao;

import com.nanjing.weather.domain.Pressures;
import com.nanjing.weather.entity.Pressure;
import com.nanjing.weather.entity.PressureCenter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PressuresMapper {
    List<Pressures> findAll();

    Pressures findPressuresByid(String stationId);

    void add(Pressures pressures);

    void update(Pressures pressures);

    void delete(String stationIds);

    List<Pressure> findAllBySomeTerm(PressureCenter pressureCenter);
}
