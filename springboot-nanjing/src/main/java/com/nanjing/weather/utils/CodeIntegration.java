package com.nanjing.weather.utils;

import com.nanjing.wContour.ContourHelper;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.LegendLevel;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.LegendLevelMapper;
import com.nanjing.weather.dao.StationsMapper;
import com.nanjing.weather.domain.Stations;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CodeIntegration {

    public static List<ValuePoint> getValuePoint(List list,String parmO,String parmT){
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        List<ValuePoint> valuePoints = new ArrayList<>();
        Method metd = null;
        try {
            // 遍历集合
            for (Object object : list) {
                Class clazz = object.getClass();// 获取集合中的对象类型
                //metd = clazz.getMethod(parmO, null);// 根据字段名找到对应的get方法，null表示无参数
                if(Double.parseDouble(clazz.getMethod(parmT,null).invoke(object,null).toString())<10000){
                    String name = (String)clazz.getMethod(parmO, null).invoke(object, null);// 调用该字段的get方法
                    StationsMapper stationsMapper = applicationContext.getBean(StationsMapper.class);
                    Stations station = stationsMapper.findStationsByid(name);
                    ValuePoint valuePoint = new ValuePoint();
                    valuePoint.setId(station.getId());
                    valuePoint.setValue(Double.parseDouble(clazz.getMethod(parmT,null).invoke(object,null).toString()));
                    valuePoint.setLatitude(Double.parseDouble(station.getLatitude() + ""));
                    valuePoint.setLongitude(Double.parseDouble(station.getLongitude() + ""));

                    valuePoint.setName(station.getName());
                    valuePoints.add(valuePoint);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return valuePoints;
    }

    /**
     *
     * @param type 色标图查询类型
     * @return
     */
    public static ContourResult getResult(String type,List<ValuePoint> list,String time){
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        LegendLevelMapper legendLevelMapper = applicationContext.getBean(LegendLevelMapper.class);
        List<LegendLevel> legendLevels = legendLevelMapper.findAll(type);
        ContourHelper contourHelper = new ContourHelper("D:\\project\\springboot-nanjing\\springboot-nanjing\\src\\main\\resources\\static\\json\\nanjing.json");
        ContourResult contourResult = contourHelper.Calc(list, legendLevels, 8, -9999);
        contourResult.setTime(time);
        return contourResult;
    }

}
