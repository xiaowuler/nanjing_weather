package com.nanjing.weather;

import com.nanjing.weather.dao.UserMapper;
import com.nanjing.weather.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;


@RestController
public class Hellocontroller {


    @Autowired
    private UserMapper userMapper;


    @RequestMapping("/hello")
    public ModelAndView sayHello() {
        return new ModelAndView("index");
    }

    @RequestMapping("hello1")
    public String sayHello1() {
        return "hello springboot,woshi111";
    }

    @RequestMapping("/findUserById")
    public User findUserById(Integer id) {
        User user = userMapper.findUserById(id);
        return user;
    }

   /* @RequestMapping("/login")
    public String controller(@RequestBody User user){
        String username=user.getUsername();
        String password=user.getPassword();

        System.out.println(username);
        System.out.println(password);
        return user.toString();
    }*/

    @RequestMapping("/findUsers")
    public List<User> findUsers() {
        return userMapper.findUserList();
    }


}
