package com.nanjing.weather.service;

import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.weather.domain.Temperatures;

public interface TemperaturesService {
    /**
     * 查询单个
     *
     * @param id
     * @return
     */
    public Temperatures findTemperaturesByid(String stationId);

    /**
     * 增加
     */
    public void add(Temperatures temperatures);

    /**
     * 修改
     */
    public void update(Temperatures temperatures);

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
    public ContourResult<Temperatures> findAll(Integer page, Double pageSize);

    /**
     * 返回ContourResult
     */
    ContourResult<Temperatures> findAllBySomeTerm(String parmOne, String parmTwo, String time);
}
