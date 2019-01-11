package com.nanjing.weather.controller;


import com.nanjing.weather.domain.DataArrivals;
import com.nanjing.weather.domain.DataState;
import com.nanjing.weather.service.DataArrivalsService;
import com.nanjing.weather.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/dataArrivals")
public class DataArrivalsController {

    @Autowired
    private DataArrivalsService dataArrivalsService;

    //查询
    @RequestMapping("/findByType")
    public PageResult<DataArrivals> findByType(String startTime, String endTime, Integer page, Integer rows, String type) {

        if (type.equals("60-fen-zhong")) {
            type = "6-fen-zhong";
            if (startTime.equals("") && endTime.equals("")) {
                PageResult<DataArrivals> pageResult = dataArrivalsService.findByTiming(page, rows, type);
                List<DataArrivals> rowsList = getDataArrivals(pageResult);
                pageResult.setRows(rowsList);
                return pageResult;
            } else {
                PageResult pageResult = getPageResult(startTime, endTime, page, rows, type);
                return pageResult;
            }

        } else if (type.equals("30-fen-zhong")) {
            type = "6-fen-zhong";
            if (startTime.equals("") && endTime.equals("")) {
                PageResult<DataArrivals> pageResult = dataArrivalsService.findByHalfTime(page, rows, type);
                List<DataArrivals> rowsList = getDataArrivals(pageResult);
                pageResult.setRows(rowsList);
                return pageResult;
            } else {
                PageResult pageResult = getPageResult(startTime, endTime, page, rows, type);
                return pageResult;
            }
        } else {
            if (startTime.equals("") && endTime.equals("")) {
                PageResult<DataArrivals> pageResult = dataArrivalsService.findByType(page, rows, type);
                List<DataArrivals> rowsList = getDataArrivals(pageResult);
                pageResult.setRows(rowsList);
                return pageResult;
            } else {
                PageResult pageResult = getPageResult(startTime, endTime, page, rows, type);
                return pageResult;
            }
        }
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

    private PageResult getPageResult(String startTime,String endTime,Integer page, Integer rows, String type){
        String[] strs = startTime.split("/");
        String[] strs2 = endTime.split("/");
        //string1 += strs[0] + "-" + strs[1] + "-" + strs[2] + " " + strs[3].substring(0, 2);
        //string2 += strs2[0] + "-" + strs2[1] + "-" + strs2[2] + " " + strs2[3].substring(0, 2);
        String string1 =String.format("%s-%s-%s %s",strs[0],strs[1],strs[2],strs[3].substring(0, 2));
        String string2 =String.format("%s-%s-%s %s",strs2[0],strs2[1],strs2[2],strs2[3].substring(0, 2));

        PageResult<DataArrivals> pageResult = dataArrivalsService.findByType1(string1, string2, page, rows, type);
        List<DataArrivals> rowsList = getDataArrivals(pageResult);
        pageResult.setRows(rowsList);
        return pageResult;
    }

    @RequestMapping("/findState")
    public List<List<DataState>> findState() {
        return dataArrivalsService.findDataState();
    }

}
