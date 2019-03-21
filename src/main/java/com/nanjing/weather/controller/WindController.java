package com.nanjing.weather.controller;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.dao.WindsMapper;
import com.nanjing.weather.domain.Winds;
import com.nanjing.weather.service.WindService;
import com.nanjing.weather.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/winds")
public class WindController {
    @Autowired
    private WindService windsService;

    @RequestMapping("/demo")
    public void demo() {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        WindsMapper windsMapper = applicationContext.getBean(WindsMapper.class);
    }

    /**
     * 根据条件绘制色斑图
     */
    @RequestMapping("/findAllBySomeTerm")
    public ContourResult<Winds> findAllBySomeTerm(String parmOne, String parmTwo, String time) {
        return windsService.findAllBySomeTerm(parmOne, parmTwo, time);
    }
}
