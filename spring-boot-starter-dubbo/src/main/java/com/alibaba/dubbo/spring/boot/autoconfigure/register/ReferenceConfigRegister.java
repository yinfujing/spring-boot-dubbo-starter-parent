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
        log.debug("注册 {}", getTClass().getCanonicalName());
        if (configs == null) {
            return;
        }
        for (ReferenceConfig config : configs) {
            initConfigId(config);
            initProperties(config);
            beanFactory.registerSingleton(config.getId(), config.get());
        }
    }

    private void initProperties(ReferenceConfig referenceConfig) {
        initApplicationConfig(referenceConfig);
        initRegistryConfig(referenceConfig);
        initConsumerConfig(referenceConfig);
        initModuleConfig(referenceConfig);
        initMonitorConfig(referenceConfig);
    }


    @Autowired
    private ApplicationConfigRegister applicationConfigRegister;
    private void initApplicationConfig(ReferenceConfig referenceConfig) {
        ApplicationConfig applicationConfig = referenceConfig.getApplication();
        referenceConfig.setApplication(applicationConfigRegister.getDefault(applicationConfig));
    }

    @Autowired
    private RegistryConfigRegister registryConfigRegister;
    private void initRegistryConfig(ReferenceConfig referenceConfig) {
        List<RegistryConfig> registries = referenceConfig.getRegistries();
        if (registries == null || registries.size() == 0) {
            referenceConfig.setRegistries(registryConfigRegister.getDefaultRegistryConfigList());
        } else {
            for (int i = 0; i < registries.size(); i++) {
                RegistryConfig registry = registryConfigRegister.merge(registries.get(i));
                registries.set(i, registry);
            }
        }
        referenceConfig.setRegistries(registries);
    }

    @Autowired
    private ConsumerConfigRegister consumerConfigRegister;
    private void initConsumerConfig(ReferenceConfig referenceConfig) {
        ConsumerConfig consumerConfig = referenceConfig.getConsumer();
        referenceConfig.setConsumer(consumerConfigRegister.getDefault(consumerConfig));
    }

    @Autowired
    private ModuleConfigRegister moduleConfigRegister;
    private void initModuleConfig(ReferenceConfig referenceConfig) {
        ModuleConfig moduleConfig = referenceConfig.getModule();
        referenceConfig.setModule(moduleConfigRegister.getDefault(moduleConfig));
    }
    @Autowired
    private MonitorConfigRegister monitorConfigRegister;
    private void initMonitorConfig(ReferenceConfig referenceConfig) {
        MonitorConfig monitorConfig=referenceConfig.getMonitor();
        referenceConfig.setMonitor(monitorConfigRegister.getDefault(monitorConfig));
    }

}
