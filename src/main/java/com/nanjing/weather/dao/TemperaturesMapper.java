package com.nanjing.weather.dao;

import com.nanjing.weather.domain.Temperatures;
import com.nanjing.weather.entity.Temperature;
import com.nanjing.weather.entity.TemperatureCenter;
import com.nanjing.weather.entity.TemperatureNinMax;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface TemperaturesMapper {
    public List<Temperatures> findAll(Integer time,Double rainfalls);

    public Temperatures findTemperaturesByid(String stationId);

    public void add(Temperatures temperatures);

    public void update(Temperatures temperatures);

    public void delete(String stationIds);

    List<Temperatures> findAllByTemHigh(Integer parm);

    List<Temperatures> findAllByTemLow(Integer parm);
    List<Temperatures> findAllByTemHHigh(Integer parm);

    List<Temperatures> findAllByTemHLow(Integer parm);
    List<Temperatures> findAllByTemLHigh(Integer parm);

    List<Temperatures> findAllByTemLLow(Integer parm);
    List<Temperatures> findAllByTemTHigh(Integer parm1,Integer parm2);

    List<Temperatures> findAllByTemTLow(Integer parm1,Integer parm2);

    List<Temperature> findAllBySomeDataHH(TemperatureCenter temperatureCenter);
    List<Temperature> findAllBySomeDataHh(TemperatureCenter temperatureCenter);
    List<Temperature> findAllBySomeDatahh(TemperatureNinMax temperatureNinMax);
    List<Temperature> findAllBySomeDatahH(TemperatureNinMax temperatureNinMax);

}
