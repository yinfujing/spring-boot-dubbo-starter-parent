package com.github.yinfujing.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.AbstractInterfaceConfig;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.github.yinfujing.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@Slf4j
public class ApplicationConfigRegister extends RegisterDubboConfig<ApplicationConfig> {
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
        configs = dubboProperties.getApplications();
    }

    @Override
    ApplicationConfig getDefaultBySystem() {
        ApplicationConfig config = new ApplicationConfig();
        config.setOwner("rui");
        try {
            config.setName(environment.getProperty("info.build.artifact"));
            config.setOrganization(environment.getProperty("info.build.groupId"));
        } catch (Exception e) {
            log.warn("没有配置 info.build 中的系统信息 ");
            config.setName("dubbo-spring-boot-start");
            config.setOrganization("com.alibaba.dubbo.spring.boot");
        }
        config.setDefault(true);
        return config;
}

    @Override
    public ApplicationConfig compareAndMerge(ApplicationConfig source, ApplicationConfig target) {
        return target;
    }

    void initConfig(AbstractInterfaceConfig config) {
        ApplicationConfig applicationConfig = config.getApplication();
        config.setApplication(getDefault(applicationConfig));
    }

}
