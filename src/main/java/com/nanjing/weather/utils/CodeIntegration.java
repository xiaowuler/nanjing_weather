package com.nanjing.weather.utils;

import com.nanjing.wContour.ContourHelper;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.LegendLevel;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.LegendLevelMapper;
import com.nanjing.weather.dao.StationsMapper;
import com.nanjing.weather.domain.Rainfalls;
import com.nanjing.weather.domain.Stations;
import com.nanjing.weather.entity.RainFall;
import com.nanjing.weather.entity.RainFallCenter;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeIntegration {

    public static List<ValuePoint> getValuePoint(List list, String parmO, String parmT) {
        if(list.size() > 0){
            ApplicationContext applicationContext = SpringUtil.getApplicationContext();
            List<ValuePoint> valuePoints = new ArrayList<>();
            Method metd = null;
            try {
                // 遍历集合
                for (Object object : list) {
                    Class clazz = object.getClass();// 获取集合中的对象类型
                    //metd = clazz.getMethod(parmO, null);// 根据字段名找到对应的get方法，null表示无参数
                    if (Double.parseDouble(clazz.getMethod(parmT, null).invoke(object, null).toString()) < 9527) {
                        String name = (String) clazz.getMethod(parmO, null).invoke(object, null);// 调用该字段的get方法
                        StationsMapper stationsMapper = applicationContext.getBean(StationsMapper.class);
                        Stations station = stationsMapper.findStationsByid(name);
                        ValuePoint valuePoint = new ValuePoint();
                        valuePoint.setId(station.getId());
                        valuePoint.setValue(Double.parseDouble(clazz.getMethod(parmT, null).invoke(object, null).toString()));
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
        return null;
    }

    /**
     * @param type 色标图查询类型
     * @return
     */
    public static ContourResult getResult(String type, List<ValuePoint> list, String time) {
        if(list.size() > 0){
            ApplicationContext applicationContext = SpringUtil.getApplicationContext();
            LegendLevelMapper legendLevelMapper = applicationContext.getBean(LegendLevelMapper.class);
            List<LegendLevel> legendLevels = legendLevelMapper.findAll(type);
            ContourHelper contourHelper = new ContourHelper("D:\\project\\springboot-nanjing\\springboot-nanjing\\src\\main\\resources\\static\\json\\nanjing.json");
            ContourResult contourResult = contourHelper.Calc(list, legendLevels, 8, -9999);
            contourResult.setTime(time);
            return contourResult;
        }
        return null;
    }

    /**
     * 算出相同地区平均值
     * @param objList 输入的结果集
     * @param allClassName 创建对象的全类名
     * @param creObjSetMeth 创建对象的set方法
     * @param getListName 输入结果集的关系列表
     */
    public static List caleAvg(List objList,String getListName,String getMethName,String allClassName,String creObjSetMeth ){
        if(objList.size() > 0){
            List creList = new ArrayList();
            try {
                for (Object object : objList) {
                    Class clazz = object.getClass();
                    double x = 0;
                    double y = 0;

                    List list = (List)clazz.getMethod(getListName).invoke(object);
                    for (Object obj : list) {
                        Class cla = obj.getClass();
                        if (Double.parseDouble(cla.getMethod(getMethName).invoke(obj).toString()) < 4399) {
                            x += Double.parseDouble(cla.getMethod(getMethName).invoke(obj).toString());
                            y++;
                        }
                    }

                    if (y != 0) {
                        Class objs = Class.forName(allClassName);
                        Object o = objs.newInstance();
                        objs.getMethod("setStationId",String.class).invoke(o,(clazz.getMethod("getStationId").invoke(object).toString()));
                        objs.getMethod(creObjSetMeth,BigDecimal.class).invoke(o,(new BigDecimal(new DecimalFormat("#.00").format(x / y))));
                        creList.add(o);
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return creList;
        }
        return null;
    }

    /**
     * 输出持久层条件格式
     */
    public static Map getTerm(String parmOne,String parmTwo,String time){
        Map map = new HashMap();
        if (time != null) {
            map.put("value",new BigDecimal(parmOne.substring(1)));
            map.put("createTime",time.split(":")[0]);
            map.put("routineTime",time.split(":")[1]);
        } else {
            map.put("value",new BigDecimal(parmTwo.substring(1)));
            map.put("routineTime",parmOne);
        }
        return map;
    }
}
