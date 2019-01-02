package com.nanjing.weather.service.impl;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.TemperatureMapper;
import com.nanjing.weather.entitys.Temperature;
import com.nanjing.weather.entity.TemperatureCenter;
import com.nanjing.weather.entity.TemperatureNinMax;
import com.nanjing.weather.service.TemperatureService;
import com.nanjing.weather.utils.CodeIntegration;
import com.nanjing.weather.utils.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TemperatureServiceImpl implements TemperatureService {

    @Autowired
    private TemperatureMapper temperaturesMapper;

    @Override
    public ContourResult<Temperature> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        List<ValuePoint> list;
        List<Temperature> temperatures = new ArrayList<>();
        List<com.nanjing.weather.entity.Temperature> temperatureList = new ArrayList<>();
        Integer num = Integer.parseInt(parmTwo.substring(1));
        if (parmOne.equals("温度")) {
            String numParm = parmTwo.substring(0, 1);
            if (numParm.equals("≥")) {
                TemperatureCenter temperatureCenter = new TemperatureCenter();
                temperatureCenter.setValue(new BigDecimal(parmTwo.substring(1)));
                if (time != null) {
                    temperatureCenter.setCreateTime(time.split(":")[0]);
                    temperatureCenter.setRoutineTime(time.split(":")[1]);
                }
                temperatureList = temperaturesMapper.findAllBySomeDataHH(temperatureCenter);
            } else {
                TemperatureCenter temperatureCenter = new TemperatureCenter();
                temperatureCenter.setValue(new BigDecimal(parmTwo.substring(1)));
                if (time != null) {
                    temperatureCenter.setCreateTime(time.split(":")[0]);
                    temperatureCenter.setRoutineTime(time.split(":")[1]);
                }
                temperatureList = temperaturesMapper.findAllBySomeDataHh(temperatureCenter);
            }
            temperatures = CodeIntegration.caleAvg(temperatureList,"getTemperatureCenter","getValue","com.nanjing.weather.entitys.Temperature","setValue");

        } else if (parmOne.equals("最高温度")) {
            if (num >= 0) {
                TemperatureNinMax temperatureNinMax = new TemperatureNinMax();
                temperatureNinMax.setMaxValue(new BigDecimal(parmTwo.substring(1)));
                if (time != null) {
                    temperatureNinMax.setCreateTime(time.split(":")[0]);
                    temperatureNinMax.setRoutineTime(time.split(":")[1]);
                }
                temperatureList = temperaturesMapper.findAllBySomeDatahh(temperatureNinMax);
            } else {
                TemperatureNinMax temperatureNinMax = new TemperatureNinMax();
                temperatureNinMax.setMaxValue(new BigDecimal(parmTwo.substring(1)));
                if (time != null) {
                    temperatureNinMax.setCreateTime(time.split(":")[0]);
                    temperatureNinMax.setRoutineTime(time.split(":")[1]);
                }
                temperatureList = temperaturesMapper.findAllBySomeDatahH(temperatureNinMax);
            }

            temperatures = CodeIntegration.caleAvg(temperatureList,"getTemperatureNinMaxe","getMaxValue","com.nanjing.weather.entitys.Temperature","setValue");

        } else if (parmOne.equals("最低温度")) {
            if (num >= 0) {
                TemperatureNinMax temperatureNinMax = new TemperatureNinMax();
                temperatureNinMax.setMinValue(new BigDecimal(parmTwo.substring(1)));
                if (time != null) {
                    temperatureNinMax.setCreateTime(time.split(":")[0]);
                    temperatureNinMax.setRoutineTime(time.split(":")[1]);
                }
                temperatureList = temperaturesMapper.findAllBySomeDatahh(temperatureNinMax);
            } else {
                TemperatureNinMax temperatureNinMax = new TemperatureNinMax();
                temperatureNinMax.setMinValue(new BigDecimal(parmTwo.substring(1)));
                if (time != null) {
                    temperatureNinMax.setCreateTime(time.split(":")[0]);
                    temperatureNinMax.setRoutineTime(time.split(":")[1]);
                }
                temperatureList = temperaturesMapper.findAllBySomeDatahH(temperatureNinMax);
            }

            temperatures = CodeIntegration.caleAvg(temperatureList,"getTemperatureNinMaxe","getMinValue","com.nanjing.weather.entitys.Temperature","setValue");
        } else if (parmOne.equals("24小时变温")) {
            String numParm = parmTwo.substring(0, 1);
            if (numParm.equals("≥")) {
                TemperatureCenter temperatureCenter = new TemperatureCenter();
                temperatureCenter.setValue(new BigDecimal(parmTwo.substring(1)));
                temperatureCenter.setRoutineTime("24");
                temperatureList = temperaturesMapper.findAllBySomeDataHH(temperatureCenter);
            } else {
                TemperatureCenter temperatureCenter = new TemperatureCenter();
                temperatureCenter.setValue(new BigDecimal(parmTwo.substring(1)));
                temperatureCenter.setRoutineTime("24");
                temperatureList = temperaturesMapper.findAllBySomeDataHh(temperatureCenter);
            }
            temperatures = CodeIntegration.caleAvg(temperatureList, "getTemperatureCenter", "getValue", "com.nanjing.weather.entitys.Temperature", "setValue");
        }
        list = CodeIntegration.getValuePoint(temperatures, "getStationId", "getValue");
        if (time != null) {
            return CodeIntegration.getResult("temperatures", list, time.split(":")[1]);
        } else {
            if(temperatureList != null && temperatureList.size()>0){
                if(parmOne.equals("最低温度") || parmOne.equals("最高温度"))
                    return CodeIntegration.getResult("temperatures", list, TimeFormat.getTime(temperatureList.get(0).getTemperatureNinMaxe().get(0).getRoutineTime()));
                return CodeIntegration.getResult("temperatures", list, TimeFormat.getTime(temperatureList.get(0).getTemperatureCenter().get(0).getRoutineTime()));
            }
            return null;
        }
    }
}
