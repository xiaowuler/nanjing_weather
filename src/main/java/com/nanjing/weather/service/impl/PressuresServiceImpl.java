package com.nanjing.weather.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.LegendLevelMapper;
import com.nanjing.weather.dao.PressuresMapper;
import com.nanjing.weather.dao.StationsMapper;
import com.nanjing.weather.domain.Pressures;
import com.nanjing.weather.entity.Pressure;
import com.nanjing.weather.entity.PressureCenter;
import com.nanjing.weather.service.PressuresService;
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
public class PressuresServiceImpl implements PressuresService {


    @Autowired
    private PressuresMapper pressuresMapper;

    @Autowired
    private StationsMapper stationsMapper;

    @Autowired
    private LegendLevelMapper legendLevelMapper;

    @Override
    public List<Pressures> findAll() {
        return pressuresMapper.findAll();
    }

    @Override
    public Pressures findPressuresByid(String stationId) {
        return pressuresMapper.findPressuresByid(stationId);
    }

    @Override
    public void add(Pressures pressures) {

    }

    @Override
    public void update(Pressures pressures) {

    }

    @Override
    public void delete(String[] stationIds) {

    }

    @Override
    public PageInfo<Pressures> findAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Pressures> all = pressuresMapper.findAll();
        PageInfo<Pressures> info = new PageInfo<Pressures>(all);
        return info;
    }

    @Override
    public ContourResult<Pressures> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        List<ValuePoint> list;
        List<Pressures> pressuresList = new ArrayList<>();
        PressureCenter pressureCenter = new PressureCenter();
        if (parmOne.equals("本站气压")) {
            if (time != null) {
                pressureCenter.setCreateTime(time.split(":")[0]);
                pressureCenter.setRoutineTime(time.split(":")[1]);
            }
        } else {
            pressureCenter.setRoutineTime("24");
        }
        List<Pressure> allBySomeMap = pressuresMapper.findAllBySomeTerm(pressureCenter);
        for (Pressure pressure : allBySomeMap) {
            double x = 0;
            double y = 0;
            for (PressureCenter center : pressure.getPressureCenter()) {
                if (Double.parseDouble(center.getValue().toString()) < 9999) {
                    x += Double.parseDouble(center.getValue().toString());
                    y++;
                }
            }
            if (y != 0) {
                Pressures pre = new Pressures();
                pre.setStation_Id(pressure.getStationId());
                pre.setValue(new BigDecimal(new DecimalFormat("#.00").format(x / y)));
                pressuresList.add(pre);
            }
        }

        if (pressuresList.size() > 0) {
            list = CodeIntegration.getValuePoint(pressuresList, "getStation_Id", "getValue");
            if (list.size() > 0) {
                if (time == null) {
                    return CodeIntegration.getResult("pressures", list, TimeFormat.getTime(allBySomeMap.get(0).getPressureCenter().get(0).getRoutineTime()));
                } else {
                    return CodeIntegration.getResult("pressures", list, time.split(":")[1]);
                }
            }
        }
        return null;
    }
}
