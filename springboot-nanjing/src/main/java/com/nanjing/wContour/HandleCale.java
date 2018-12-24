package com.nanjing.wContour;

import com.nanjing.wContour.bean.ValuePoint;
import com.nanjing.weather.dao.StationsMapper;
import com.nanjing.weather.domain.Stations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HandleCale {

    @Autowired
    private StationsMapper stationsMapper;

    public ValuePoint handlerCale(String id){
        Stations station = stationsMapper.findStationsByid(id);
        ValuePoint valuePoint = new ValuePoint();
        valuePoint.setLatitude(Double.parseDouble(station.getLatitude() + ""));
        valuePoint.setLongitude(Double.parseDouble(station.getLongitude() + ""));
        return valuePoint;
    }
}
