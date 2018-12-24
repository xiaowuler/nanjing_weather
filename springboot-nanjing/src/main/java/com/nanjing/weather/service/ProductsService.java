package com.nanjing.weather.service;


import com.nanjing.weather.domain.ProductCategoryRegionRels;
import com.nanjing.weather.domain.ProductData;
import com.nanjing.weather.domain.Products;

import java.util.List;

public interface ProductsService {
    List<Products> findByTime(String type,String startTime,String county);

    List<Products>findAllByTime(String type, String startTime, String county,String windValue);

    List<Products>findByTiming(String type, String startTime,String county,String windValue);

    List<Products>findByhalfTime(String type, String startTime, String county,String windValue);

    List<Products>findByTime1(String type,String county,String categoryCodeValue);

    List<ProductCategoryRegionRels> findAllByTypeAndArea();
}
