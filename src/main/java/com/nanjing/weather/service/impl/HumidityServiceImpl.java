package com.nanjing.weather.service.impl;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.HumidityMapper;
import com.nanjing.weather.domain.Humidities;
import com.nanjing.weather.entity.Humiditie;
import com.nanjing.weather.entity.HumiditieCenter;
import com.nanjing.weather.entitys.Humidity;
import com.nanjing.weather.service.HumidityService;
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
public class HumidityServiceImpl implements HumidityService {

    @Autowired
    private HumidityMapper humiditiesMapper;

    @Override
    public ContourResult<Humidity> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        List<ValuePoint> list;
        List<Humidity> humiditiesList;
        HumiditieCenter humiditieCenter = new HumiditieCenter();
        humiditieCenter.setValue(new BigDecimal(parmOne.substring(1)));
        if (time != null) {
            humiditieCenter.setCreateTime(time.split(":")[0]);
            humiditieCenter.setRoutineTime(time.split(":")[1]);
        }
        List<Humiditie> allBySomeTerm = humiditiesMapper.findAllBySomeTerm(humiditieCenter);
        humiditiesList = CodeIntegration.caleAvg(allBySomeTerm,"getHumiditieCenter","getValue","com.nanjing.weather.entitys.Humidity","setValue");

        list = CodeIntegration.getValuePoint(humiditiesList, "getStationId", "getValue");

        if (time != null)
            return CodeIntegration.getResult("humidities", list, time.split(":")[1]);
        return CodeIntegration.getResult("humidities", list, TimeFormat.getTime(allBySomeTerm.get(0).getHumiditieCenter().get(0).getRoutineTime()));

    }


}
