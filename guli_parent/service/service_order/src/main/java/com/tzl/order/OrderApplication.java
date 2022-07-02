package com.tzl.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: guli_parent
 * @description: 订单模块启动类
 * @author: zl
 * @create: 2022-05-31 17:38
 **/
@EnableFeignClients(basePackages = "com.tzl")
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.tzl"})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
