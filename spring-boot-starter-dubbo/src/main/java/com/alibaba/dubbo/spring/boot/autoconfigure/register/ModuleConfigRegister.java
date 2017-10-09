package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.ModuleConfig;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.env.Environment;

import java.util.List;

@Slf4j
public class ModuleConfigRegister extends RegisterDubboConfig {
    @Getter
    private ModuleConfig defaultModuleConfig;
    @Getter
    private List<ModuleConfig> moduleConfigs;

    public ModuleConfigRegister(BeanFactory beanFactory, DubboProperties dubboProperties) {
        super(beanFactory, dubboProperties);
        this.moduleConfigs=dubboProperties.getModules();
    }

    @Override
    public void registerDubboConfig() {
        log.debug("注册 ModuleConfig");
        log.debug("这个功能暂时还没有写！！！");
    }
}
