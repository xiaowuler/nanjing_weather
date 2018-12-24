package com.nanjing.weather.dao;

import com.nanjing.weather.domain.ProductCategoryRegionRels;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductCategoryRegionRelsMapper {
    public List<ProductCategoryRegionRels> findByCategoryCode(String categoryCode);
}
