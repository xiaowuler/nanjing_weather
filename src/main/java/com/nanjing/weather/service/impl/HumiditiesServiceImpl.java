package com.nanjing.weather.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.HumiditiesMapper;
import com.nanjing.weather.dao.LegendLevelMapper;
import com.nanjing.weather.dao.StationsMapper;
import com.nanjing.weather.domain.Humidities;
import com.nanjing.weather.entity.Humiditie;
import com.nanjing.weather.entity.HumiditieCenter;
import com.nanjing.weather.service.HumiditiesService;
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
public class HumiditiesServiceImpl implements HumiditiesService {
    @Autowired
    private HumiditiesMapper humiditiesMapper;
    @Autowired
    private StationsMapper stationsMapper;
    @Autowired
    private LegendLevelMapper legendLevelMapper;

    @Override
    public List<Humidities> findAll() {
        return humiditiesMapper.findAll();
    }


    @Override
    public Humidities findHumiditiesByid(String stationId) {
        return humiditiesMapper.findHumiditiesByid(stationId);
    }

    @Override
    public void add(Humidities humidities) {

    }

    @Override
    public void update(Humidities humidities) {

    }

    @Override
    public void delete(String[] stationIds) {

    }

    @Override
    public PageInfo<Humidities> findAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Humidities> all = humiditiesMapper.findAll();
        PageInfo<Humidities> info = new PageInfo<Humidities>(all);
        return info;
    }

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
