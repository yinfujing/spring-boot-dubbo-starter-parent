package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.ModuleConfig;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.env.Environment;

import java.util.List;

@Slf4j
public class ModuleConfigRegister extends RegisterDubboConfig<ModuleConfig> {

    public ModuleConfigRegister(BeanFactory beanFactory, DubboProperties dubboProperties) {
        super(beanFactory, dubboProperties);
    }
    @Override
    public Class<ModuleConfig> getTClass() {
        return ModuleConfig.class;
    }
    @Override
    void initConfigs() {
        this.configs=dubboProperties.getModules();
    }

    @Override
    ModuleConfig getDefaultBySystem() {
        return null;
    }

    @Override
    public void registerDubboConfig() {
        log.debug("注册 ModuleConfig");
        log.debug("这个功能暂时还没有写！！！");
    }



    @Override
    public ModuleConfig compareAndMerge(ModuleConfig source, ModuleConfig target) {
        return source;
    }
}
