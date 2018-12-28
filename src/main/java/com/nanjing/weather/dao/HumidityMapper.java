package com.nanjing.weather.dao;

import com.nanjing.weather.domain.Humidities;
import com.nanjing.weather.entity.Humiditie;
import com.nanjing.weather.entity.HumiditieCenter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HumidityMapper {

    /**
     * 根据条件查询
     */
    List<Humiditie> findAllBySomeTerm(HumiditieCenter humiditieCenter);
}
