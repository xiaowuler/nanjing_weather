package com.nanjing.weather.controller;


import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Humidities;
import com.nanjing.weather.domain.Rainfalls;
import com.nanjing.weather.entity.Result;
import com.nanjing.weather.service.HumiditiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@RequestMapping("/humidities")
public class HumiditiesController {
    @Autowired
    private HumiditiesService humiditiesService;

    //查询全部
    @RequestMapping("/findAll")
    public List<Humidities> findAll() {
        return humiditiesService.findAll();
    }

    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody Humidities humidities) {
        try {
            humiditiesService.add(humidities);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }

    //修改
    @RequestMapping("/update")
    public Result update(@RequestBody Humidities humidities) {
        try {
            humiditiesService.update(humidities);
            return new Result(true, "修改成功1");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败1");
        }
    }


    //查询单个
    @RequestMapping("/findOne")
    public Humidities findHumiditiesByid(String stationId) {
        return humiditiesService.findHumiditiesByid(stationId);
    }

    // 批量删除
    @RequestMapping("/delete")
    public Result delete(String[] stationIds) {
        try {
            humiditiesService.delete(stationIds);
            return new Result(true, "删除成功1");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败1");
        }
    }

    /**
     * 返回全部列表分页
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageInfo<Humidities> findAll(int page, int pagesize) {
        return humiditiesService.findAll(page, pagesize);

    }

    /**
     * 绘制色斑图
     */

    @RequestMapping("/findAllBySomeTerm")
    public ContourResult<Humidities> findAllBySomeTerm(String parmOne, String parmTwo, String time){
        return humiditiesService.findAllBySomeTerm(parmOne,parmTwo,time);
    }

}
