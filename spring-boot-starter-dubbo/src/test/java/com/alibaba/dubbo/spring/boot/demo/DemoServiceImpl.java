package com.alibaba.dubbo.spring.boot.demo;

import org.springframework.stereotype.Service;

/**
 * 实现接口
 * Created by 赵睿 on 2017/4/5.
 */
@Service
public class DemoServiceImpl implements DemoService {

    static {
        System.out.println("DemoServiceImpl被类加载器加载,类加载器为"
                +DemoServiceImpl.class.getClassLoader().toString());
    }

    private String name;

    public void setName(String name) {
        System.out.println("属性注入");
        this.name = name;
    }

    public void sayHello() {
        System.out.println("HelloWorld");
    }

    public DemoServiceImpl() {
        System.out.println("无参构造器初始化");
    }

    public DemoServiceImpl(String name){
        System.out.println("有参构造器初始化");
        this.name=name;
    }
}
