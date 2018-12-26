package com.nanjing.weather.dao;

import com.nanjing.weather.domain.Rainfalls;
import com.nanjing.weather.entity.RainFall;
import com.nanjing.weather.entity.RainFallCenter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RainfallsMapper {

    List<Rainfalls> findAll();

    Rainfalls findRainfallsByid(String stationId);

    void add(Rainfalls rainfalls);

    void update(Rainfalls rainfalls);

    void delete(String stationIds);

    List<RainFall> findAllBySomeTerm(RainFallCenter rainFallCenter);
}
