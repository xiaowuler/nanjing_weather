package com.nanjing.weather.dao;

import com.nanjing.weather.domain.GroundTemperatures;
import com.nanjing.weather.entity.GroupTemperature;
import com.nanjing.weather.entity.GroupTemperatureCenter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroundTemperatureMapper {

    List<GroundTemperatures> findAllByTerm();

    List<GroupTemperature> findAllBySomeTerm(GroupTemperatureCenter groupTemperatureCenter);
}
