package com.card.operation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by duyuxiang on 2017/12/8.
 */
@ComponentScan(basePackages = "com.card.operation.*")
@MapperScan("com.card.operation.mapper")
@SpringBootApplication
public class GsCardApplication extends SpringBootServletInitializer {

    public static void main(String args[]){
        SpringApplication.run(GsCardApplication.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GsCardApplication.class);
    }
}
