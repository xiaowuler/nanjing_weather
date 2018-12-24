package com.nanjing.weather.dao;

import com.nanjing.weather.domain.Humidities;
import com.nanjing.weather.domain.Stations;
import com.nanjing.weather.entity.Humiditie;
import com.nanjing.weather.entity.HumiditieCenter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HumiditiesMapper {
    public List<Humidities> findAll();

    public Humidities findHumiditiesByid(String stationId);

    public void add(Humidities humidities);

    public void update(Humidities humidities);

    public void delete(String stationIds);

    /**
     * 根据条件查询
     *
     */
    List<Humidities> findAllByTerm(Integer value);

    List<Humiditie> findAllBySomeTerm(HumiditieCenter humiditieCenter);
}
