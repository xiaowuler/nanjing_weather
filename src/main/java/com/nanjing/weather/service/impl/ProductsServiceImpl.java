package com.nanjing.weather.service.impl;

import com.nanjing.weather.dao.ProductsMapper;
import com.nanjing.weather.domain.*;
import com.nanjing.weather.dto.Category;
import com.nanjing.weather.dto.Product;
import com.nanjing.weather.dto.Region;
import com.nanjing.weather.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Transactional
@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsMapper productsMapper;

    /**
    * @Description:
    * @Param: [type, startTime, county]
    * @return: java.util.List<com.nanjing.weather.domain.Products>
    * @Author: LW
    * @Date: 2019/3/21
    * @Modify: 无
    */
    @Override
    public List<Products> findByTime(String type, String startTime, String county) {
        List<Products> products = productsMapper.findByTime(type, startTime, county);
        String path = productsMapper.findConfigPath();
        for (int i = 0; i < products.size(); i++) {
            String[] str=products.get(i).getUrl().split("/");
            String data=str[str.length-2];
            String data1=data.substring(6,8)+"日";
            String time=str[str.length-1].split("\\.")[0]+"时";
            String dataTime=data1+time;
            products.get(i).setDataTime(dataTime);
            String path1 = products.get(i).getUrl();
            String paths = path + "/" + path1;
            products.get(i).setUrl(paths);
        }
        return products;
    }

    @Override
    public List<Products> findAllByTime(String type, String startTime, String county, String windValue) {
        List<Products> products = productsMapper.findAllByTime(type, startTime, county, windValue);
        String root = productsMapper.findConfigPath();
        for (int i = 0; i < products.size(); i++) {
            String[] str=products.get(i).getUrl().split("/");
            String data=str[str.length-3];
            String data1=data.substring(6,8)+"日";
            String time=str[str.length-1].split("\\.")[0];
            String time1=time.substring(0,2)+"时";
            String min=time.substring(2,4)+"分";
            String dataTime=data1+time1+min;
            products.get(i).setDataTime(dataTime);

            String path = products.get(i).getUrl();
            String url = String.format("%s/%s", root, path);
            products.get(i).setUrl(url);
        }
        return products;
    }

    /**
    * @Description:
    * @Param: [type, startTime, county, windValue]
    * @return: java.util.List<com.nanjing.weather.domain.Products>
    * @Author: LW
    * @Date: 2019/3/21
    * @Modify: 无
    */
    @Override
    public List<Products> findByhalfTime(String type, String startTime, String county, String windValue) {
        List<Products> products = productsMapper.findByhalfTime(type, startTime, county, windValue);

        // 校验值是否为空
        if (products.size() < 1){
            String time = productsMapper.findMaxHalfRoutineByProduct(type, county, windValue).toString();
            products = productsMapper.findByhalfTime(type, time, county, windValue);
        }

        String path = productsMapper.findConfigPath();
        for (int i = 0; i < products.size(); i++) {
            String[] str=products.get(i).getUrl().split("/");
            String data=str[str.length-3];
            String data1=data.substring(6,8)+"日";
            String time=str[str.length-1].split("\\.")[0];
            String time1=time.substring(0,2)+"时";
            String min=time.substring(2,4)+"分";
            String dataTime=data1+time1+min;
            products.get(i).setDataTime(dataTime);

            String path1 = products.get(i).getUrl();
            String paths = path + "/" + path1;
            products.get(i).setUrl(paths);
        }
        return products;
    }

    @Override
    public List<Products> findByTiming(String type, String startTime, String county, String windValue) {
        List<Products> products = productsMapper.findByTiming(type, startTime, county, windValue);
        if (products.size() < 1){
            String time = productsMapper.findMaxRoutineByProduct(type, county, windValue).toString();
            products = productsMapper.findByTiming(type, time, county, windValue);
        }
        String path = productsMapper.findConfigPath();
        for (int i = 0; i < products.size(); i++) {
            String[] str=products.get(i).getUrl().split("/");
            String data=str[str.length-3];
            String data1=data.substring(6,8)+"日";
            String time=str[str.length-1].split("\\.")[0];
            String time1=time.substring(0,2)+"时";
            String dataTime=data1+time1;
            products.get(i).setDataTime(dataTime);

            String path1 = products.get(i).getUrl();
            String paths = path + "/" + path1;
            products.get(i).setUrl(paths);
        }
        return products;
    }

    @Override
    public List<Products> findByTime1(String type, String county, String categoryCodeValue) {
        List<Products> products = productsMapper.findByTime1(type, county, categoryCodeValue);
        String path = productsMapper.findConfigPath();
        for (int i = 0; i < products.size(); i++) {
            String[] str=products.get(i).getUrl().split("\\\\");
            String data=str[str.length-2];
            String data1=data.substring(6,8)+"日";
            String time=str[str.length-1].split("\\.")[0]+"时";
            String dataTime=data1+time;
            products.get(i).setDataTime(dataTime);

            String path1 = products.get(i).getUrl();
            String paths = path + "/" + path1;
            products.get(i).setUrl(paths);
        }
        return products;

    }

    // gps/met查询
    @Override
    public List<ProductCategoryRegionRels> findAllByTypeAndArea() {
        String configPath = productsMapper.findConfigPath();
        configPath += "/";
        List<ProductCategoryRegionRels> allGound = productsMapper.findProductCategoryRegionRels();
        for (ProductCategoryRegionRels productCategoryRegionRels : allGound) {
            for (ProductRegion productRegion : productCategoryRegionRels.getProductRegion()) {
                for (ProductType productType : productRegion.getProductTypes()) {
                    ProductData productData ;
                    if (productCategoryRegionRels.getCategoryCode().equals("feng-kuo-xian")) {
                        if (productType.getCode().equals("30-fen-zhong")) {
                            productData = productsMapper.findOneByTypeAndThirty(productRegion.getCode());
                        } else if (productType.getCode().equals("60-fen-zhong")) {
                            productData = productsMapper.findOneByTypeAndOne(productRegion.getCode());
                        } else if (productType.getCode().equals("6-fen-zhong")) {
                            productData = productsMapper.findOneByTypeAndTwo(productRegion.getCode());
                        } else {
                            productData = productsMapper.findOneByTypeAndArea(productCategoryRegionRels.getCategoryCode(), productRegion.getCode(), productType.getCode());
                        }
                    } else {
                        productData = productsMapper.findOneByTypeAndArea(productCategoryRegionRels.getCategoryCode(), productRegion.getCode(), productType.getCode());
                    }

                    if (!StringUtils.isEmpty(productData)) {
                        productData.setUrl(configPath + productData.getUrl());
                    } else {
                        if (productCategoryRegionRels.getCategoryCode().equals("gps/met")) {
                            productData = new ProductData();
                            productData.setUrl("picture/GPS.png");
                        } else {
                            productData = new ProductData();
                            productData.setUrl("picture/00f860d4c0a9ff106b8dc647610a181f.jpg");
                        }
                    }
                    productType.setProductData(productData);
                }
            }
        }
        return allGound;
    }

    @Override
    public List<Category> findCategory() {

        String configPath = productsMapper.findConfigPath();
        configPath += "/";
        List<Category> categorys = productsMapper.findCategory();

        for (Category category : categorys) {
            for (Region region : category.getRegions()) {
                for (Product product : region.getProducts()) {
                    ProductData productData ;
                    if (category.getName().equals("feng-kuo-xian")) {
                        if (product.getCode().equals("30-fen-zhong")) {
                            productData = productsMapper.findOneByTypeAndThirty(region.getCode());
                        } else if (product.getCode().equals("60-fen-zhong")) {
                            productData = productsMapper.findOneByTypeAndOne(region.getCode());
                        } else if (product.getCode().equals("6-fen-zhong")) {
                            productData = productsMapper.findOneByTypeAndTwo(region.getCode());
                        } else {
                            productData = productsMapper.findOneByTypeAndArea(category.getName(), region.getCode(), product.getCode());
                        }
                    } else {
                        productData = productsMapper.findOneByTypeAndArea(category.getName(), region.getCode(), product.getCode());
                    }

                    if (!StringUtils.isEmpty(productData)) {
                        productData.setUrl(configPath + productData.getUrl());
                    } else {
                        if (region.getName().equals("gps/met")) {
                            productData = new ProductData();
                            productData.setUrl("picture/GPS.png");
                        } else {
                            productData = new ProductData();
                            productData.setUrl("picture/00f860d4c0a9ff106b8dc647610a181f.jpg");
                        }
                    }
                    product.setImageUrl(productData.getUrl());
                }
            }
        }
        return categorys;
    }


}
