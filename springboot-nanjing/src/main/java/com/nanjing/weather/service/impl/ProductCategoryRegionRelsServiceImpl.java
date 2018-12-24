package com.nanjing.weather.service.impl;

import com.nanjing.weather.dao.ProductCategoryRegionRelsMapper;
import com.nanjing.weather.domain.ProductCategoryRegionRels;
import com.nanjing.weather.service.ProductCategoryRegionRelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductCategoryRegionRelsServiceImpl implements ProductCategoryRegionRelsService {
    @Autowired
    private ProductCategoryRegionRelsMapper productCategoryRegionRelsmapper;


    @Override
    public List<ProductCategoryRegionRels> findByCategoryCode(String categoryCode) {
        return productCategoryRegionRelsmapper.findByCategoryCode(categoryCode);
    }
}
