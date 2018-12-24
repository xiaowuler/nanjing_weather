package com.nanjing.weather.controller;

import com.nanjing.wContour.bean.LegendLevel;
import com.nanjing.weather.service.LegendLevelservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LegendLevelController {

    @Autowired
    private LegendLevelservice legendLevelservice;

    //查询全部
    @RequestMapping("/findAll")
    public List<LegendLevel> findAll() {
        return legendLevelservice.findAll("");
    }


}
