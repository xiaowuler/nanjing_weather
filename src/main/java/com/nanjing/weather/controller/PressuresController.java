package com.nanjing.weather.controller;

import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Pressures;
import com.nanjing.weather.entity.Result;
import com.nanjing.weather.entitys.Pressure;
import com.nanjing.weather.service.PressureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pressures")
public class PressuresController {

    @Autowired
    private PressureService pressuresService;

    /**
     * 返回色斑图绘制值
     */
    @RequestMapping("/findAllBySomeTerm")
    public ContourResult<Pressure> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        return pressuresService.findAllBySomeTerm(parmOne, parmTwo, time);
    }
}
