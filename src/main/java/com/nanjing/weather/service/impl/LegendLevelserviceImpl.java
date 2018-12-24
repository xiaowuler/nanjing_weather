package com.nanjing.weather.service.impl;

import com.nanjing.wContour.bean.LegendLevel;
import com.nanjing.weather.dao.LegendLevelMapper;
import com.nanjing.weather.service.LegendLevelservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LegendLevelserviceImpl implements LegendLevelservice {
    @Autowired
    private LegendLevelMapper legendLevelMapper;

    @Override
    public List<LegendLevel> findAll(String type) {
        return legendLevelMapper.findAll(type);
    }
}
