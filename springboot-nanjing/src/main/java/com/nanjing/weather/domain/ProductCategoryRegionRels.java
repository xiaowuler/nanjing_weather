package com.nanjing.weather.domain;

import java.util.List;

public class ProductCategoryRegionRels {
    private String categoryCode;
    private String regionCode;
    private Integer sequence;
    private String name;
    private List<ProductRegion> productRegion;

    public List<ProductRegion> getProductRegion() {
        return productRegion;
    }

    public void setProductRegion(List<ProductRegion> productRegion) {
        this.productRegion = productRegion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
