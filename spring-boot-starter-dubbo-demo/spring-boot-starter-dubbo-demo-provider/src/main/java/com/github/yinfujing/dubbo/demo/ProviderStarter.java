package com.github.yinfujing.dubbo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * dubbo 提供者 启动类
 * Created by 赵睿 on 2017/6/5.
 */
@SpringBootApplication
public class ProviderStarter {
    public static void main(String[] args) {
        SpringApplication.run(ProviderStarter.class, args);
    }
}
