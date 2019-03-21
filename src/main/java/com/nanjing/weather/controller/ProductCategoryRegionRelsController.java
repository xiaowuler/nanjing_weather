package com.nanjing.weather.controller;

import com.nanjing.weather.domain.ProductCategoryRegionRels;
import com.nanjing.weather.service.ProductCategoryRegionRelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ProductCategoryRegionRels")
public class ProductCategoryRegionRelsController {

    @Autowired
    private ProductCategoryRegionRelsService productCategoryRegionRelsService;

    @RequestMapping("/findByCategoryCode")
    public List<ProductCategoryRegionRels> findByCategoryCode(String categoryCode) {
        return productCategoryRegionRelsService.findByCategoryCode(categoryCode);
    }
}
