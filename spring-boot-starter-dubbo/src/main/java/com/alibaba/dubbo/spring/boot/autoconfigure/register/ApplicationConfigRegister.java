package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.AbstractInterfaceConfig;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ApplicationConfigRegister  extends RegisterDubboConfig<ApplicationConfig>{
    public ApplicationConfigRegister(BeanFactory beanFactory, DubboProperties dubboProperties) {
        super(beanFactory, dubboProperties);
    }

    @Autowired
    private Environment environment;

    @Override
    public Class<ApplicationConfig> getTClass() {
        return ApplicationConfig.class;
    }

    @Override
    void initConfigs() {
        configs=dubboProperties.getApplications();
    }

    @Override
    ApplicationConfig getDefaultBySystem() {
        ApplicationConfig config=new ApplicationConfig();
        config.setOwner("rui");
        if(environment.getProperty("info.build") ==null){
            log.warn("没有配置 info.build 中的系统信息 ");
            config.setName("dubbo-spring-boot-start");
            config.setOrganization("com.alibaba.dubbo.spring.boot");
        }else {
            config.setName(environment.getProperty("info.build.artifact"));
            config.setOrganization(environment.getProperty("info.build.groupId"));
        }
        config.setDefault(true);
        return config;
    }

    @Override
    public ApplicationConfig compareAndMerge(ApplicationConfig source, ApplicationConfig target) {
        return target;
    }

    public void initConfig(AbstractInterfaceConfig config) {
        ApplicationConfig applicationConfig = config.getApplication();
        config.setApplication(getDefault(applicationConfig));
    }

}
