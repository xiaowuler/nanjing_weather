package com.nanjing.weather.dao;

import com.nanjing.weather.domain.Winds;
import com.nanjing.weather.entity.Wind;
import com.nanjing.weather.entity.WindCenter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WindsMapper {

    public List<Winds> findAll();

    public Winds findWindsByid(String stationId);

    public void add(Winds winds);

    public void update(Winds winds);

    public void delete(String stationIds);

    //条件查询风向风速列表
    List<Winds> findAllByZTerm(Integer windSpeed);

    List<Winds> findAllByTTerm(Integer windSpeed);

    List<Winds> findAllByTeTerm(Integer windSpeed);

    List<Winds> demo(String name);

    List<Wind> findAllBySomeTerm(WindCenter windCenter);
}
