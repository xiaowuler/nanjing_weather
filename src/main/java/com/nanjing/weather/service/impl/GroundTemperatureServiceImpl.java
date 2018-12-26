package com.nanjing.weather.service.impl;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.GroundTemperatureMapper;
import com.nanjing.weather.dao.LegendLevelMapper;
import com.nanjing.weather.dao.StationsMapper;
import com.nanjing.weather.domain.GroundTemperatures;
import com.nanjing.weather.entity.GroupTemperature;
import com.nanjing.weather.entity.GroupTemperatureCenter;
import com.nanjing.weather.service.GroundTemperatureService;
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
public class GroundTemperatureServiceImpl implements GroundTemperatureService {

    @Autowired
    private GroundTemperatureMapper groundTemperatureMapper;

    @Autowired
    private LegendLevelMapper legendLevelMapper;

    @Autowired
    private StationsMapper stationsMapper;

    @Override
    public ContourResult<GroundTemperatures> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        List<GroundTemperatures> groundTemperatures = new ArrayList<>();
        List<ValuePoint> list;
        GroupTemperatureCenter groupTemperatureCenter = new GroupTemperatureCenter();
        if (time != null) {
            groupTemperatureCenter.setCreateTime(time.split(":")[0]);
            groupTemperatureCenter.setRoutineTime(time.split(":")[1]);
        }
        List<GroupTemperature> allBySomeTerm = groundTemperatureMapper.findAllBySomeTerm(groupTemperatureCenter);
        for (GroupTemperature groupTemperature : allBySomeTerm) {
            double x = 0;
            double y = 0;
            for (GroupTemperatureCenter center : groupTemperature.getGroupTemperatureCenter()) {
                if (parmOne.equals("0")) {
                    if (Double.parseDouble(center.getValue().toString()) < 9999) {
                        x += Double.parseDouble(center.getValue().toString());
                        y++;
                    }
                } else if (parmOne.equals("5")) {
                    if (Double.parseDouble(center.getValueFivecm().toString()) < 9999) {
                        x += Double.parseDouble(center.getValueFivecm().toString());
                        y++;
                    }
                } else if (parmOne.equals("10")) {
                    if (Double.parseDouble(center.getValueTencm().toString()) < 9999) {
                        x += Double.parseDouble(center.getValueTencm().toString());
                        y++;
                    }
                } else if (parmOne.equals("15")) {
                    if (Double.parseDouble(center.getValueFifcm().toString()) < 9999) {
                        x += Double.parseDouble(center.getValueFifcm().toString());
                        y++;
                    }
                } else if (parmOne.equals("20")) {
                    if (Double.parseDouble(center.getValueTwecm().toString()) < 9999) {
                        x += Double.parseDouble(center.getValueTwecm().toString());
                        y++;
                    }
                } else if (parmOne.equals("40")) {
                    if (Double.parseDouble(center.getValueForcm().toString()) < 9999) {
                        x += Double.parseDouble(center.getValueForcm().toString());
                        y++;
                    }
                }
            }
            if (y != 0) {
                GroundTemperatures gt = new GroundTemperatures();
                gt.setStationId(groupTemperature.getStationId());
                gt.setValue(new BigDecimal(new DecimalFormat("#.00").format(x / y)));
                groundTemperatures.add(gt);
            }
        }

        if (groundTemperatures.size() > 0) {
            list = CodeIntegration.getValuePoint(groundTemperatures, "getStationId", "getValue");
            if (list.size() > 0) {
                if (time != null) {
                    return CodeIntegration.getResult("groundTemperature", list, time.split(":")[1]);
                } else {
                    return CodeIntegration.getResult("groundTemperature", list, TimeFormat.getTime(allBySomeTerm.get(0).getGroupTemperatureCenter().get(0).getRoutineTime()));
                }
            }
        }

        return null;
    }
}
