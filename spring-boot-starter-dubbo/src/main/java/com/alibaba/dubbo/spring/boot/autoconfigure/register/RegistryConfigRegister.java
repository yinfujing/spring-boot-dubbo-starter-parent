package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RegistryConfigRegister extends RegisterDubboConfig {
    @Getter
    private RegistryConfig defaultRegistryConfig;
    @Getter
    private List<RegistryConfig> registryConfigs;

    public RegistryConfigRegister(BeanFactory beanFactory, DubboProperties dubboProperties) {
        super(beanFactory, dubboProperties);
        this.registryConfigs=dubboProperties.getRegistries();
        getDefault(registryConfigs);
    }

    @Override
    public void registerDubboConfig() {
        log.debug("注册 RegistryConfig");
        for (RegistryConfig registryConfig : registryConfigs) {
            beanFactory.registerSingleton(registryConfig.getId(),registryConfig);
        }
    }

    private void getDefault(List<RegistryConfig> registryConfigs){
        if(registryConfigs!=null&&registryConfigs.size()!=0){
            //循环得到默认，如果没有默认，则设置第一个为默认
            for (RegistryConfig registryConfig : registryConfigs) {
                if(registryConfig.isDefault()!=null&&registryConfig.isDefault()){
                    defaultRegistryConfig=registryConfig;
                    return ;
                }
            }
            if(defaultRegistryConfig==null){
                defaultRegistryConfig= registryConfigs.get(0);
            }
        }else{
            registryConfigs=new ArrayList<>();
            defaultRegistryConfig=new RegistryConfig();
            defaultRegistryConfig.setId("zk");
            defaultRegistryConfig.setAddress("127.0.0.1:2181");
            defaultRegistryConfig.setProtocol("zookeeper");
            defaultRegistryConfig.setServer("dubbo");
            registryConfigs.add(defaultRegistryConfig);
        }
        defaultRegistryConfig.setDefault(true);
    }
}
