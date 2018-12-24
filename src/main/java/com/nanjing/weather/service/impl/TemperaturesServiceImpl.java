package com.nanjing.weather.service.impl;

import com.nanjing.wContour.ContourHelper;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.LegendLevel;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.LegendLevelMapper;
import com.nanjing.weather.dao.StationsMapper;
import com.nanjing.weather.dao.TemperaturesMapper;
import com.nanjing.weather.domain.Stations;
import com.nanjing.weather.domain.Temperatures;
import com.nanjing.weather.entity.Temperature;
import com.nanjing.weather.entity.TemperatureCenter;
import com.nanjing.weather.entity.TemperatureNinMax;
import com.nanjing.weather.service.TemperaturesService;
import com.nanjing.weather.utils.CodeIntegration;
import com.nanjing.weather.utils.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TemperaturesServiceImpl implements TemperaturesService {
    @Autowired
    private TemperaturesMapper temperaturesMapper;
    @Autowired
    private StationsMapper stationsMapper;
    @Autowired
    private LegendLevelMapper legendLevelMapper;


    @Override
    public ContourResult findAll(Integer time,Double railfalls) {
        return null;
    }

    @Override
    public Temperatures findTemperaturesByid(String stationId) {
        return temperaturesMapper.findTemperaturesByid(stationId);
    }

    @Override
    public void add(Temperatures temperatures) {

    }

    @Override
    public void update(Temperatures temperatures) {

    }

    @Override
    public void delete(String[] stationIds) {

    }

    @Override
    public ContourResult<Temperatures> findAllByTerm(String parm1, String parm2) {
        List<ValuePoint> list;
        List<Temperatures> temperatures ;
        //获取温度值
        Integer num = Integer.parseInt(parm2.substring(1));
        //判断前端传值信息
        if(parm1.equals("温度")){
            String numParm = parm2.substring(0,1);
            if(numParm.equals("≥")){
                temperatures= temperaturesMapper.findAllByTemHigh(num);
            }else{
                temperatures= temperaturesMapper.findAllByTemLow(num);
            }
        }else if(parm1.equals("最高温度")){
            if(num >= 0){
                temperatures= temperaturesMapper.findAllByTemHHigh(num);
            }else{
                temperatures= temperaturesMapper.findAllByTemHLow(num);
            }
        }else if(parm1.equals("最低温度")){
            if(num >= 0){
                temperatures= temperaturesMapper.findAllByTemLHigh(num);
            }else{
                temperatures= temperaturesMapper.findAllByTemLLow(num);
            }
        }else{
            if(num >= 0){
                temperatures= temperaturesMapper.findAllByTemTHigh(24,num);
            }else{
                temperatures= temperaturesMapper.findAllByTemTLow(24,num);
            }
        }
        if(temperatures.size()>0){
            list = CodeIntegration.getValuePoint(temperatures,"getStation_Id","getValue");
            if(list.size()>0){
                return CodeIntegration.getResult("temperatures",list,TimeFormat.getTime(temperatures.get(0).getRoutine_Time()));
            }
        }
        return null;
    }

    @Override
    public ContourResult<Temperatures> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        List<ValuePoint> list;
        List<Temperatures> temperatures = new ArrayList<>();
        List<Temperature> temperatureList;
        Integer num = Integer.parseInt(parmTwo.substring(1));
        if(parmOne.equals("温度")){
            String numParm = parmTwo.substring(0,1);
            if(numParm.equals("≥")){
                TemperatureCenter temperatureCenter = new TemperatureCenter();
                temperatureCenter.setValue(new BigDecimal(parmTwo.substring(1)));
                temperatureCenter.setCreateTime(time.split(":")[0]);
                temperatureCenter.setRoutineTime(time.split(":")[1]);
                temperatureList = temperaturesMapper.findAllBySomeDataHH(temperatureCenter);
            }else{
                TemperatureCenter temperatureCenter = new TemperatureCenter();
                temperatureCenter.setValue(new BigDecimal(parmTwo.substring(1)));
                temperatureCenter.setCreateTime(time.split(":")[0]);
                temperatureCenter.setRoutineTime(time.split(":")[1]);
                temperatureList = temperaturesMapper.findAllBySomeDataHh(temperatureCenter);
            }
            if(temperatureList.size()>0){
                for(Temperature temperature:temperatureList){
                    double x = 0;
                    double y = 0;
                    for(TemperatureCenter center:temperature.getTemperatureCenter()){
                        if(Double.parseDouble(center.getValue().toString())<999){
                            x += Double.parseDouble(center.getValue().toString());
                            y++;
                        }
                    }
                    if(y != 0){
                        Temperatures tempera = new Temperatures();
                        tempera.setStation_Id(temperature.getStationId());
                        tempera.setValue(new BigDecimal(new DecimalFormat("#.00").format(x/y)));
                        temperatures.add(tempera);
                    }
                }
            }
        }else if(parmOne.equals("最高温度")){
            if(num >= 0){
                TemperatureNinMax temperatureNinMax = new TemperatureNinMax();
                temperatureNinMax.setMaxValue(new BigDecimal(parmTwo.substring(1)));
                temperatureNinMax.setCreateTime(time.split(":")[0]);
                temperatureNinMax.setRoutineTime(time.split(":")[1]);
                temperatureList = temperaturesMapper.findAllBySomeDatahh(temperatureNinMax);
            }else{
                TemperatureNinMax temperatureNinMax = new TemperatureNinMax();
                temperatureNinMax.setMaxValue(new BigDecimal(parmTwo.substring(1)));
                temperatureNinMax.setCreateTime(time.split(":")[0]);
                temperatureNinMax.setRoutineTime(time.split(":")[1]);
                temperatureList = temperaturesMapper.findAllBySomeDatahH(temperatureNinMax);
            }
            if(temperatureList.size()>0){
                for(Temperature temperature:temperatureList){
                    double x = 0;
                    for(TemperatureNinMax temperatureNinMax:temperature.getTemperatureNinMaxe()){
                        if(Double.parseDouble(temperatureNinMax.getMaxValue().toString())<999){
                            if(Double.parseDouble(temperatureNinMax.getMaxValue().toString())>x){
                                x = Double.parseDouble(temperatureNinMax.getMaxValue().toString());
                            }
                        }
                    }
                    Temperatures tempera = new Temperatures();
                    tempera.setStation_Id(temperature.getStationId());
                    tempera.setValue(new BigDecimal(x));
                    temperatures.add(tempera);
                }
            }
        }else if(parmOne.equals("最低温度")){
            if(num >= 0){
                TemperatureNinMax temperatureNinMax = new TemperatureNinMax();
                temperatureNinMax.setMinValue(new BigDecimal(parmTwo.substring(1)));
                temperatureNinMax.setCreateTime(time.split(":")[0]);
                temperatureNinMax.setRoutineTime(time.split(":")[1]);
                temperatureList = temperaturesMapper.findAllBySomeDatahh(temperatureNinMax);
            }else{
                TemperatureNinMax temperatureNinMax = new TemperatureNinMax();
                temperatureNinMax.setMinValue(new BigDecimal(parmTwo.substring(1)));
                temperatureNinMax.setCreateTime(time.split(":")[0]);
                temperatureNinMax.setRoutineTime(time.split(":")[1]);
                temperatureList = temperaturesMapper.findAllBySomeDatahH(temperatureNinMax);
            }
            if(temperatureList.size()>0){
                for(Temperature temperature:temperatureList){
                    double x = 0;
                    for(TemperatureNinMax temperatureNinMax:temperature.getTemperatureNinMaxe()){
                        if(Double.parseDouble(temperatureNinMax.getMinValue().toString())<999){
                            if(Double.parseDouble(temperatureNinMax.getMinValue().toString())<x){
                                x = Double.parseDouble(temperatureNinMax.getMinValue().toString());
                            }
                        }
                    }
                    Temperatures tempera = new Temperatures();
                    tempera.setStation_Id(temperature.getStationId());
                    tempera.setValue(new BigDecimal(x));
                    temperatures.add(tempera);
                }
            }
        }
        if(temperatures.size()>0){
            list = CodeIntegration.getValuePoint(temperatures,"getStation_Id","getValue");
            if(list.size()>0){
                return CodeIntegration.getResult("temperatures",list,time.split(":")[1]);
            }
        }
        return null;
    }
}
