package com.nanjing.weather.controller;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Humidities;
import com.nanjing.weather.entitys.Humidity;
import com.nanjing.weather.service.HumidityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/humidities")
public class HumidityController {

    @Autowired
    private HumidityService humiditiesService;

    /**
     * 绘制色斑图
     */

    @RequestMapping("/findAllBySomeTerm")
    public ContourResult<Humidity> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        return humiditiesService.findAllBySomeTerm(parmOne, parmTwo, time);
    }

}
