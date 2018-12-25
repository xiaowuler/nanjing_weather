package com.nanjing.weather.controller;

import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.dao.WindsMapper;
import com.nanjing.weather.domain.Winds;
import com.nanjing.weather.entity.Result;
import com.nanjing.weather.service.WindsService;
import com.nanjing.weather.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/winds")
public class WindsController {
    @Autowired
    private WindsService windsService;

    //查询全部
    @RequestMapping("/findAll")
    public List<Winds> findAll() {
        return windsService.findAll();
    }


    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody Winds winds) {
        try {
            windsService.add(winds);
            return new Result(true, "增加成功5");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败5");
        }
    }


    //修改
    @RequestMapping("/update")
    public Result update(@RequestBody Winds winds) {
        try {
            windsService.update(winds);
            return new Result(true, "修改成功5");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败5");
        }
    }

    //通过id查询
    @RequestMapping("/findOne")
    public Winds findWindsByid(String stationId) {
        return windsService.findWindsByid(stationId);
    }

    // 批量删除
    @RequestMapping("/delete")
    public Result delete(String[] stationIds) {
        try {
            windsService.delete(stationIds);
            return new Result(true, "删除成功5");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败5");
        }
    }

    /**
     * 返回全部列表分页
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageInfo<Winds> findAll(int page, int pagesize) {
        return windsService.findAll(page, pagesize);
    }


    @RequestMapping("/demo")
    public void demo (){
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        WindsMapper windsMapper = applicationContext.getBean(WindsMapper.class);
    }

    /**
     * 根据条件绘制色斑图
     */
    @RequestMapping("/findAllBySomeTerm")
    public ContourResult<Winds> findAllBySomeTerm(String parmOne,String parmTwo,String time){
        return windsService.findAllBySomeTerm(parmOne,parmTwo,time);
    }
}
