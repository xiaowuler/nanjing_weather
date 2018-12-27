package com.nanjing.weather.service.impl;

import com.nanjing.weather.dao.DataArrivalsMapper;
import com.nanjing.weather.domain.CenterDataArrival;
import com.nanjing.weather.domain.DataArrivalLitter;
import com.nanjing.weather.domain.DataArrivals;
import com.nanjing.weather.domain.DataState;
import com.nanjing.weather.service.DataArrivalsService;
import com.nanjing.weather.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //long startTime=System.currentTimeMillis();
        List<DataState> dataStates = dataArrivalsMapper.findState();
        //long endTime = System.currentTimeMillis();
        //System.out.println("持久层运行时间="+(endTime-startTime));

        //long start = System.currentTimeMillis();
        for (DataState dataState : dataStates) {
            for (DataArrivalLitter dataArrivalLitter : dataState.getDataArrivalLitter()) {
                String time = proFormat(dataArrivalLitter.getRoutineTime());
                dataArrivalLitter.setTime(time);
            }
        }

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
        //Long end = System.currentTimeMillis();
        //System.out.println("数据处理层运行时间"+(end-start));
        //System.out.println("------------");

        //startTime = System.currentTimeMillis();
        List<DataState> routine = dataArrivalsMapper.findRoutine();
        //endTime = System.currentTimeMillis();
        //System.out.println("持久层运行时间="+(endTime-startTime));

        //start = System.currentTimeMillis();
        for (DataState dataState : routine) {
            for (DataArrivalLitter dataArrivalLitter : dataState.getDataArrivalLitter()) {
                String time = proFormat(dataArrivalLitter.getRoutineTime());
                dataArrivalLitter.setTime(time);
            }
        }


        if (routine.size() < 5) {
            List<String> notRoutineData = new ArrayList<>();
            notRoutineData.add("雨滴谱");
            notRoutineData.add("激光雷达");
            notRoutineData.add("微波辐射");
            notRoutineData.add("GPS/MET");
            List<String> strings = getFlag(notRoutineData, routine);
            for (String name : strings) {
                DataState dataState = addData(ds, name);
                routine.add(dataState);
            }
        }
        /*end = System.currentTimeMillis();
        System.out.println("数据处理层运行时间"+(end-start));
        System.out.println("------------");

        startTime = System.currentTimeMillis();*/
        List<DataState> windData = dataArrivalsMapper.findWindData();
        /*endTime = System.currentTimeMillis();
        System.out.println("持久层运行时间="+(endTime-startTime));*/

//        start = System.currentTimeMillis();
        for (DataState dataState : windData) {
            for (DataArrivalLitter dataArrivalLitter : dataState.getDataArrivalLitter()) {
                String time = proFormat(dataArrivalLitter.getRoutineTime());
                dataArrivalLitter.setTime(time);
            }
        }

        if (windData.size() < 5) {
            List<String> notRoutineData = new ArrayList<>();
            notRoutineData.add("风廓线");
            List<String> strings = getFlag(notRoutineData, windData);
            for (String name : strings) {
                DataState dataState = addData(ds, name);
                windData.add(dataState);
            }
        }
        /*end = System.currentTimeMillis();
        System.out.println("数据处理层运行时间"+(end-start));
        System.out.println("------------");*/

        list.add(dataStates);
        list.add(routine);
        list.add(windData);

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

    //处理时间格式
    private String proFormat(Timestamp time) {
        String changTime = time.toString().split(" ")[1].split(":")[0] + ":" + time.toString().split(" ")[1].split(":")[1];
        return changTime;
    }

    //处理数据缺失
    private List<String> getFlag(List<String> list, List<DataState> list1) {
        List<String> list2 = new ArrayList<>();
        for (String str : list) {
            boolean flag = false;
            for (DataState dataState : list1) {
                if (dataState.getProductTypeCode().equals(str)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                list2.add(str);
            }
        }
        return list2;
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
