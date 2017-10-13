package com.alibaba.dubbo.spring.boot.autoconfigure;

import com.alibaba.dubbo.spring.boot.autoconfigure.register.RegisterDubboConfig;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Slf4j
public class DubboBeanFactory implements BeanFactoryAware {
    @Setter
    private BeanFactory beanFactory;

    @PostConstruct
    public void register() throws Exception {
        log.debug("dubbo 工厂注册 ");
        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        Map<String,RegisterDubboConfig> registerDubboConfigMap=listableBeanFactory.getBeansOfType(RegisterDubboConfig.class);
        for (RegisterDubboConfig registerDubboConfig : registerDubboConfigMap.values()) {
            registerDubboConfig.registerDubboConfig();
        }
    }
}
