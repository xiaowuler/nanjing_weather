package com.nanjing.weather.dao;

import com.nanjing.weather.domain.Pressures;
import com.nanjing.weather.entity.Pressure;
import com.nanjing.weather.entity.PressureCenter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PressuresMapper {
    public List<Pressures> findAll();

    public Pressures findPressuresByid(String stationId);

    public void add(Pressures pressures);

    public void update(Pressures pressures);

    public void delete(String stationIds);

    List<Pressures> findAllByTerm();

    Pressures findAllByAvg(String time,String id);

    List<Pressure> findAllBySomeTerm(PressureCenter pressureCenter);
}
