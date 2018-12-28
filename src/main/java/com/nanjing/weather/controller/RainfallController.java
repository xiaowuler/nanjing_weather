package com.nanjing.weather.controller;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Rainfalls;
import com.nanjing.weather.service.impl.RainfallServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rainfalls")
public class RainfallController {
    @Autowired
    private RainfallServiceImpl rainfallsService;

    //根据条件查询

    @RequestMapping("/findAllBySomeTerm")
    public ContourResult<Rainfalls> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        return rainfallsService.findAllBySomeTerm(parmOne, parmTwo, time);
    }

    @RequestMapping("/findSomeByTerm")
    public ContourResult<Rainfalls> findSomeByTerm(String parmOne, String parmTwo, String time){
        return rainfallsService.findSomeByTerm(parmOne, parmTwo, time);
    }
}
