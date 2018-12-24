package com.nanjing.weather.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.ContourHelper;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.LegendLevel;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.LegendLevelMapper;
import com.nanjing.weather.dao.RainfallsMapper;
import com.nanjing.weather.dao.StationsMapper;
import com.nanjing.weather.domain.Rainfalls;
import com.nanjing.weather.domain.Stations;
import com.nanjing.weather.domain.Temperatures;
import com.nanjing.weather.domain.Winds;
import com.nanjing.weather.entity.RainFall;
import com.nanjing.weather.entity.RainFallCenter;
import com.nanjing.weather.service.RainfallsService;
import com.nanjing.weather.utils.CodeIntegration;
import com.nanjing.weather.utils.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RainfallsServiceImpl implements RainfallsService {

    @Autowired
    private RainfallsMapper rainfallsMapper;

    @Autowired
    private StationsMapper stationsMapper;

    @Autowired
    private LegendLevelMapper legendLevelMapper;


    @Override
    public List<Rainfalls> findAll() {
        return rainfallsMapper.findAll();
    }

    @Override
    public Rainfalls findRainfallsByid(String stationId) {
        return rainfallsMapper.findRainfallsByid(stationId);
    }

    @Override
    public void add(Rainfalls rainfalls) {

    }

    @Override
    public void update(Rainfalls rainfalls) {

    }

    @Override
    public void delete(String[] stationIds) {

    }

    @Override
    public PageInfo<Rainfalls> findAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Rainfalls> all = rainfallsMapper.findAll();
        PageInfo<Rainfalls> info = new PageInfo<Rainfalls>(all);
        return info;
    }

    @Override
    public ContourResult<Rainfalls> findAllByTerm(Integer time, String railfalls) {
        List<ValuePoint> list;
        List<Rainfalls> temperatures = rainfallsMapper.findAllByTerm(time,Double.parseDouble(railfalls));
        if(temperatures.size()>0){
            list = CodeIntegration.getValuePoint(temperatures,"getStation_Id","getValue");
            if(list.size()>0){
                return CodeIntegration.getResult("rainfalls",list,TimeFormat.getTime(temperatures.get(0).getRoutine_Time()));
            }
        }
        return null;
    }

    @Override
    public ContourResult<Rainfalls> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        List<ValuePoint> list;
        List<Rainfalls> rainfallsList = new ArrayList<>();
        RainFallCenter rainFallCenter = new RainFallCenter();
        rainFallCenter.setValue(new BigDecimal(parmOne.substring(1)));
        rainFallCenter.setCreateTime(time.split(":")[0]);
        rainFallCenter.setRoutineTime(time.split(":")[1]);
        List<RainFall> rainFalls = rainfallsMapper.findAllBySomeTerm(rainFallCenter);
        if(rainFalls.size()>0){
            for(RainFall rainFall:rainFalls){
                double x = 0;
                double y = 0;
                for(RainFallCenter center:rainFall.getRainFallCenter()){
                    if(Double.parseDouble(center.getValue().toString())<999){
                        x += Double.parseDouble(center.getValue().toString());
                        y++;
                    }
                }

                if(y != 0){
                    Rainfalls rain = new Rainfalls();
                    rain.setStation_Id(rainFall.getStationId());
                    rain.setValue(new BigDecimal(new DecimalFormat("#.00").format(x/y)));
                    rainfallsList.add(rain);
                }

            }
            list = CodeIntegration.getValuePoint(rainfallsList,"getStation_Id","getValue");
            if(rainfallsList.size()>0){
                return CodeIntegration.getResult("rainfalls",list,time.split(":")[1]);
            }
        }
        return null;
    }
}
