package com.nanjing.weather.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.ContourHelper;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.LegendLevel;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.LegendLevelMapper;
import com.nanjing.weather.dao.PressuresMapper;
import com.nanjing.weather.dao.StationsMapper;
import com.nanjing.weather.domain.Pressures;
import com.nanjing.weather.domain.Stations;
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
    public ContourResult<Pressures> findAllByTerm(String parms) {
        List<ValuePoint> list = new ArrayList<>();
        List<Pressures> allByTerm = pressuresMapper.findAllByTerm();

        if(allByTerm.size()>0){
            for(Pressures pressures:allByTerm){
                if(Double.parseDouble(pressures.getValue().toString())<10000){
                    Stations station = stationsMapper.findStationsByid(pressures.getStation_Id());
                    ValuePoint valuePoint = new ValuePoint();
                    if(parms.equals("本站气压")){
                        valuePoint.setValue(Double.parseDouble(pressures.getValue().toString()));
                    }else if(parms.equals("24小时变压")){
                        //24小时前的值
                        BigDecimal decimal = pressuresMapper.findAllByAvg(pressures.getRoutine_Time().toString(), pressures.getStation_Id()).getValue();
                        valuePoint.setValue(Double.parseDouble(pressures.getValue().toString())-Double.parseDouble((decimal.toString())));
                    }
                    valuePoint.setLatitude(Double.parseDouble(station.getLatitude() + ""));
                    valuePoint.setLongitude(Double.parseDouble(station.getLongitude() + ""));
                    valuePoint.setName(station.getName());
                    list.add(valuePoint);
                }
            }

            if(list.size()>0){
                /*List<LegendLevel> levels = legendLevelMapper.findAll("winds");
                ContourHelper contourHelper = new ContourHelper("D:\\project\\springboot-nanjing\\springboot-nanjing\\src\\main\\resources\\static\\json\\nanjing.json");
                ContourResult contourResult = contourHelper.Calc(list, levels, 8, -9999);
                contourResult.setTime(TimeFormat.getTime(allByTerm.get(0).getRoutine_Time()));
                return contourResult;*/
                return CodeIntegration.getResult("pressures",list,TimeFormat.getTime(allByTerm.get(0).getRoutine_Time()));
            }
        }
        return null;
    }

    @Override
    public ContourResult<Pressures> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        List<ValuePoint> list;
        List<Pressures> pressuresList = new ArrayList<>();
        PressureCenter pressureCenter = new PressureCenter();
        pressureCenter.setCreateTime(time.split(":")[0]);
        pressureCenter.setRoutineTime(time.split(":")[1]);
        List<Pressure> allBySomeMap = pressuresMapper.findAllBySomeTerm(pressureCenter);
        for(Pressure pressure:allBySomeMap){
            double x = 0;
            double y = 0;
            for(PressureCenter center:pressure.getPressureCenter()){
                if(Double.parseDouble(center.getValue().toString())<9999){
                    x += Double.parseDouble(center.getValue().toString());
                    y++;
                }
            }
            if(y != 0){
                Pressures pre = new Pressures();
                pre.setStation_Id(pressure.getStationId());
                pre.setValue(new BigDecimal(new DecimalFormat("#.00").format(x/y)));
                pressuresList.add(pre);
            }
        }

        if(pressuresList.size()>0){
            list = CodeIntegration.getValuePoint(pressuresList,"getStation_Id","getValue");
            if(list.size()>0){
                return CodeIntegration.getResult("pressures",list,time.split(":")[1]);
            }
        }
        return null;
    }
}
