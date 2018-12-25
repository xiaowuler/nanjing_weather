package com.nanjing.weather.service;

import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Winds;

import java.util.List;

public interface WindsService {

    public List<Winds> findAll();

    /**
     * 查询单个
     *
     * @param id
     * @return
     */
    public Winds findWindsByid(String stationId);

    /**
     * 增加
     */
    public void add(Winds winds);

    /**
     * 修改
     */
    public void update(Winds inds);

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
    public PageInfo<Winds> findAll(int page, int pageSize);

    /**
     * 返回wContour结果集
     */
    ContourResult<Winds> findAllBySomeTerm(String parmsOne,String parmsTwo,String time);
}
