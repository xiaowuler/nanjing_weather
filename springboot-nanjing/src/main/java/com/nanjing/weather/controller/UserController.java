package com.nanjing.weather.controller;

import com.nanjing.weather.dao.UserMapper;
import com.nanjing.weather.utils.SpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//用户登录验证
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/findOne")
    public void findOne(){
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        UserMapper bean = applicationContext.getBean(UserMapper.class);
        System.out.println(bean.findUserById(1).getId());
    }
}
