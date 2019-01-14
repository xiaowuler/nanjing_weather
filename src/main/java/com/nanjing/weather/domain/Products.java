package com.nanjing.weather.domain;

import java.sql.Timestamp;

public class Products {
    private Integer id;
    private String category_code;
    private String type_code;
    private String product_categoy_code;
    private String url;
    private Timestamp routine_time;
    private Timestamp create_time;
    private String dataTime;

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public String getProduct_categoy_code() {
        return product_categoy_code;
    }

    public void setProduct_categoy_code(String product_categoy_code) {
        this.product_categoy_code = product_categoy_code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getRoutine_time() {
        return routine_time;
    }

    public void setRoutine_time(Timestamp routine_time) {
        this.routine_time = routine_time;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }
}
