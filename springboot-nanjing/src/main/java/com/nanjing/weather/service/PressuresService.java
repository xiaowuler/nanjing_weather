package com.nanjing.weather.service;

import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Pressures;

import java.util.List;

public interface PressuresService {


    public List<Pressures> findAll();

    /**
     * 查询单个
     *
     * @param id
     * @return
     */
    public Pressures findPressuresByid(String stationId);

    /**
     * 增加
     */
    public void add(Pressures pressures);

    /**
     * 修改
     */
    public void update(Pressures pressures);

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
    public PageInfo<Pressures> findAll(int page, int pageSize);

    ContourResult<Pressures> findAllByTerm(String parms);

    ContourResult<Pressures> findAllBySomeTerm(String parmOne,String parmTwo,String time);
}
