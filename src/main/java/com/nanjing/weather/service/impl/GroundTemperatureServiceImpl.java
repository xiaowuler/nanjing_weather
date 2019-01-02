package com.nanjing.weather.service.impl;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.GroundTemperatureMapper;
import com.nanjing.weather.dao.LegendLevelMapper;
import com.nanjing.weather.dao.StationsMapper;
import com.nanjing.weather.domain.GroundTemperatures;
import com.nanjing.weather.entity.GroupTemperature;
import com.nanjing.weather.entity.GroupTemperatureCenter;
import com.nanjing.weather.entitys.GroundTemperature;
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


    @Override
    public ContourResult<GroundTemperature> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        List<GroundTemperature> groundTemperatures = new ArrayList<>();
        List<ValuePoint> list;
        GroupTemperatureCenter groupTemperatureCenter = new GroupTemperatureCenter();
        if (time != null) {
            groupTemperatureCenter.setCreateTime(time.split(":")[0]);
            groupTemperatureCenter.setRoutineTime(time.split(":")[1]);
        }
        List<GroupTemperature> groundTemperature = groundTemperatureMapper.findAllBySomeTerm(groupTemperatureCenter);
        if (parmOne.equals("0"))
            groundTemperatures = CodeIntegration.caleAvg(groundTemperature,"getGroupTemperatureCenter","getValue","com.nanjing.weather.entitys.GroundTemperature","setValue");
        else if (parmOne.equals("5"))
            groundTemperatures = CodeIntegration.caleAvg(groundTemperature,"getGroupTemperatureCenter","getValue5Cm","com.nanjing.weather.entitys.GroundTemperature","setValue");
        else if (parmOne.equals("10"))
            groundTemperatures = CodeIntegration.caleAvg(groundTemperature,"getGroupTemperatureCenter","getValue10Cm","com.nanjing.weather.entitys.GroundTemperature","setValue");
        else if (parmOne.equals("15"))
            groundTemperatures = CodeIntegration.caleAvg(groundTemperature,"getGroupTemperatureCenter","getValue15Cm","com.nanjing.weather.entitys.GroundTemperature","setValue");
        else if (parmOne.equals("20"))
            groundTemperatures = CodeIntegration.caleAvg(groundTemperature,"getGroupTemperatureCenter","getValue20Cm","com.nanjing.weather.entitys.GroundTemperature","setValue");
        else if (parmOne.equals("40"))
            groundTemperatures = CodeIntegration.caleAvg(groundTemperature,"getGroupTemperatureCenter","getValue40Cm","com.nanjing.weather.entitys.GroundTemperature","setValue");

        list = CodeIntegration.getValuePoint(groundTemperatures, "getStationId", "getValue");

        if(groundTemperature != null && groundTemperature.size()>0){
            if (time != null)
                return CodeIntegration.getResult("groundTemperature", list, time.split(":")[1]);
            return CodeIntegration.getResult("groundTemperature", list, TimeFormat.getTime(groundTemperature.get(0).getGroupTemperatureCenter().get(0).getRoutineTime()));
        }
        return null;
    }
}
