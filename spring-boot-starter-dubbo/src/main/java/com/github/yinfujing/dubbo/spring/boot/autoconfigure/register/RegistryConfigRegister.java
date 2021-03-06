package com.github.yinfujing.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.AbstractInterfaceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.github.yinfujing.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RegistryConfigRegister extends RegisterDubboConfig<RegistryConfig> {

    private List<RegistryConfig> defaultRegistryConfigList;

    public RegistryConfigRegister(BeanFactory beanFactory, DubboProperties dubboProperties) {
        super(beanFactory, dubboProperties);
    }

    @Override
    public Class<RegistryConfig> getTClass() {
        return RegistryConfig.class;
    }

    @Override
    void initConfigs() {
        this.configs=dubboProperties.getRegistries();
    }

    @Override
    RegistryConfig getDefaultBySystem() {
        RegistryConfig registryConfig=new RegistryConfig();
        registryConfig.setId("zk");
        registryConfig.setAddress("127.0.0.1:2181");
        registryConfig.setProtocol("zookeeper");
        registryConfig.setServer("dubbo");
        registryConfig.setDefault(true);
        return registryConfig;
    }

    @Override
    public RegistryConfig compareAndMerge(RegistryConfig source, RegistryConfig target) {
        return source;
    }

    private List<RegistryConfig> getDefaultRegistryConfigList(){
        if(defaultRegistryConfigList==null){
            defaultRegistryConfigList=new ArrayList<>();
            for (RegistryConfig config : configs) {
                if(config.isDefault()!=null&&config.isDefault()){
                    defaultRegistryConfigList.add(config);
                }
            }
            if(defaultRegistryConfigList.size()==0){
                defaultRegistryConfigList.add(defaultConfig);
            }
        }
        return defaultRegistryConfigList;
    }

    void initConfig(AbstractInterfaceConfig config) {
        List<RegistryConfig> registries = config.getRegistries();
        if (registries == null || registries.size() == 0) {
            registries=getDefaultRegistryConfigList();
        } else {
            for (int i = 0; i < registries.size(); i++) {
                RegistryConfig registry = merge(registries.get(i));
                registries.set(i, registry);
            }
        }
        config.setRegistries(registries);
    }
}
