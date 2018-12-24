package com.nanjing.weather.service;

import com.nanjing.weather.domain.ProductCategoryRegionRels;

import java.util.List;

public interface ProductCategoryRegionRelsService {
    public List<ProductCategoryRegionRels> findByCategoryCode(String categoryCode);
}
