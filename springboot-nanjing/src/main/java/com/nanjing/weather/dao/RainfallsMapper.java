package com.nanjing.weather.dao;

import com.nanjing.weather.domain.Rainfalls;
import com.nanjing.weather.domain.Temperatures;
import com.nanjing.weather.entity.RainFall;
import com.nanjing.weather.entity.RainFallCenter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RainfallsMapper {

    public List<Rainfalls> findAll();

    public Rainfalls findRainfallsByid(String stationId);

    public void add(Rainfalls rainfalls);

    public void update(Rainfalls rainfalls);

    public void delete(String stationIds);

    List<Rainfalls> findAllByTerm(Integer time, Double rainfalls);

    List<RainFall> findAllBySomeTerm(RainFallCenter rainFallCenter);
}
