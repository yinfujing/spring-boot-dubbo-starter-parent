package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProtocolConfigRegister extends RegisterDubboConfig<ProtocolConfig> {
    public ProtocolConfigRegister(BeanFactory listableBeanFactory, DubboProperties dubboProperties) {
        super(listableBeanFactory, dubboProperties);
    }

    private List<ProtocolConfig> defaultProtocolConfigList;
    @Override
    void initConfigs() {
        this.configs=dubboProperties.getProtocols();
    }

    @Override
    public Class<ProtocolConfig> getTClass() {
        return ProtocolConfig.class;
    }

    @Override
    ProtocolConfig getDefaultBySystem() {
        ProtocolConfig protocolConfig=new ProtocolConfig();
        protocolConfig.setId("dubbo-protocol");
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20881);
        protocolConfig.setDefault(true);
        return protocolConfig;
    }

    @Override
    public ProtocolConfig compareAndMerge(ProtocolConfig source, ProtocolConfig target) {
        return target;
    }

    void initConfig(ServiceConfig config){
        List<ProtocolConfig> protocols = config.getProtocols();
        if (protocols == null || protocols.size() == 0) {
            config.setProtocols(getDefaultConfigList());
        } else {
            for (int i = 0; i < protocols.size(); i++) {
                ProtocolConfig protocolConfig = merge(protocols.get(i));
                protocols.set(i, protocolConfig);
            }
        }
        config.setProtocols(protocols);
    }


    private List<ProtocolConfig> getDefaultConfigList(){
        if(defaultProtocolConfigList==null){
            defaultProtocolConfigList=new ArrayList<>();
            for (ProtocolConfig config : configs) {
                if(config.isDefault()!=null&&config.isDefault()){
                    defaultProtocolConfigList.add(config);
                }
            }
            if(defaultProtocolConfigList.size()==0){
                defaultProtocolConfigList.add(defaultConfig);
            }
        }
        return defaultProtocolConfigList;
    }

}
