package com.nanjing.weather.dao;

import com.nanjing.weather.domain.Winds;
import com.nanjing.weather.entity.Wind;
import com.nanjing.weather.entity.WindCenter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WindsMapper {

    List<Winds> findAll();

    Winds findWindsByid(String stationId);

    void add(Winds winds);

    void update(Winds winds);

    void delete(String stationIds);

    List<Winds> demo(String name);

    //条件查询风向风速列表
    List<Wind> findAllBySomeTerm(WindCenter windCenter);
}
