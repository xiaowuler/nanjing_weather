package com.nanjing.weather.service.impl;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.PressureMapper;
import com.nanjing.weather.entitys.Pressure;
import com.nanjing.weather.entity.PressureCenter;
import com.nanjing.weather.service.PressureService;
import com.nanjing.weather.utils.CodeIntegration;
import com.nanjing.weather.utils.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class PressureServiceImpl implements PressureService {


    @Autowired
    private PressureMapper pressuresMapper;

    @Override
    public ContourResult<Pressure> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        List<ValuePoint> list;
        List<Pressure> pressuresList = new ArrayList<>();
        PressureCenter pressureCenter = new PressureCenter();
        if (parmOne.equals("本站气压")) {
            if (time != null) {
                pressureCenter.setCreateTime(time.split(":")[0]);
                pressureCenter.setRoutineTime(time.split(":")[1]);
            }
        } else {
            pressureCenter.setRoutineTime("24");
        }
        List<com.nanjing.weather.entity.Pressure> allBySomeMap = pressuresMapper.findAllBySomeTerm(pressureCenter);
        pressuresList = CodeIntegration.caleAvg(allBySomeMap,"getPressureCenter","getValue","com.nanjing.weather.entitys.Pressure","setValue");

        list = CodeIntegration.getValuePoint(pressuresList, "getStationId", "getValue");

        if(allBySomeMap != null && allBySomeMap.size()>0){
            if (time == null)
                return CodeIntegration.getResult("pressures", list, TimeFormat.getTime(allBySomeMap.get(0).getPressureCenter().get(0).getRoutineTime()));
            return CodeIntegration.getResult("pressures", list, time.split(":")[1]);
        }
        return null;
    }
}
