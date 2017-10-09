package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.env.Environment;

public abstract class RegisterDubboConfig  {

    final DefaultListableBeanFactory beanFactory;
    final DubboProperties dubboProperties;

    public RegisterDubboConfig(BeanFactory listableBeanFactory, DubboProperties dubboProperties) {
        this.beanFactory = (DefaultListableBeanFactory) listableBeanFactory;
        this.dubboProperties = dubboProperties;
    }



    public abstract void registerDubboConfig();
}
