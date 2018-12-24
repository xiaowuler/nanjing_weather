package com.nanjing.weather.domain;

import java.util.List;

public class DataState {

    private String productCategoryCode;
    private String productTypeCode;
    private List<DataArrivalLitter> dataArrivalLitter;

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    public List<DataArrivalLitter> getDataArrivalLitter() {

        return dataArrivalLitter;
    }

    public void setDataArrivalLitter(List<DataArrivalLitter> dataArrivalLitter) {
        this.dataArrivalLitter = dataArrivalLitter;
    }

    public String getProductCategoryCode() {
        return productCategoryCode;
    }

    public void setProductCategoryCode(String productCategoryCode) {
        this.productCategoryCode = productCategoryCode;
    }
}
