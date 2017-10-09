package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ApplicationConfigRegister  extends RegisterDubboConfig{

    public ApplicationConfigRegister(BeanFactory beanFactory, DubboProperties dubboProperties) {
        super(beanFactory, dubboProperties);
        this.applicationConfigs=dubboProperties.getApplications();
        getDefault(applicationConfigs);
    }

    @Autowired
    private Environment environment;
    @Getter
    public ApplicationConfig defaultApplicationConfig;
    @Getter
    private List<ApplicationConfig> applicationConfigs;

    @Override
    public void registerDubboConfig() {
        log.debug("注册 ApplicationConfig");
        for (ApplicationConfig applicationConfig : applicationConfigs) {
            beanFactory.registerSingleton(applicationConfig.getId(),applicationConfig);
        }
    }

    private void getDefault(List<ApplicationConfig> applications){
        if(applications!=null&&applications.size()!=0){
            //循环得到默认，如果没有默认，则设置第一个为默认
            for (ApplicationConfig application : applications) {
                if(application.isDefault()!=null&&application.isDefault()){
                    defaultApplicationConfig=application;
                    return ;
                }
            }
            if(defaultApplicationConfig==null){
                defaultApplicationConfig= applications.get(0);
            }
        }else{
            applicationConfigs=new ArrayList<>();
            defaultApplicationConfig=new ApplicationConfig();
            defaultApplicationConfig.setOwner("rui");
            if(environment.getProperty("info.build") ==null){
                log.warn("没有配置 info.build 中的系统信息 ");
                defaultApplicationConfig.setName("dubbo-spring-boot-start");
                defaultApplicationConfig.setOrganization("com.alibaba.dubbo.spring.boot");
            }else {
                defaultApplicationConfig.setName(environment.getProperty("info.build.artifact"));
                defaultApplicationConfig.setOrganization(environment.getProperty("info.build.groupId"));
            }
            applicationConfigs.add(defaultApplicationConfig);
        }
        defaultApplicationConfig.setDefault(true);
    }

    public ApplicationConfig getApplicationConfigById(String id){
        ApplicationConfig applicationConfig = null;

        if(StringUtils.isEmpty(id)){
            applicationConfig= defaultApplicationConfig;
        }else{
            for (ApplicationConfig config : applicationConfigs) {
                if(id.equals(config.getId())){
                   applicationConfig=config;
                   break;
                }
            }
        }
        if(applicationConfig==null){
            throw new BeanDefinitionStoreException("此 ApplicationConfig 的id 没有配置，需要的bean的id为{}",id);
        }

        return applicationConfig;
    }

    public ApplicationConfig merge(ApplicationConfig applicationConfig) {
        ApplicationConfig mergeConfig= null;
        try {
            mergeConfig = getApplicationConfigById(applicationConfig.getId());
            if(mergeConfig.getName().equals(applicationConfig.getName())){
                //当前没有需要做merge的操作。
                mergeConfig= applicationConfig;
            }
        } catch (BeanDefinitionStoreException e) {
            applicationConfigs.add(mergeConfig);
        }
        return mergeConfig;

    }
}
