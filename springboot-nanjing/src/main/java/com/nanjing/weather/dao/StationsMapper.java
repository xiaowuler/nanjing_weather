package com.nanjing.weather.dao;

import com.nanjing.weather.domain.Stations;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StationsMapper {

    public List<Stations> findAll();

    public Stations findStationsByid(String id);

    public void add(Stations stations);

    public void update(Stations stations);

    public void delete(String ids);

}
