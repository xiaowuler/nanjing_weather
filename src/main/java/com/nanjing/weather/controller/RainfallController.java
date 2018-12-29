package com.nanjing.weather.controller;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Rainfalls;
import com.nanjing.weather.entitys.Rainfall;
import com.nanjing.weather.service.RainfallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rainfalls")
public class RainfallController {

    @Autowired
    private RainfallService rainfallsService;

    //根据条件查询

    @RequestMapping("/findAllBySomeTerm")
    public ContourResult<Rainfall> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        return rainfallsService.findAllBySomeTerm(parmOne, parmTwo, time);
    }

    @RequestMapping("/findSomeByTerm")
    public ContourResult<Rainfall> findSomeByTerm(String parmOne, String parmTwo, String time){
        return rainfallsService.findSomeByTerm(parmOne, parmTwo, time);
    }
}
