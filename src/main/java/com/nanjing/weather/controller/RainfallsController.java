package com.nanjing.weather.controller;

import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Rainfalls;
import com.nanjing.weather.entity.Result;
import com.nanjing.weather.service.impl.RainfallsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rainfalls")
public class RainfallsController {
    @Autowired
    private RainfallsServiceImpl rainfallsService;

    //查询全部
    @RequestMapping("/findAll")
    @ResponseBody
    public List<Rainfalls> findAll() {
        return rainfallsService.findAll();
    }


    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody Rainfalls pressures) {
        try {
            rainfallsService.add(pressures);
            return new Result(true, "增加成功3");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败3");
        }
    }


    //修改
    @RequestMapping("/update")
    public Result update(@RequestBody Rainfalls ressures) {
        try {
            rainfallsService.update(ressures);
            return new Result(true, "修改成功3");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败3");
        }
    }

    //通过id查询
    @RequestMapping("/findOne")
    public Rainfalls findRainfallsByid(String stationId) {
        return rainfallsService.findRainfallsByid(stationId);
    }

    // 批量删除
    @RequestMapping("/delete")
    public Result delete(String[] stationIds) {
        try {
            rainfallsService.delete(stationIds);
            return new Result(true, "删除成功3");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败3");
        }
    }

    /**
     * 返回全部列表分页
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageInfo<Rainfalls> findAll(int page, int pagesize) {
        return rainfallsService.findAll(page, pagesize);
    }

    //根据条件查询

    @RequestMapping("/findAllBySomeTerm")
    public ContourResult<Rainfalls> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        return rainfallsService.findAllBySomeTerm(parmOne, parmTwo, time);
    }
}
