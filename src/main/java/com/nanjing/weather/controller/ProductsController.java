package com.nanjing.weather.controller;

import com.nanjing.weather.domain.ProductCategoryRegionRels;
import com.nanjing.weather.domain.Products;
import com.nanjing.weather.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @RequestMapping("/findByTime")
    public List<Products> findByTime(String type, String imagetime, String county, String windValue, String categoryCodeValue) {
        String[] strs = imagetime.split("/");
        if (categoryCodeValue.equals("feng-kuo-xian")) {
            if (type.equals("6-fen-zhong")) {
                String startTime = strs[0] + "-" + strs[1] + "-" + strs[2] + " " + strs[3].substring(0, 2) + ":" + strs[4].substring(0, 2) + ":00";
                return productsService.findAllByTime(type, startTime, county, windValue);
            } else if (type.equals("30-fen-zhong")) {
                type = "6-fen-zhong";
                String startTime = strs[0] + "-" + strs[1] + "-" + strs[2] + " " + strs[3].substring(0, 2) + ":" + strs[4].substring(0, 2) + ":00";
                return productsService.findByhalfTime(type, startTime, county, windValue);
            } else if (type.equals("60-fen-zhong")) {
                type = "6-fen-zhong";
                String startTime = strs[0] + "-" + strs[1] + "-" + strs[2] + " " + strs[3].substring(0, 2) + ":00:00";
                return productsService.findByTiming(type, startTime, county, windValue);
            }
        } else if (categoryCodeValue.equals("gps/met")) {
            return productsService.findByTime1(type, county, categoryCodeValue);
        } else {
            String startTime = strs[0] + "-" + strs[1] + "-" + strs[2] + " " + strs[3].substring(0, 2) + ":00:00";
            return productsService.findByTime(type, startTime, county);


        }

        return null;
    }

    @RequestMapping("/findAllByTypeAndArea")
    public List<ProductCategoryRegionRels> findAllByTypeAndArea() {
        return productsService.findAllByTypeAndArea();
    }


    @RequestMapping("/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        String fileRoot = "\\\\10.124.102.231\\product-files";
        String imageFile = String.format("%s\\%s", fileRoot, url.substring(url.indexOf("image-files")));
        File file = new File(imageFile);
        if (!file.exists())
            return;

        response.setContentType("application/octet-stream");
        response.addHeader("Content-disposition", "attachment;filename=" + new String(file.getName().getBytes(), "ISO-8859-1"));
        ServletOutputStream outputStream = response.getOutputStream();

        byte[] bytes = new byte[1024];
        InputStream inputStream = new FileInputStream(file);
        while((inputStream.read(bytes) > 0))
            outputStream.write(bytes);

        outputStream.close();
        inputStream.close();
    }


}



