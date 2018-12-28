package com.nanjing.weather.dao;

import com.nanjing.weather.domain.Humidities;
import com.nanjing.weather.entity.Humiditie;
import com.nanjing.weather.entity.HumiditieCenter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HumiditiesMapper {
    List<Humidities> findAll();

    Humidities findHumiditiesByid(String stationId);

    void add(Humidities humidities);

    void update(Humidities humidities);

    void delete(String stationIds);

    /**
     * 根据条件查询
     */
    List<Humiditie> findAllBySomeTerm(HumiditieCenter humiditieCenter);
}
