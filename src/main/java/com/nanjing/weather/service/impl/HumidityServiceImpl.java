package com.nanjing.weather.service.impl;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.HumidityMapper;
import com.nanjing.weather.domain.Humidities;
import com.nanjing.weather.entity.Humiditie;
import com.nanjing.weather.entity.HumiditieCenter;
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
    public ContourResult<Humidities> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        List<ValuePoint> list;
        List<Humidities> humiditiesList = new ArrayList<>();
        HumiditieCenter humiditieCenter = new HumiditieCenter();
        humiditieCenter.setValue(new BigDecimal(parmOne.substring(1)));
        if (time != null) {
            humiditieCenter.setCreateTime(time.split(":")[0]);
            humiditieCenter.setRoutineTime(time.split(":")[1]);
        }
        List<Humiditie> allBySomeTerm = humiditiesMapper.findAllBySomeTerm(humiditieCenter);
        for (Humiditie humiditie : allBySomeTerm) {
            double x = 0;
            double y = 0;
            for (HumiditieCenter center : humiditie.getHumiditieCenter()) {
                if (Double.parseDouble(center.getValue().toString()) < 999) {
                    x += Double.parseDouble(center.getValue().toString());
                    y++;
                }
            }
            if (y != 0) {
                Humidities hum = new Humidities();
                hum.setStation_Id(humiditie.getStationId());
                hum.setValue(new BigDecimal(new DecimalFormat("#.00").format(x / y)));
                humiditiesList.add(hum);
            }
        }

        if (humiditiesList.size() > 0) {
            list = CodeIntegration.getValuePoint(humiditiesList, "getStation_Id", "getValue");
            if (list.size() > 0) {
                if (time != null) {
                    return CodeIntegration.getResult("humidities", list, time.split(":")[1]);
                } else {
                    return CodeIntegration.getResult("humidities", list, TimeFormat.getTime(allBySomeTerm.get(0).getHumiditieCenter().get(0).getRoutineTime()));
                }
            }
        }
        return null;
    }


}
