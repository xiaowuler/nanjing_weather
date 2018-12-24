package com.nanjing.weather;


import com.nanjing.weather.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(SpringUtil.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        ///////11111
    }
}
