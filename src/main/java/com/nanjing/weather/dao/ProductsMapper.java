package com.nanjing.weather.dao;

import com.nanjing.weather.domain.ProductCategoryRegionRels;
import com.nanjing.weather.domain.ProductData;
import com.nanjing.weather.domain.ProductType;
import com.nanjing.weather.domain.Products;
import com.nanjing.weather.dto.Category;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface ProductsMapper {

    String findConfigPath();

    List<Products> findByTime(String type, String startTime, String county);

    List<Products> findAllByTime(String type, String startTime, String county, String windValue);

    List<Products> findByhalfTime(String type, String startTime, String county, String windValue);

    Timestamp findMaxHalfRoutineByProduct(String type, String county, String windValue);

    Timestamp findMaxRoutineByProduct(String type, String county, String windValue);

    List<Products> findByTiming(String type, String startTime, String county, String windValue);

    List<Products> findByTime1(String type, String county, String categoryCodeValue);

    ProductData findOneByTypeAndArea(String categoryCode, String regionCode, String typeCode);

    List<ProductCategoryRegionRels> findProductCategoryRegionRels();

    List<Category> findCategory();

    ProductData findOneByTypeAndThirty(String area);

    ProductData findOneByTypeAndOne(String area);

    ProductData findOneByTypeAndTwo(String area);

    List<ProductType> findAllProductType();
}
