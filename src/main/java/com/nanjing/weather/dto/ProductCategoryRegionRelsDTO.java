package com.nanjing.weather.dto;

import com.nanjing.weather.domain.ProductCategoryRegionRels;
import com.nanjing.weather.domain.ProductRegion;

import java.util.List;

public class ProductCategoryRegionRelsDTO extends ProductCategoryRegionRels {

    private List<ProductRegion> productRegion;

    public List<ProductRegion> getProductRegion() {
        return productRegion;
    }

    public void setProductRegion(List<ProductRegion> productRegion) {
        this.productRegion = productRegion;
    }
}