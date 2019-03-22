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
import java.text.DecimalFormat;
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
            List<com.nanjing.weather.entity.Temperature> temperatureOne;
            String numParm = parmTwo.substring(0, 1);
            if (numParm.equals("≥")) {
                TemperatureCenter temperatureCenter = new TemperatureCenter();
                temperatureCenter.setValue(new BigDecimal(parmTwo.substring(1)));
                temperatureOne = temperaturesMapper.findAllBySomeDataOfHour(temperatureCenter);
                temperatureCenter.setRoutineTime("24");
                temperatureList = temperaturesMapper.findAllBySomeDataOfHour(temperatureCenter);
            } else {
                TemperatureCenter temperatureCenter = new TemperatureCenter();
                temperatureCenter.setValue(new BigDecimal(parmTwo.substring(1)));
                temperatureOne = temperaturesMapper.findAllBySomeDataOfTime(temperatureCenter);
                temperatureCenter.setRoutineTime("24");
                temperatureList = temperaturesMapper.findAllBySomeDataOfTime(temperatureCenter);
            }
            temperatures = handler24HourWarming(temperatureList, temperatureOne);
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

    /**
    * @Description: 处理24小时变温
    * @Param: [oneHours, twHours]
    * @return: java.util.List<com.nanjing.weather.entitys.Temperature>
    * @Author: XW
    * @Date: 2019/3/22
    * @Modify: 无
    */
    private List<Temperature> handler24HourWarming(List<com.nanjing.weather.entity.Temperature> oneHours, List<com.nanjing.weather.entity.Temperature> twHours){
        List<Temperature> temperatures = new ArrayList<>();
        for (com.nanjing.weather.entity.Temperature temperature: oneHours) {
            boolean flag = true;
            Temperature tem = new Temperature();
            tem.setStationId(temperature.getStationId());
            for (com.nanjing.weather.entity.Temperature temperatureTw : twHours){
                if (temperatureTw.getStationId().equals(temperature.getStationId())){
                    tem.setValue(new BigDecimal(new DecimalFormat("#.00").format(temperature.getTemperatureCenter().get(temperature.getTemperatureCenter().size()-1).getValue().subtract(temperatureTw.getTemperatureCenter().get(temperatureTw.getTemperatureCenter().size()-1).getValue()))));
                    flag = false;
                    break;
                }
            }
            if(flag){
                tem.setValue(new BigDecimal(new DecimalFormat("#.00").format(temperature.getTemperatureCenter().get(temperature.getTemperatureCenter().size()-1).getValue())));
            }
            temperatures.add(tem);
        }
        return temperatures;
    }
}
