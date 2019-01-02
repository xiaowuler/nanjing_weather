package com.nanjing.weather.service.impl;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.RainfallMapper;
import com.nanjing.weather.domain.Rainfalls;
import com.nanjing.weather.entity.RainFall;
import com.nanjing.weather.entity.RainFallCenter;
import com.nanjing.weather.entitys.Rainfall;
import com.nanjing.weather.service.RainfallService;
import com.nanjing.weather.utils.CodeIntegration;
import com.nanjing.weather.utils.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class RainfallServiceImpl implements RainfallService {

    @Autowired
    private RainfallMapper rainfallsMapper;

    /**
     *
     * @param parmOne
     * @param parmTwo
     * @param time 时间选段参数
     * @return 绘图数据
     */
    @Override
    public ContourResult<Rainfall> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        List<ValuePoint> list;
        List<Rainfalls> rainfallsList;
        RainFallCenter rainFallCenter = new RainFallCenter();
        if (time != null) {
            rainFallCenter.setValue(new BigDecimal(parmOne.substring(1)));
            rainFallCenter.setCreateTime(time.split(":")[0]);
            rainFallCenter.setRoutineTime(time.split(":")[1]);
        } else {
            rainFallCenter.setValue(new BigDecimal(parmTwo));
            rainFallCenter.setRoutineTime(parmOne);
        }
        List<RainFall> rainFalls = rainfallsMapper.findAllBySomeTerm(rainFallCenter);
        rainfallsList = CodeIntegration.caleAvg(rainFalls,"getRainFallCenter","getValue","com.nanjing.weather.entitys.Rainfall","setValue");

        list = CodeIntegration.getValuePoint(rainfallsList, "getStationId", "getValue");
        if (time != null)
            return CodeIntegration.getResult("rainfalls", list, time.split(":")[1]);
        return CodeIntegration.getResult("rainfalls", list, TimeFormat.getTime(rainFalls.get(0).getRainFallCenter().get(0).getRoutineTime()));

    }

    @Override
    public ContourResult<Rainfall> findSomeByTerm(String parmOne, String parmTwo, String time) {
        List<ValuePoint> list;
        List<Rainfalls> rainfallsList;
        RainFallCenter rainFallCenter = new RainFallCenter();

        rainFallCenter.setValue(new BigDecimal(0));
        rainFallCenter.setRoutineTime("1");

        List<RainFall> rainFalls = rainfallsMapper.findAllBySomeTerm(rainFallCenter);
        if (rainFalls.size() > 0) {
            rainfallsList = CodeIntegration.caleAvg(rainFalls,"getRainFallCenter","getValue","com.nanjing.weather.entitys.Rainfall","setValue");
            list = CodeIntegration.getValuePoint(rainfallsList, "getStation_Id", "getValue");
            if (rainfallsList.size() > 0) {
                if (time != null) {
                    return CodeIntegration.getResult("rainfalls", list, time.split(":")[1]);
                } else {
                    return CodeIntegration.getResult("rainfalls", list, TimeFormat.getTime(rainFalls.get(0).getRainFallCenter().get(0).getRoutineTime()));
                }
            }
        }
        return null;
    }
}
