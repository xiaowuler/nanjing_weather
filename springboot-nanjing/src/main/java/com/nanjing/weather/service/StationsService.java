package com.nanjing.weather.service;

import com.github.pagehelper.PageInfo;
import com.nanjing.weather.domain.Stations;

import java.util.List;

public interface StationsService {
    /**
     * 查询全部
     *
     * @return
     */
    public List<Stations> findAll();

    /**
     * 查询单个
     *
     * @param id
     * @return
     */
    public Stations findStationsByid(String id);

    /**
     * 增加
     */
    public void add(Stations stations);

    /**
     * 修改
     */
    public void update(Stations stations);

    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(String[] ids);


    /**
     * 返回分页列表
     *
     * @return
     */
    public PageInfo<Stations> findAll(int page, int pageSize);
}


