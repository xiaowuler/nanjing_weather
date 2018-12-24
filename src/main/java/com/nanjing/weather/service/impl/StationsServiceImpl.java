package com.nanjing.weather.service.impl;

import com.github.pagehelper.PageHelper;

import com.github.pagehelper.PageInfo;
import com.nanjing.weather.dao.StationsMapper;
import com.nanjing.weather.domain.Stations;
import com.nanjing.weather.service.StationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class StationsServiceImpl implements StationsService {


    @Autowired
    private StationsMapper stationsMapper;

    @Override
    public List<Stations> findAll() {
        return stationsMapper.findAll();
    }

    @Override
    public Stations findStationsByid(String id) {

        return stationsMapper.findStationsByid(id);
    }

    @Override
    public void add(Stations stations) {
        stationsMapper.add(stations);
    }

    @Override
    public void update(Stations stations) {
        stationsMapper.update(stations);
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            stationsMapper.delete(id);
        }
    }

    /**
     * 按分页查询
     */
    @Override
    public PageInfo<Stations> findAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Stations> all = stationsMapper.findAll();
        PageInfo<Stations> info = new PageInfo<Stations>(all);
        return info;
    }


}