package com.nanjing.weather.service.impl;

import com.nanjing.weather.dao.DataArrivalsMapper;
import com.nanjing.weather.domain.CenterDataArrival;
import com.nanjing.weather.domain.DataArrivalLitter;
import com.nanjing.weather.domain.DataArrivals;
import com.nanjing.weather.domain.DataState;
import com.nanjing.weather.entity.DataArrival;
import com.nanjing.weather.service.DataArrivalsService;
import com.nanjing.weather.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class DataArrivalsServiceImpl implements DataArrivalsService {
    @Autowired
    private DataArrivalsMapper dataArrivalsMapper;

    @Override
    public PageResult<DataArrivals> findByType(Integer pageNum, Integer pageSize, String type) {
        Map map = new HashMap();
        map.put("type", type);
        map.put("begin", (pageNum - 1) * pageSize);
        map.put("pageSize", pageSize);
        return new PageResult<>(dataArrivalsMapper.findCount(type), dataArrivalsMapper.findByType(map));
    }

    //根据时间段查询
    @Override
    public PageResult<DataArrivals> findByType1(String startTime, String endTime, Integer pageNum, Integer pageSize, String type) {
        Map map = new HashMap();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("type", type);
        map.put("begin", (pageNum - 1) * pageSize);
        map.put("pageSize", pageSize);

        Long count1 = dataArrivalsMapper.findCount1(map);
        List<DataArrivals> byTypeAndTime = dataArrivalsMapper.findByTypeAndTime(map);
        return new PageResult<>(count1, byTypeAndTime);
    }

    @Override
    public List<List<DataState>> findState() {
        DataState ds = new DataState();
        List<List<DataState>> list = new ArrayList<>();
        List<DataState> routine = null;
        List<DataState> windData = null;

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String maxTime = dateFormat.format(date);

        //根据当前时间获取数据
        List<DataState> dataStates = dataArrivalsMapper.findState(maxTime);
        int size = getSize(dataStates);
        if(size <= 5){
            List<DataArrival> maxList = dataArrivalsMapper.findMaxTime();
            for(DataArrival dataArrival:maxList){
               if(dataArrival.getProductTypeCode().equals("humidity")){
                   dataStates = dataArrivalsMapper.findState(dataArrival.getRoutineTime().toString());
                   dataStates = getHandleTime(dataStates);
               }

               if(dataArrival.getProductTypeCode().equals("2d-tu")){
                   List<DataState> notRoutine = dataArrivalsMapper.findNotRoutine(dataArrival.getRoutineTime().toString());
                   routine = getHandleTime(notRoutine);
               }

               if(dataArrival.getProductTypeCode().equals("6-fen-zhong")){
                   windData = dataArrivalsMapper.findWindData(dataArrival.getRoutineTime().toString());
                   windData = getHandleTime(windData);
               }
            }
        }

        dataStates = getHandleTime(dataStates);

        if (dataStates.size() < 6) {
            List<String> notRoutineData = new ArrayList<>();
            notRoutineData.add("湿度");
            notRoutineData.add("温度");
            notRoutineData.add("气压");
            notRoutineData.add("降雨");
            notRoutineData.add("风");
            notRoutineData.add("地温");
            List<String> strings = getFlag(notRoutineData, dataStates);
            for (String name : strings) {
                DataState dataState = addData(ds, name);
                dataStates.add(dataState);
            }
        }

        if(routine == null){
            routine = dataArrivalsMapper.findRoutineData(maxTime);
            routine = getHandleTime(routine);
            size = getSize(routine);
            if(size < 15){
                List<DataArrival> maxList = dataArrivalsMapper.findMaxTime();
                for(DataArrival dataArrival:maxList){

                    if(dataArrival.getProductTypeCode().equals("2d-tu")){
                        List<DataState> notRoutine = dataArrivalsMapper.findNotRoutine(dataArrival.getRoutineTime().toString());
                        routine = getHandleTime(notRoutine);
                    }

                    if(dataArrival.getProductTypeCode().equals("6-fen-zhong")){
                        windData = dataArrivalsMapper.findWindData(dataArrival.getRoutineTime().toString());
                        windData = getHandleTime(windData);
                    }
                }
            }
        }

        //定义一个flag
        boolean flag = false;

        if (routine.size() < 5) {
            flag = true;
            List<String> notRoutineData = new ArrayList<>();
            notRoutineData.add("雨滴谱");
            notRoutineData.add("激光雷达");
            notRoutineData.add("微波辐射");
            notRoutineData.add("GPS/MET");
            notRoutineData.add("风廓线");
            List<String> strings = getFlag(notRoutineData, routine);
            for (String name : strings) {
                DataState dataState = addData(ds, name);
                routine.add(dataState);
            }
        }

        if(!flag){
            windData = dataArrivalsMapper.findWindData(maxTime);
            windData = getHandleTime(windData);
        }

        for(DataState dataState:dataStates){
            for(DataArrivalLitter dataArrivalLitter:dataState.getDataArrivalLitter()){
                for(CenterDataArrival centerDataArrival:dataArrivalLitter.getCenterDataArrival()){
                    String num = centerDataArrival.getDescription().replaceAll("[^0-9\\-]", "");
                    centerDataArrival.setDescription(num);
                }
            }
        }

        list.add(dataStates);
        routine = getUpArea(routine);
        list.add(routine);
        if(windData != null){
            windData = getUpArea(windData);
            list.add(windData);
        }

        return list;
    }

    @Override
    public PageResult<DataArrivals> findByTiming(Integer pageNum, Integer pageSize, String type) {
        Map map = new HashMap();
        map.put("type", type);
        map.put("begin", (pageNum - 1) * pageSize);
        map.put("pageSize", pageSize);
        return new PageResult<>(dataArrivalsMapper.findCount(type), dataArrivalsMapper.findByTiming(map));
    }

    @Override
    public PageResult<DataArrivals> findByHalfTime(Integer pageNum, Integer pageSize, String type) {
        Map map = new HashMap();
        map.put("type", type);
        map.put("begin", (pageNum - 1) * pageSize);
        map.put("pageSize", pageSize);
        PageResult<DataArrivals> dataArrivalsPageResult = new PageResult<DataArrivals>(dataArrivalsMapper.findCount(type), dataArrivalsMapper.findByHalfTime(map));

        return dataArrivalsPageResult;
    }

    private List<DataState> getHandleTime(List<DataState> dataStates){
        for (DataState dataState : dataStates) {
            for (DataArrivalLitter dataArrivalLitter : dataState.getDataArrivalLitter()) {
                String time = proFormat(dataArrivalLitter.getRoutineTime());
                dataArrivalLitter.setTime(time);
            }
        }
        return dataStates;
    }

    //处理地区首字母
    private List<DataState> getUpArea(List<DataState> dataStates){
        for(DataState dataState:dataStates){
            if(dataState.getDataArrivalLitter().get(0).getCenterDataArrival().get(0).getProductRegionCode() != null){
                for(DataArrivalLitter dataArrivalLitter:dataState.getDataArrivalLitter()){
                    for(CenterDataArrival centerDataArrival:dataArrivalLitter.getCenterDataArrival()){
                        String[] string = centerDataArrival.getProductRegionCode().split("-");
                        String area = "";
                        for(String str :string){
                            area += str.substring(0,1).toUpperCase();
                        }
                        centerDataArrival.setProductRegionCode(area);
                    }
                }
            }
        }
        return dataStates;
    }

    //判断使用当前时间获取的数据是否可用
    private int getSize(List<DataState> dataStates){
        int size = 100 ;
        for(DataState dataState:dataStates){
            if(dataState.getDataArrivalLitter().size() < size){
                size = dataState.getDataArrivalLitter().size();
            }
        }
        return size;
    }

    //处理时间格式
    private String proFormat(Timestamp time) {
        String changTime = time.toString().split(" ")[1].split(":")[0] + ":" + time.toString().split(" ")[1].split(":")[1];
        return changTime;
    }

    //处理数据缺失
    private List<String> getFlag(List<String> list, List<DataState> dataStates) {
        List<String> strings = new ArrayList<>();
        for (String str : list) {
            boolean flag = false;
            for (DataState dataState : dataStates) {
                if (dataState.getProductTypeCode().equals(str)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                strings.add(str);
            }
        }
        return strings;
    }

    //添加数据
    private DataState addData(DataState ds, String name) {
        DataState dataState = ds;
        dataState.setProductCategoryCode(name);
        dataState.setProductTypeCode(name);
        List<DataArrivalLitter> litterList = new ArrayList<>();
        DataArrivalLitter dataArrivalLitter = new DataArrivalLitter();
        List<CenterDataArrival> centerDataArrivals = new ArrayList<>();
        CenterDataArrival centerDataArrival = new CenterDataArrival();
        for (int i = 0; i < 12; i++) {
            if (name.equals("GPS/MET")) {
                centerDataArrival.setTimeliessCode("lost");
            } else {
                centerDataArrival.setTimeliessCode("missing");
            }
            centerDataArrivals.add(centerDataArrival);
            dataArrivalLitter.setCenterDataArrival(centerDataArrivals);
            litterList.add(dataArrivalLitter);
        }
        dataState.setDataArrivalLitter(litterList);
        return dataState;
    }
}
