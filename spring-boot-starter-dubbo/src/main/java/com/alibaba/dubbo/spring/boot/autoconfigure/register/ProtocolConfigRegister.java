package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProtocolConfigRegister extends RegisterDubboConfig {

    public ProtocolConfigRegister(BeanFactory listableBeanFactory, DubboProperties dubboProperties) {
        super(listableBeanFactory, dubboProperties);
        this.protocolConfigs=dubboProperties.getProtocols();
        getDefault(protocolConfigs);
    }

    private ProtocolConfig defaultProtocolConfig;

    private List<ProtocolConfig> protocolConfigs;

    @Override
    public void registerDubboConfig() {
        log.debug("注册 ProtocolConfig");
        for (ProtocolConfig protocolConfig : protocolConfigs) {
            beanFactory.registerSingleton(protocolConfig.getId(),protocolConfig);
        }
    }

    private void getDefault(List<ProtocolConfig> protocolConfigs){
        if(protocolConfigs!=null&&protocolConfigs.size()!=0){
            //循环得到默认，如果没有默认，则设置第一个为默认
            for (ProtocolConfig protocolConfig : protocolConfigs) {
                if(protocolConfig.isDefault()!=null&&protocolConfig.isDefault()){
                    defaultProtocolConfig=protocolConfig;
                    return ;
                }
            }
            if(defaultProtocolConfig==null){
                defaultProtocolConfig= protocolConfigs.get(0);
            }
        }else{
            protocolConfigs=new ArrayList<>();
            defaultProtocolConfig=new ProtocolConfig();
            defaultProtocolConfig.setId("dubbo-protocol");
            defaultProtocolConfig.setName("dubbo");
            defaultProtocolConfig.setPort(20881);
            protocolConfigs.add(defaultProtocolConfig);
        }
        defaultProtocolConfig.setDefault(true);
    }
}
