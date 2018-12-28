package com.nanjing.weather.service.impl;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.LegendLevelMapper;
import com.nanjing.weather.dao.StationsMapper;
import com.nanjing.weather.dao.TemperaturesMapper;
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
    public ContourResult findAll(Integer time, Double railfalls) {
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
    public ContourResult<Temperatures> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        List<ValuePoint> list;
        List<Temperatures> temperatures = new ArrayList<>();
        List<Temperature> temperatureList = new ArrayList<>();
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
            if (temperatureList.size() > 0) {
                for (Temperature temperature : temperatureList) {
                    double x = 0;
                    double y = 0;
                    for (TemperatureCenter center : temperature.getTemperatureCenter()) {
                        if (Double.parseDouble(center.getValue().toString()) < 999) {
                            x += Double.parseDouble(center.getValue().toString());
                            y++;
                        }
                    }
                    if (y != 0) {
                        Temperatures tempera = new Temperatures();
                        tempera.setStation_Id(temperature.getStationId());
                        tempera.setValue(new BigDecimal(new DecimalFormat("#.00").format(x / y)));
                        temperatures.add(tempera);
                    }
                }
            }
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
            if (temperatureList.size() > 0) {
                for (Temperature temperature : temperatureList) {
                    double x = 0;
                    for (TemperatureNinMax temperatureNinMax : temperature.getTemperatureNinMaxe()) {
                        if (Double.parseDouble(temperatureNinMax.getMaxValue().toString()) < 999) {
                            if (Double.parseDouble(temperatureNinMax.getMaxValue().toString()) > x) {
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
            if (temperatureList.size() > 0) {
                for (Temperature temperature : temperatureList) {
                    double x = 0;
                    for (TemperatureNinMax temperatureNinMax : temperature.getTemperatureNinMaxe()) {
                        if (Double.parseDouble(temperatureNinMax.getMinValue().toString()) < 999) {
                            if (Double.parseDouble(temperatureNinMax.getMinValue().toString()) < x) {
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
            if (temperatureList.size() > 0) {
                for (Temperature temperature : temperatureList) {
                    double x = 0;
                    double y = 0;
                    for (TemperatureCenter center : temperature.getTemperatureCenter()) {
                        if (Double.parseDouble(center.getValue().toString()) < 999) {
                            x += Double.parseDouble(center.getValue().toString());
                            y++;
                        }
                    }
                    if (y != 0) {
                        Temperatures tempera = new Temperatures();
                        tempera.setStation_Id(temperature.getStationId());
                        tempera.setValue(new BigDecimal(new DecimalFormat("#.00").format(x / y)));
                        temperatures.add(tempera);
                    }
                }
            }
        }
        if (temperatures.size() > 0) {
            list = CodeIntegration.getValuePoint(temperatures, "getStation_Id", "getValue");
            if (list.size() > 0) {
                if (time != null) {
                    return CodeIntegration.getResult("temperatures", list, time.split(":")[1]);
                } else {
                    return CodeIntegration.getResult("temperatures", list, TimeFormat.getTime(temperatureList.get(0).getTemperatureCenter().get(0).getRoutineTime()));
                }
            }
        }
        return null;
    }
}
