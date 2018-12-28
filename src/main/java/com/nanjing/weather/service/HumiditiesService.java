package com.nanjing.weather.service;

import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Humidities;

import java.util.List;

public interface HumiditiesService {

    public List<Humidities> findAll();

    /**
     * 查询单个
     *
     * @param id
     * @return
     */
    public Humidities findHumiditiesByid(String stationId);

    /**
     * 增加
     */
    public void add(Humidities humidities);

    /**
     * 修改
     */
    public void update(Humidities humidities);

    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(String[] stationIds);


    /**
     * 返回分页列表
     *
     * @return
     */
    public PageInfo<Humidities> findAll(int page, int pageSize);

    /**
     * 返回wContour结果集
     */
    ContourResult<Humidities> findAllBySomeTerm(String parmOne, String parmTwo, String time);
}

