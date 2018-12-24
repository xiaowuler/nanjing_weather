package com.nanjing.weather.controller;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.GroundTemperatures;
import com.nanjing.weather.service.GroundTemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groundTemperature")
public class GroundTemperatureController {

    @Autowired
    private GroundTemperatureService groundTemperatureService;

    /**
     * 定义色斑图映射
     */
    @RequestMapping("/findAllByTerm")
    public ContourResult<GroundTemperatures> findAllByTerm(String parms1,String parms2){
        return groundTemperatureService.findAllByTerm(parms1);
    }

    @RequestMapping("/findAllBySomeTerm")
    public ContourResult<GroundTemperatures> findAllBySomeTerm(String parmOne, String parmTwo, String time){
        return groundTemperatureService.findAllBySomeTerm(parmOne,parmTwo,time);
    }
}
