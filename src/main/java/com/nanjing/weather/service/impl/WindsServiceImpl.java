package com.nanjing.weather.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.ContourHelper;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.LegendLevel;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.LegendLevelMapper;
import com.nanjing.weather.dao.StationsMapper;
import com.nanjing.weather.dao.WindsMapper;
import com.nanjing.weather.domain.Stations;
import com.nanjing.weather.domain.Winds;
import com.nanjing.weather.entity.Wind;
import com.nanjing.weather.entity.WindCenter;
import com.nanjing.weather.service.WindsService;
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
public class WindsServiceImpl implements WindsService {
    @Autowired
    private WindsMapper WindsMapper;

    @Autowired
    private StationsMapper stationsMapper;

    @Autowired
    private LegendLevelMapper legendLevelMapper;

    @Override
    public List<Winds> findAll() {
        return WindsMapper.findAll();
    }

    @Override
    public Winds findWindsByid(String stationId) {
        return WindsMapper.findWindsByid(stationId);
    }

    @Override
    public void add(Winds winds) {

    }

    @Override
    public void update(Winds inds) {

    }

    @Override
    public void delete(String[] stationIds) {

    }

    @Override
    public PageInfo<Winds> findAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Winds> all = WindsMapper.findAll();
        PageInfo<Winds> info = new PageInfo<Winds>(all);
        return info;
    }

    @Override
    public ContourResult<Winds> findAllByTerm(String parms1, String parms2) {
        List<ValuePoint> list = new ArrayList<>();
        List<Winds> winds ;
        Integer num = Integer.parseInt(parms2.substring(1));

        //判断前端查询条件
        if(parms1.equals("平均风")){
            winds = WindsMapper.findAllByZTerm(num);
        }else if(parms1.equals("2分钟风向风速")){
            winds = WindsMapper.findAllByTTerm(num);
        }else{
            winds = WindsMapper.findAllByTeTerm(num);
        }

        //判断查询数据是否可用
        if(winds.size()>0){
            //list = CodeIntegration.getValuePoint(winds,"getStation_Id","getAvg_Speed");
            for(Winds wind:winds){
                if(Double.parseDouble(wind.getAvg_Speed().toString())<999 && Double.parseDouble(wind.getAvg_Speed_Direction().toString())<999){
                    Stations stations = stationsMapper.findStationsByid(wind.getStation_Id());
                    ValuePoint valuePoint = new ValuePoint();
                    valuePoint.setName(stations.getName());
                    valuePoint.setLatitude(Double.parseDouble(stations.getLatitude().toString()));
                    valuePoint.setLongitude(Double.parseDouble(stations.getLongitude().toString()));
                    valuePoint.setValue(Double.parseDouble(wind.getAvg_Speed().toString()));
                    valuePoint.setId(stations.getId());
                    valuePoint.setInstantDirection(Double.parseDouble(wind.getAvg_Speed_Direction().toString()));
                    list.add(valuePoint);
                }

            }
            if(list.size()>0){
                ContourResult winds1 = CodeIntegration.getResult("winds", list, TimeFormat.getTime(winds.get(0).getRoutine_Time()));
                return winds1;
            }
        }
        return null;
    }

    @Override
    public ContourResult<Winds> findAllBySomeTerm(String parmsOne, String parmsTwo, String time) {
        List<ValuePoint> list = new ArrayList<>();
        List<Wind> winds = new ArrayList<>();
        List<Winds> windsList = new ArrayList<>();
        Integer num = Integer.parseInt(parmsTwo.substring(1));
        //判断前端查询条件
        /*if(parmsOne.equals("平均风")){
            WindCenter windCenter = new WindCenter();
            windCenter.setSpeed(new BigDecimal(num));
            windCenter.setCreateTime(time.split(":")[0]);
            windCenter.setRoutineTime(time.split(":")[1]);
            winds =WindsMapper.findAllBySomeTerm(windCenter);
            if(winds.size() > 0){
                for(Wind wind:winds){
                    double x = 0;
                    double z = 0;
                    double y = 0;
                    for(WindCenter windCenters:wind.getWindCenter()){
                        if(Double.parseDouble(windCenters.getSpeed().toString())<9999 && Double.parseDouble(windCenters.getDirection().toString())<9999){
                            x += Double.parseDouble(windCenters.getSpeed().toString());
                            z += Double.parseDouble(windCenters.getDirection().toString());
                            y++;
                        }
                    }
                    //System.out.println(x+"\t"+y+"\t"+z);
                    Winds windOne = new Winds();
                    if(y != 0){
                        windOne.setAvg_Speed(new BigDecimal(new DecimalFormat("#.00").format(x/y)));
                        windOne.setAvg_Speed_Direction(new BigDecimal(new DecimalFormat("#.00").format(z/y)));
                        //windOne.setAvg_Speed(new BigDecimal(x/y));
                        //windOne.setAvg_Speed_Direction(new BigDecimal(z/y));
                        windOne.setStation_Id(wind.getStationId());
                        windsList.add(windOne);
                    }
                }
            }
        }else if(parmsOne.equals("2分钟风向风速")){
            WindCenter windCenter = new WindCenter();
            windCenter.setSpeedTwoMin(new BigDecimal(num));
            windCenter.setCreateTime(time.split(":")[0]);
            windCenter.setRoutineTime(time.split(":")[1]);
            winds =WindsMapper.findAllBySomeTerm(windCenter);
            if(winds.size() > 0){
                for(Wind wind:winds){
                    double x = 0;
                    double z = 0;
                    double y = 0;
                    for(WindCenter windCenters:wind.getWindCenter()){
                        if(Double.parseDouble(windCenters.getSpeedTwoMin().toString())<9999 && Double.parseDouble(windCenters.getDirectionTwoMin().toString())<9999){
                            x += Double.parseDouble(windCenters.getSpeedTwoMin().toString());
                            z += Double.parseDouble(windCenters.getDirectionTwoMin().toString());
                            y++;
                        }
                    }
                    //System.out.println(x+"\t"+y+"\t"+z);
                    Winds windOne = new Winds();
                    if(y != 0){
                        windOne.setAvg_Speed(new BigDecimal(new DecimalFormat("#.00").format(x/y)));
                        windOne.setAvg_Speed_Direction(new BigDecimal(new DecimalFormat("#.00").format(z/y)));
                        windOne.setStation_Id(wind.getStationId());
                        windsList.add(windOne);
                    }
                }
            }
        }else{
            WindCenter windCenter = new WindCenter();
            windCenter.setSpeedTenMin(new BigDecimal(num));
            windCenter.setCreateTime(time.split(":")[0]);
            windCenter.setRoutineTime(time.split(":")[1]);
            winds =WindsMapper.findAllBySomeTerm(windCenter);
            if(winds.size() > 0){
                for(Wind wind:winds){
                    double x = 0;
                    double z = 0;
                    double y = 0;
                    for(WindCenter windCenters:wind.getWindCenter()){
                        if(Double.parseDouble(windCenters.getSpeedTenMin().toString())<9999 && Double.parseDouble(windCenters.getDirectionTenMin().toString())<9999){
                            x += Double.parseDouble(windCenters.getSpeedTenMin().toString());
                            z += Double.parseDouble(windCenters.getDirectionTenMin().toString());
                            y++;
                        }
                    }
                    //System.out.println(x+"\t"+y+"\t"+z);
                    Winds windOne = new Winds();
                    if(y != 0){
                        windOne.setAvg_Speed(new BigDecimal(new DecimalFormat("#.00").format(x/y)));
                        windOne.setAvg_Speed_Direction(new BigDecimal(new DecimalFormat("#.00").format(z/y)));
                        //windOne.setAvg_Speed(new BigDecimal(x/y));
                        //windOne.setAvg_Speed_Direction(new BigDecimal(z/y));
                        windOne.setStation_Id(wind.getStationId());
                        windsList.add(windOne);
                    }
                }
            }
        }*/

        WindCenter windCenter = new WindCenter();
        windCenter.setSpeed(new BigDecimal(num));
        windCenter.setCreateTime(time.split(":")[0]);
        windCenter.setRoutineTime(time.split(":")[1]);
        winds =WindsMapper.findAllBySomeTerm(windCenter);
        if(winds.size() > 0){
            for(Wind wind:winds){
                double x = 0;
                double z = 0;
                double y = 0;
                for(WindCenter windCenters:wind.getWindCenter()){
                    if(parmsOne.equals("平均风")){
                        if(Double.parseDouble(windCenters.getSpeed().toString())<9999 && Double.parseDouble(windCenters.getDirection().toString())<9999){
                            x += Double.parseDouble(windCenters.getSpeed().toString());
                            z += Double.parseDouble(windCenters.getDirection().toString());
                            y++;
                        }
                    }else if(parmsOne.equals("2分钟风向风速")){
                        if(Double.parseDouble(windCenters.getSpeedTwoMin().toString())<9999 && Double.parseDouble(windCenters.getDirectionTwoMin().toString())<9999){
                            x += Double.parseDouble(windCenters.getSpeedTwoMin().toString());
                            z += Double.parseDouble(windCenters.getDirectionTwoMin().toString());
                            y++;
                        }
                    }else{
                        if(Double.parseDouble(windCenters.getSpeedTenMin().toString())<9999 && Double.parseDouble(windCenters.getDirectionTenMin().toString())<9999){
                            x += Double.parseDouble(windCenters.getSpeedTenMin().toString());
                            z += Double.parseDouble(windCenters.getDirectionTenMin().toString());
                            y++;
                        }
                    }
                }
                Winds windOne = new Winds();
                if(y != 0){
                    windOne.setAvg_Speed(new BigDecimal(new DecimalFormat("#.00").format(x/y)));
                    windOne.setAvg_Speed_Direction(new BigDecimal(new DecimalFormat("#.00").format(z/y)));
                    //windOne.setAvg_Speed(new BigDecimal(x/y));
                    //windOne.setAvg_Speed_Direction(new BigDecimal(z/y));
                    windOne.setStation_Id(wind.getStationId());
                    windsList.add(windOne);
                }
            }
            //System.out.println(x+"\t"+y+"\t"+z);
        }

        //判断查询数据是否可用
        if(windsList.size()>0){
            //list = CodeIntegration.getValuePoint(winds,"getStation_Id","getAvg_Speed");
            for(Winds wind:windsList){
                if(Double.parseDouble(wind.getAvg_Speed().toString())<999 && Double.parseDouble(wind.getAvg_Speed_Direction().toString())<999){
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
            if(list.size()>0){
                ContourResult winds1 = CodeIntegration.getResult("winds", list, time.split(":")[1]);
                return winds1;
            }
        }

        return null;
    }
}
