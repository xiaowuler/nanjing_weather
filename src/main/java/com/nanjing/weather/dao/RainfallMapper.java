package com.nanjing.weather.dao;

import com.nanjing.weather.entity.RainFall;
import com.nanjing.weather.entity.RainFallCenter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RainfallMapper {

    List<RainFall> findAllBySomeTerm(RainFallCenter rainFallCenter);
}
