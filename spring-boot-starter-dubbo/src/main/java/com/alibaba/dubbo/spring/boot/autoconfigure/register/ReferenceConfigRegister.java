package com.alibaba.dubbo.spring.boot.autoconfigure.register;


import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Slf4j
public class ReferenceConfigRegister extends RegisterDubboConfig<ReferenceConfig> {
    public ReferenceConfigRegister(BeanFactory listableBeanFactory, DubboProperties dubboProperties) {
        super(listableBeanFactory, dubboProperties);
    }

    @Override
    public Class<ReferenceConfig> getTClass() {
        return ReferenceConfig.class;
    }


    @Override
    void initConfigs() {
        this.configs = dubboProperties.getReferences();
    }

    @Override
    ReferenceConfig getDefaultBySystem() {
        return null;
    }

    @Override
    public ReferenceConfig compareAndMerge(ReferenceConfig source, ReferenceConfig target) {
        return source;
    }

    @Override
    public void registerDubboConfig() throws Exception {
        if (configs == null) {
            return;
        }
        log.debug("注册 {}", getTClass().getCanonicalName());
        for (ReferenceConfig config : configs) {
            initConfigId(config);
            initProperties(config);
            beanFactory.registerSingleton(config.getId(), config.get());
        }
    }
    @Autowired
    private ApplicationConfigRegister applicationConfigRegister;
    @Autowired
    private ModuleConfigRegister moduleConfigRegister;
    @Autowired
    private RegistryConfigRegister registryConfigRegister;
    @Autowired
    private MonitorConfigRegister monitorConfigRegister;

    @Autowired
    private ConsumerConfigRegister consumerConfigRegister;

    private void initProperties(ReferenceConfig config) {
        applicationConfigRegister.initConfig(config);
        moduleConfigRegister.initConfig(config);
        registryConfigRegister.initConfig(config);
        monitorConfigRegister.initConfig(config);
        consumerConfigRegister.initConfig(config);
    }
}
