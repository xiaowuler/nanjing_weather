package com.nanjing.weather.service.impl;

import com.nanjing.weather.dao.DataArrivalsMapper;
import com.nanjing.weather.domain.*;
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
    public PageResult<DataArrivals> findAllType(Integer pageNum, Integer pageSize, String type) {
        Map map = new HashMap();
        map.put("type", type);
        map.put("begin", (pageNum - 1) * pageSize);
        map.put("pageSize", pageSize);
        PageResult<DataArrivals> dataArrivalsPageResult = new PageResult<>(dataArrivalsMapper.findCount(type), dataArrivalsMapper.findAllType(map));
        return dataArrivalsPageResult;
    }

    //根据时间段查询
    @Override
    public PageResult<DataArrivals> findAllType1(String startTime, String endTime, Integer pageNum, Integer pageSize, String type) {
        Map map = new HashMap();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("type", type);
        map.put("begin", (pageNum - 1) * pageSize);
        map.put("pageSize", pageSize);
        Long count1 = dataArrivalsMapper.findCount1(map);
        List<DataArrivals> byTypeAndTime = dataArrivalsMapper.findAllTypeAndTime(map);
        return new PageResult<>(count1, byTypeAndTime);
    }

    @Override
    public PageResult<DataArrivals> findByType(Integer pageNum, Integer pageSize, String type,String regionCode) {
        Map map = new HashMap();
        map.put("type", type);
        map.put("begin", (pageNum - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("regionCode",regionCode);
        PageResult<DataArrivals> dataArrivalsPageResult = new PageResult<>(dataArrivalsMapper.findCount(type), dataArrivalsMapper.findByType(map));
        return dataArrivalsPageResult;
    }

    //根据时间段查询
    @Override
    public PageResult<DataArrivals> findByType1(String startTime, String endTime, Integer pageNum, Integer pageSize, String type,String regionCode) {
        Map map = new HashMap();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("type", type);
        map.put("begin", (pageNum - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("regionCode",regionCode);
        Long count1 = dataArrivalsMapper.findCount1(map);
        List<DataArrivals> byTypeAndTime = dataArrivalsMapper.findByTypeAndTime(map);
        return new PageResult<>(count1, byTypeAndTime);
    }

    @Override
    public PageResult<DataArrivals> findByTiming(Integer pageNum, Integer pageSize, String type,String regionCode) {
        Map map = new HashMap();
        map.put("type", type);
        map.put("begin", (pageNum - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("regionCode",regionCode);
        return new PageResult<>(dataArrivalsMapper.findCount(type), dataArrivalsMapper.findByTiming(map));
    }

    @Override
    public PageResult<DataArrivals> findByHalfTime(Integer pageNum, Integer pageSize, String type,String regionCode) {
        Map map = new HashMap();
        map.put("type", type);
        map.put("begin", (pageNum - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("regionCode",regionCode);
        PageResult<DataArrivals> dataArrivalsPageResult = new PageResult<DataArrivals>(dataArrivalsMapper.findCount(type), dataArrivalsMapper.findByHalfTime(map));

        return dataArrivalsPageResult;
    }

    @Override
    public List<List<DataState>> findState() {
        /*List<List<DataState>> list = new ArrayList<>();
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
                DataState dataState = addData(name);
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
                DataState dataState = addData(name);
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

        return list;*/
        return null;
    }

    @Override
    public List<List<DataState>> findDataState() {
        List<List<DataState>> list = new ArrayList<>();
        List<DataArrival> maxList = dataArrivalsMapper.findMaxTime();
        List<DataState> routineData = new ArrayList<>();
        List<DataState> notRoutineData = new ArrayList<>();
        List<DataState> windData = new ArrayList<>();
        for(DataArrival dataArrival:maxList){
            if(dataArrival.getProductTypeCode().equals("humidity")){
                routineData = dataArrivalsMapper.findState(dataArrival.getRoutineTime().toString());
                routineData = getHandleTime(routineData);
            }

            if(dataArrival.getProductTypeCode().equals("2d-tu")){
                notRoutineData = dataArrivalsMapper.findNotRoutine(dataArrival.getRoutineTime().toString());
                notRoutineData = getHandleTime(notRoutineData);
            }

            if(dataArrival.getProductTypeCode().equals("6-fen-zhong")){
                windData = dataArrivalsMapper.findWindData(dataArrival.getRoutineTime().toString());
                windData = getHandleTime(windData);
            }
        }

        if (routineData.size() < 6) {
            List<String> routineString = new ArrayList<>();
            routineString.add("湿度");
            routineString.add("温度");
            routineString.add("气压");
            routineString.add("降雨");
            routineString.add("风");
            routineString.add("地温");
            List<String> strings = getFlag(routineString, routineData);
            for (String name : strings) {
                DataState dataState = addData(name);
                routineData.add(dataState);
            }
        }

        if (notRoutineData.size() < 4) {
            List<String> notRoutineString = new ArrayList<>();
            notRoutineString.add("雨滴谱");
            notRoutineString.add("激光雷达");
            notRoutineString.add("微波辐射");
            notRoutineString.add("GPS/MET");
            List<String> strings = getFlag(notRoutineString, notRoutineData);
            for (String name : strings) {
                DataState dataState = addData(name);
                notRoutineData.add(dataState);
            }
        }

        for(DataState dataState:routineData){
            for(DataArrivalLitter dataArrivalLitter:dataState.getDataArrivalLitter()){
                for(CenterDataArrival centerDataArrival:dataArrivalLitter.getCenterDataArrival()){
                    String num = centerDataArrival.getDescription().replaceAll("[^0-9\\-]", "");
                    centerDataArrival.setDescription(num);
                }
            }
        }

        list.add(routineData);
        notRoutineData = getUpArea(notRoutineData);
        list.add(setAreaName(notRoutineData));
        windData = getUpArea(windData);
        list.add(setAreaName(windData));
        return list;
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

    //处理地区
    private List<DataState> setAreaName(List<DataState> dataStates){

        List<String> strings = new ArrayList<>();
        strings.add("JN");
        strings.add("GC");
        strings.add("LS");
        strings.add("LH");
        strings.add("PK");

        for (String str : strings) {
            for(DataState dataState:dataStates){
                for(DataArrivalLitter dataArrivalLitter:dataState.getDataArrivalLitter()){
                    if(dataArrivalLitter.getCenterDataArrival().size() < 5){
                        boolean flag = false;
                        for(CenterDataArrival centerDataArrival:dataArrivalLitter.getCenterDataArrival()){
                            if(centerDataArrival.getProductRegionCode().equals(str)){
                                flag = true;
                                break;
                            }
                        }
                        if(flag == false){
                            CenterDataArrival centerDataArrival = new CenterDataArrival();
                            centerDataArrival.setProductRegionCode(str);
                            centerDataArrival.setTimeliessCode("lost");
                            dataArrivalLitter.getCenterDataArrival().add(centerDataArrival);
                        }
                    }
                }
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
                        if(centerDataArrival.getProductRegionCode().contains("-")){
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
    private DataState addData(String name) {
        DataState dataState = new DataState();
        dataState.setProductCategoryCode(name);
        dataState.setProductTypeCode(name);
        List<DataArrivalLitter> litterList = new ArrayList<>();
        DataArrivalLitter dataArrivalLitter = new DataArrivalLitter();
        List<CenterDataArrival> centerDataArrivals = new ArrayList<>();
        CenterDataArrival centerDataArrival = new CenterDataArrival();
        if (name.equals("GPS/MET")) {
            centerDataArrival.setTimeliessCode("lost");
        } else {
            centerDataArrival.setTimeliessCode("missing");
        }
        centerDataArrival.setProductRegionCode("jiang"+"-"+"ning");
        centerDataArrival.setDescription("-1");
        centerDataArrivals.add(centerDataArrival);
        dataArrivalLitter.setCenterDataArrival(centerDataArrivals);
        for (int i = 0; i < 12; i++) {
            litterList.add(dataArrivalLitter);
        }
        dataState.setDataArrivalLitter(litterList);
        return dataState;
    }
}
