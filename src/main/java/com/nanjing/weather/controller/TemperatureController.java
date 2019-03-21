package com.nanjing.weather.controller;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Temperatures;
import com.nanjing.weather.entitys.Temperature;
import com.nanjing.weather.service.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/temperatures")
public class TemperatureController {

    @Autowired
    private TemperatureService temperaturesService;

    //根据条件查询
    @RequestMapping("/findAllBySomeTerm")
    public ContourResult<Temperature> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        return temperaturesService.findAllBySomeTerm(parmOne, parmTwo, time);
    }
}
