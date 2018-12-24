package com.nanjing.weather.dao;

import com.nanjing.weather.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public User findUserById(Integer id);

    public List<User> findUserList();

    public void add(User user);
}
