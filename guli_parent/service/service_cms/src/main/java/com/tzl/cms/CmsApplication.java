package com.tzl.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: guli_parent
 * @description: 服务动类
 * @author: zl
 * @create: 2022-05-12 14:44
 **/
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.tzl"})
public class CmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class);
    }
}
