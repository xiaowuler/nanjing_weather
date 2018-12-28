package com.nanjing.weather.controller;

import com.github.pagehelper.PageInfo;
import com.nanjing.weather.domain.Stations;
import com.nanjing.weather.entity.Result;
import com.nanjing.weather.service.StationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stations")
public class StationsController {

    @Autowired
    private StationsService stationsService;

    //返回全部数据
    @RequestMapping("/findAll")
    public List<Stations> findAll() {
        return stationsService.findAll();
    }

    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody Stations stations) {
        try {
            stationsService.add(stations);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    //修改
    @RequestMapping("/update")
    public Result update(@RequestBody Stations stations) {
        try {
            stationsService.update(stations);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    //查询单个
    @RequestMapping("/findStationsByid")
    public Stations findStationsByid(String id) {
        return stationsService.findStationsByid(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(String[] ids) {
        try {
            stationsService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }


    /**
     * 返回全部列表分页
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageInfo<Stations> findAll(int page, int pagesize) {
        return stationsService.findAll(page, pagesize);
    }


}
