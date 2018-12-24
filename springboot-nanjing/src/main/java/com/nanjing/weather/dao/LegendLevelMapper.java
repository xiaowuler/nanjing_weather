package com.nanjing.weather.dao;

import com.nanjing.wContour.bean.LegendLevel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LegendLevelMapper {
    public List<LegendLevel> findAll(String type);
}
