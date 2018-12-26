package com.nanjing.weather.dto;

import java.util.List;
import com.nanjing.weather.domain.ProductRegion;
import com.nanjing.weather.domain.ProductCategoryRegionRels;

public class ProductCategoryRegionRelsDTO extends ProductCategoryRegionRels {

    private List<ProductRegion> productRegion;

    public List<ProductRegion> getProductRegion() {
        return productRegion;
    }

    public void setProductRegion(List<ProductRegion> productRegion) {
        this.productRegion = productRegion;
    }
}