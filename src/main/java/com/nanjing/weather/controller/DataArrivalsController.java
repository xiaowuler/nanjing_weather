package com.nanjing.weather.controller;


import com.nanjing.weather.domain.DataArrivals;
import com.nanjing.weather.domain.DataState;
import com.nanjing.weather.service.DataArrivalsService;
import com.nanjing.weather.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/dataArrivals")
public class DataArrivalsController {

    @Autowired
    private DataArrivalsService dataArrivalsService;

    // 查询
    @RequestMapping("/findByType")
    public PageResult<DataArrivals> findByType(String startTime, String endTime, Integer page, Integer rows, String type,String regionCode) {

        if (getEndTime(endTime) != null)
            endTime = getEndTime(endTime);

        if (type.equals("60-fen-zhong")) {
            type = "6-fen-zhong";
            if (startTime.equals("") && endTime.equals("")) {
                PageResult<DataArrivals> pageResult = dataArrivalsService.findByTiming(page, rows, type,regionCode);
                List<DataArrivals> rowsList = getDataArrivals(pageResult);
                pageResult.setRows(rowsList);
                return pageResult;
            } else {
                return getDataArrivalsPageResult(startTime, endTime, page, rows, type,regionCode);
            }

        } else if (type.equals("30-fen-zhong")) {
            type = "6-fen-zhong";
            if (startTime.equals("") && endTime.equals("")) {
                PageResult<DataArrivals> pageResult = dataArrivalsService.findByHalfTime(page, rows, type,regionCode);
                List<DataArrivals> rowsList = getDataArrivals(pageResult);
                pageResult.setRows(rowsList);
                return pageResult;
            } else {
                return getDataArrivalsPageResult(startTime, endTime, page, rows, type,regionCode);
            }
        } else if(type.equals("rainfall")||type.equals("temperature")||type.equals("wind")||
                type.equals("pressure")||type.equals("humidity")||type.equals("ground_temperature")){
            if(startTime.equals("") && endTime.equals("")){
                // 六要素查询（没有地区）
                PageResult<DataArrivals> pageResult = dataArrivalsService.findAllType(page, rows, type);
                List<DataArrivals> rowsList = getDataArrivals(pageResult);
                pageResult.setRows(rowsList);
                return pageResult;
            }else{
                // 六要素根据时间查询（没有地区）
                return getDataArrivalsPageResultAllType(startTime, endTime, page, rows, type);
            }
        }else{
            if (startTime.equals("") && endTime.equals("")) {
                PageResult<DataArrivals> pageResult = dataArrivalsService.findByType(page, rows, type,regionCode);
                List<DataArrivals> rowsList = getDataArrivals(pageResult);
                pageResult.setRows(rowsList);
                return pageResult;
            } else {
                // 2018/12/03/00时+"/00/00"  "2018-11-10 10:20:00" 定义规范时间方法
                return getDataArrivalsPageResult(startTime, endTime, page, rows, type,regionCode);
            }
        }
    }

    private String getEndTime(String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH时");
        try {
            Date endDate = sdf.parse(endTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.HOUR, 1);
            endDate = cal.getTime();
            return sdf.format(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private PageResult<DataArrivals> getDataArrivalsPageResult(String startTime, String endTime, Integer page, Integer rows, String type,String regionCode) {
        String[] strs = startTime.split("/");
        String[] strs2 = endTime.split("/");
        String string1 = "";
        String string2 = "";
        string1 += strs[0] + "-" + strs[1] + "-" + strs[2] + " " + strs[3].substring(0, 2);
        string2 += strs2[0] + "-" + strs2[1] + "-" + strs2[2] + " " + strs2[3].substring(0, 2);

        PageResult<DataArrivals> pageResult = dataArrivalsService.findByType1(string1, string2, page, rows, type,regionCode);
        List<DataArrivals> rowsList = getDataArrivals(pageResult);
        pageResult.setRows(rowsList);
        return pageResult;
    }

    private PageResult<DataArrivals> getDataArrivalsPageResultAllType(String startTime, String endTime, Integer page, Integer rows, String type) {
        String[] strs = startTime.split("/");
        String[] strs2 = endTime.split("/");
        String string1 = "";
        String string2 = "";
        string1 += strs[0] + "-" + strs[1] + "-" + strs[2] + " " + strs[3].substring(0, 2);
        string2 += strs2[0] + "-" + strs2[1] + "-" + strs2[2] + " " + strs2[3].substring(0, 2);

        PageResult<DataArrivals> pageResult = dataArrivalsService.findAllType1(string1, string2, page, rows, type);
        List<DataArrivals> rowsList = getDataArrivals(pageResult);
        pageResult.setRows(rowsList);
        return pageResult;
    }

    private List<DataArrivals> getDataArrivals(PageResult<DataArrivals> pageResult) {
        List<DataArrivals> rowsList = pageResult.getRows();
        for (DataArrivals dataArrivals : rowsList) {
            Timestamp begin_time = dataArrivals.getBegin_sync_time();
            Timestamp end_time = dataArrivals.getEnd_sync_time();
            Timestamp routine_time = dataArrivals.getRoutine_Time();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dataArrivals.setBegin(sdf1.format(begin_time));
            dataArrivals.setEnd(sdf1.format(end_time));
            dataArrivals.setRoutine(sdf.format(routine_time));
        }
        return rowsList;
    }

    @RequestMapping("/findState")
    public List<List<DataState>> findState() {
        return dataArrivalsService.findDataState();
    }
}