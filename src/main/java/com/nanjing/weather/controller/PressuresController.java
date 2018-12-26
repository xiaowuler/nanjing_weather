package com.nanjing.weather.controller;

import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Pressures;
import com.nanjing.weather.entity.Result;
import com.nanjing.weather.service.PressuresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pressures")
public class PressuresController {

    @Autowired
    private PressuresService pressuresService;

    //查询全部
    @RequestMapping("/findAll")
    public List<Pressures> findAll() {
        return pressuresService.findAll();
    }


    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody Pressures pressures) {
        try {
            pressuresService.add(pressures);
            return new Result(true, "增加成功2");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败3");
        }
    }


    //修改
    @RequestMapping("/update")
    public Result update(@RequestBody Pressures pressures) {
        try {
            pressuresService.update(pressures);
            return new Result(true, "修改成功2");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败3");
        }
    }

    //通过id查询
    @RequestMapping("/findOne")
    public Pressures findPressuresByid(String stationId) {
        return pressuresService.findPressuresByid(stationId);
    }

    // 批量删除
    @RequestMapping("/delete")
    public Result delete(String[] stationIds) {
        try {
            pressuresService.delete(stationIds);
            return new Result(true, "删除成功2");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败2");
        }
    }

    /**
     * 返回全部列表分页
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageInfo<Pressures> findAll(int page, int pagesize) {
        return pressuresService.findAll(page, pagesize);
    }

    /**
     * 返回色斑图绘制值
     */

    @RequestMapping("/findAllBySomeTerm")
    public ContourResult<Pressures> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        return pressuresService.findAllBySomeTerm(parmOne, parmTwo, time);
    }
}
