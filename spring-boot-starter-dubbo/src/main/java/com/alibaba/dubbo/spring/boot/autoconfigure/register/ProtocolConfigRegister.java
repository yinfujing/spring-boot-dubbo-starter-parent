package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;

@Slf4j
public class ProtocolConfigRegister extends RegisterDubboConfig<ProtocolConfig> {
    public ProtocolConfigRegister(BeanFactory listableBeanFactory, DubboProperties dubboProperties) {
        super(listableBeanFactory, dubboProperties);
    }

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
        return source;
    }

}
