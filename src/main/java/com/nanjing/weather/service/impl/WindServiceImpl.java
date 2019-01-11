package com.nanjing.weather.service.impl;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.StationsMapper;
import com.nanjing.weather.dao.WindsMapper;
import com.nanjing.weather.domain.Stations;
import com.nanjing.weather.domain.Winds;
import com.nanjing.weather.entity.Wind;
import com.nanjing.weather.entity.WindCenter;
import com.nanjing.weather.service.WindService;
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
public class WindServiceImpl implements WindService {
    @Autowired
    private WindsMapper WindsMapper;

    @Autowired
    private StationsMapper stationsMapper;


    @Override
    public ContourResult<Winds> findAllBySomeTerm(String parmsOne, String parmsTwo, String time) {
        List<ValuePoint> list = new ArrayList<>();
        List<Wind> winds;
        List<Winds> windsList = new ArrayList<>();

        //判断前端查询条件
        WindCenter windCenter = new WindCenter();
        if (time != null) {
            windCenter.setCreateTime(time.split(":")[0]);
            windCenter.setRoutineTime(time.split(":")[1]);
        }
        winds = WindsMapper.findAllBySomeTerm(windCenter);
        if (winds.size() > 0) {
            for (Wind wind : winds) {
                double x = 0;
                double z = 0;
                double y = 0;
                for (WindCenter windCenters : wind.getWindCenter()) {
                    if (parmsOne.equals("平均风")) {
                        if (Double.parseDouble(windCenters.getSpeed().toString()) < 9999 && Double.parseDouble(windCenters.getDirection().toString()) < 9999) {
                            x += Double.parseDouble(windCenters.getSpeed().toString());
                            z += Double.parseDouble(windCenters.getDirection().toString());
                            y++;
                        }
                    } else if (parmsOne.equals("2分钟风向风速")) {
                        if (Double.parseDouble(windCenters.getSpeedTwoMin().toString()) < 9999 && Double.parseDouble(windCenters.getDirectionTwoMin().toString()) < 9999) {
                            x += Double.parseDouble(windCenters.getSpeedTwoMin().toString());
                            z += Double.parseDouble(windCenters.getDirectionTwoMin().toString());
                            y++;
                        }
                    } else {
                        if (Double.parseDouble(windCenters.getSpeedTenMin().toString()) < 9999 && Double.parseDouble(windCenters.getDirectionTenMin().toString()) < 9999) {
                            x += Double.parseDouble(windCenters.getSpeedTenMin().toString());
                            z += Double.parseDouble(windCenters.getDirectionTenMin().toString());
                            y++;
                        }
                    }
                }
                Winds windOne = new Winds();
                if (y != 0) {
                    windOne.setAvg_Speed(new BigDecimal(new DecimalFormat("#.00").format(x / y)));
                    windOne.setAvg_Speed_Direction(new BigDecimal(new DecimalFormat("#.00").format(z / y)));
                    //windOne.setAvg_Speed(new BigDecimal(x/y));
                    //windOne.setAvg_Speed_Direction(new BigDecimal(z/y));
                    windOne.setStation_Id(wind.getStationId());
                    windsList.add(windOne);
                }
            }
            //System.out.println(x+"\t"+y+"\t"+z);
        }

        //判断查询数据是否可用
        if (windsList.size() > 0) {
            //list = CodeIntegration.getValuePoint(winds,"getStation_Id","getAvg_Speed");
            for (Winds wind : windsList) {
                if (Double.parseDouble(wind.getAvg_Speed().toString()) < 999 && Double.parseDouble(wind.getAvg_Speed_Direction().toString()) < 999) {
                    Stations stations = stationsMapper.findStationsByid(wind.getStation_Id());
                    ValuePoint valuePoint = new ValuePoint();
                    valuePoint.setName(stations.getName());
                    valuePoint.setId(stations.getId());
                    valuePoint.setLatitude(Double.parseDouble(stations.getLatitude().toString()));
                    valuePoint.setLongitude(Double.parseDouble(stations.getLongitude().toString()));
                    valuePoint.setValue(Double.parseDouble(wind.getAvg_Speed().toString()));
                    valuePoint.setInstantDirection(Double.parseDouble(wind.getAvg_Speed_Direction().toString()));
                    list.add(valuePoint);
                }
            }

            if (time != null)
                return CodeIntegration.getResult("winds", list, time.split(":")[1]);
            return CodeIntegration.getResult("winds", list, TimeFormat.getTime(winds.get(0).getWindCenter().get(0).getRoutineTime()));
        }
        return null;
    }
}
