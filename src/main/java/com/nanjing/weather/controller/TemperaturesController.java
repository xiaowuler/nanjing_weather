package com.nanjing.weather.controller;

import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Temperatures;
import com.nanjing.weather.entity.Result;

import com.nanjing.weather.service.TemperaturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/temperatures")
public class TemperaturesController {
    @Autowired
    private TemperaturesService temperaturesService;

    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody Temperatures temperatures) {
        try {
            temperaturesService.add(temperatures);
            return new Result(true, "增加成功4");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败4");
        }
    }


    //修改
    @RequestMapping("/update")
    public Result update(@RequestBody Temperatures temperatures) {
        try {
            temperaturesService.update(temperatures);
            return new Result(true, "修改成功4");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败4");
        }
    }

    //通过id查询
    @RequestMapping("/findOne")
    public Temperatures findPressuresByid(String stationId) {
        return temperaturesService.findTemperaturesByid(stationId);
    }

    // 批量删除
    @RequestMapping("/delete")
    public Result delete(String[] stationIds) {
        try {
            temperaturesService.delete(stationIds);
            return new Result(true, "删除成功4");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败4");
        }
    }

    /**
     * 返回全部列表分页
     *
     * @return
     */
    @RequestMapping("/findAll")
    public ContourResult<Temperatures> findAll(int time, Double rainfalls) {
        System.out.println("1111");
        return temperaturesService.findAll(time, rainfalls);
    }

    //根据条件查询

    @RequestMapping("/findAllBySomeTerm")
    public ContourResult<Temperatures> findAllBySomeTerm(String parmOne,String parmTwo,String time){
        return temperaturesService.findAllBySomeTerm(parmOne,parmTwo,time);
    }
}
