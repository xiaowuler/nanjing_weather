package com.nanjing.weather.dao;

import com.nanjing.weather.entity.RainFall;
import com.nanjing.weather.entity.RainFallCenter;
import com.nanjing.weather.entitys.Rainfall;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RainfallMapper {

    List<RainFall> findAllBySomeTerm(RainFallCenter rainFallCenter);

    List<Rainfall> findSomeTerm(Map map);
}
