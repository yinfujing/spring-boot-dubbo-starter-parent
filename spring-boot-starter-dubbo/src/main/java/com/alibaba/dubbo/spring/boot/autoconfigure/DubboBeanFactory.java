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

    private final DubboProperties dubboProperties;

    private Environment environment;

    private List<RegisterDubboConfig> registerDubboConfigs;

    public DubboBeanFactory(DubboProperties dubboProperties, Environment environment) {
        this.dubboProperties = dubboProperties;
        this.environment = environment;

    }

    @PostConstruct
    public void register() {
        log.debug("dubbo 工厂注册 ");
        Assert.notNull(this.dubboProperties, "dubbo 在spring boot 中的配置文件不能为空");
        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;

        Map<String,RegisterDubboConfig> registerDubboConfigMap=listableBeanFactory.getBeansOfType(RegisterDubboConfig.class);
        for (RegisterDubboConfig registerDubboConfig : registerDubboConfigMap.values()) {
            registerDubboConfig.registerDubboConfig();
        }
    }

}
