package com.nanjing.weather.service.impl;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.RainfallMapper;
import com.nanjing.weather.domain.Rainfalls;
import com.nanjing.weather.entity.RainFall;
import com.nanjing.weather.entity.RainFallCenter;
import com.nanjing.weather.entitys.Rainfall;
import com.nanjing.weather.service.RainfallService;
import com.nanjing.weather.utils.CodeIntegration;
import com.nanjing.weather.utils.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RainfallServiceImpl implements RainfallService {

    @Autowired
    private RainfallMapper rainfallsMapper;

    /**
     *
     * @param parmOne
     * @param parmTwo
     * @param time 时间选段参数
     * @return 绘图数据
     */
    @Override
    public ContourResult<Rainfall> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        List<ValuePoint> list;
        List<Rainfalls> rainfallsList;
        RainFallCenter rainFallCenter = new RainFallCenter();
        if (time != null) {
            rainFallCenter.setValue(new BigDecimal(parmOne));
            rainFallCenter.setCreateTime(time.split(":")[0]);
            rainFallCenter.setRoutineTime(time.split(":")[1]);
        } else {
            rainFallCenter.setValue(new BigDecimal(parmTwo));
            rainFallCenter.setRoutineTime(parmOne);
        }
        List<RainFall> rainFalls = rainfallsMapper.findAllBySomeTerm(rainFallCenter);
        //rainfallsList = CodeIntegration.caleAvg(rainFalls,"getRainFallCenter","getValue","com.nanjing.weather.entitys.Rainfall","setValue");
        if (time == null)
            rainfallsList = GetIndexValue(rainFalls,Double.parseDouble(parmTwo));
        else
            rainfallsList = GetValues(rainFalls,Double.parseDouble(parmOne));

        list = CodeIntegration.getValuePoint(rainfallsList, "getStation_Id", "getValue");

        if(list == null)
            return null;

        if (time != null)
            return CodeIntegration.getResult("rainfalls", list, time.split(":")[1]);

        return CodeIntegration.getResult("rainfalls", list, TimeFormat.getTime(rainFalls.get(0).getRainFallCenter().get(0).getRoutineTime()));

    }

    @Override
    public ContourResult<Rainfall> findSomeByTerm(String parmOne, String parmTwo, String time) {
        List<ValuePoint> list;
        List<Rainfalls> rainfallsList;
        RainFallCenter rainFallCenter = new RainFallCenter();

        rainFallCenter.setValue(new BigDecimal(0));
        rainFallCenter.setRoutineTime("1");

        List<RainFall> rainFalls = rainfallsMapper.findAllBySomeTerm(rainFallCenter);
        if (rainFalls.size() > 0) {
            rainfallsList = CodeIntegration.caleAvg(rainFalls,"getRainFallCenter","getValue","com.nanjing.weather.entitys.Rainfall","setValue");
            list = CodeIntegration.getValuePoint(rainfallsList, "getStation_Id", "getValue");
            if (rainfallsList.size() > 0) {
                if (time != null) {
                    return CodeIntegration.getResult("rainfalls", list, time.split(":")[1]);
                } else {
                    return CodeIntegration.getResult("rainfalls", list, TimeFormat.getTime(rainFalls.get(0).getRainFallCenter().get(0).getRoutineTime()));
                }
            }
        }
        return null;
    }

    private List<Rainfalls> GetValues(List<RainFall> rainFalls, Double maxValue){
        List<Rainfalls> rainfallsList = new ArrayList<>();
        Rainfalls rainfall;

        for (RainFall rainFall : rainFalls){
            double x = 0;
            for (RainFallCenter rainFallCenter : rainFall.getRainFallCenter()){
                x += Double.parseDouble(rainFallCenter.getValue().toString());
            }

            if (x >= maxValue){
                rainfall = new Rainfalls();
                rainfall.setStation_Id(rainFall.getStationId());
                rainfall.setValue(new BigDecimal(new DecimalFormat("#.0").format(x )));
                rainfallsList.add(rainfall);
            }
        }

        return rainfallsList;
    }

    private List<Rainfalls> GetIndexValue(List<RainFall> rainFalls, Double maxValue){
        List<Rainfalls> rainfallsList = new ArrayList<>();
        Rainfalls rainfalls;
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (RainFall rainfall : rainFalls){
            double count = 0d;
            for (int x = 0, len = rainfall.getRainFallCenter().size(); x < len; x++){
                Date date = null;
                try {
                    date = sdf.parse(rainfall.getRainFallCenter().get(x).getRoutineTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (date == null)
                    break;
                long millisecond = date.getTime();

                if (x == len - 1)
                {
                    count += Double.parseDouble(rainfall.getRainFallCenter().get(x).getValue().toString());
                    break;
                }
                if (millisecond % 3600000 < 60000)
                    count += Double.parseDouble(rainfall.getRainFallCenter().get(x).getValue().toString());
                if (x == 0)
                    count = count - Double.parseDouble(rainfall.getRainFallCenter().get(x).getValue().toString());

            }

            if (count >= maxValue){
                rainfalls = new Rainfalls();
                rainfalls.setStation_Id(rainfall.getStationId());
                rainfalls.setValue(new BigDecimal(new DecimalFormat("#.0").format(count)));
                rainfallsList.add(rainfalls);
            }
        }

        return rainfallsList;
    }
}
