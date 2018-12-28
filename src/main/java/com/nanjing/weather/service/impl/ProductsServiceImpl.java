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

    @Override
    public List<Products> findByTime(String type, String startTime, String county) {
        List<Products> products = productsMapper.findByTime(type, startTime, county);
        String path = productsMapper.findConfigPath();
        for (int i = 0; i < products.size(); i++) {
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
            String path = products.get(i).getUrl();
            String url = String.format("%s/%s", root, path);
            products.get(i).setUrl(url);
        }
        return products;
    }

    @Override
    public List<Products> findByhalfTime(String type, String startTime, String county, String windValue) {
        List<Products> products = productsMapper.findByhalfTime(type, startTime, county, windValue);
        String path = productsMapper.findConfigPath();
        for (int i = 0; i < products.size(); i++) {
            String path1 = products.get(i).getUrl();
            String paths = path + "/" + path1;
            products.get(i).setUrl(paths);
        }
        /*List<Products> products1= productsMapper.findByhalfTime1(type, startTime, county,windValue);
        List<Products> list=new ArrayList<>();



        for(int i=0;i<products1.size();i++){
            String path1= products1.get(i).getUrl();
            String paths=path+"/"+path1;
            products1.get(i).setUrl(paths);
        }
        for(int i=0;i<products1.size();i++){
            list.add(products.get(i));
            list.add(products1.get(i));
        }*/

        return products;
    }

    @Override
    public List<Products> findByTiming(String type, String startTime, String county, String windValue) {
        List<Products> products = productsMapper.findByTiming(type, startTime, county, windValue);
        String path = productsMapper.findConfigPath();
        for (int i = 0; i < products.size(); i++) {
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
            String path1 = products.get(i).getUrl();
            String paths = path + "/" + path1;
            products.get(i).setUrl(paths);
        }
        return products;

    }

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
                    //productData.getUrl();
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
                    //productData.getUrl();
                    product.setImageUrl(productData.getUrl());
                }
            }
        }

        return categorys;
    }


}
