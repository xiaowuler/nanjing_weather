package com.nanjing.weather.service;

import com.github.pagehelper.PageInfo;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Rainfalls;
import com.nanjing.weather.domain.Temperatures;

import java.util.List;

public interface RainfallsService {

    public List<Rainfalls> findAll();

    /**
     * 查询单个
     *
     * @param id
     * @return
     */
    public Rainfalls findRainfallsByid(String stationId);

    /**
     * 增加
     */
    public void add(Rainfalls rainfalls);

    /**
     * 修改
     */
    public void update(Rainfalls rainfalls);

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
    public PageInfo<Rainfalls> findAll(int page, int pageSize);

    /**
     * 返回ContourResult
     */

    ContourResult<Rainfalls> findAllByTerm(Integer time, String railfalls);

    ContourResult<Rainfalls> findAllBySomeTerm(String parmOne,String parmTwo,String time);
}
