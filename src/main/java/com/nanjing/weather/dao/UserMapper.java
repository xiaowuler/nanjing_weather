package com.nanjing.weather.dao;

import com.nanjing.weather.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    User findUserById(Integer id);

    List<User> findUserList();

    void add(User user);
}
