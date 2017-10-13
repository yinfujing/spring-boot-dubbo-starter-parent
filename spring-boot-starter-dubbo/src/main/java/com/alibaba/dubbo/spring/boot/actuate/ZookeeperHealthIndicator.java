package com.alibaba.dubbo.spring.boot.actuate;

import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.spring.boot.autoconfigure.register.RegistryConfigRegister;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
@Setter
public class ZookeeperHealthIndicator  extends AbstractHealthIndicator {
    @Autowired
    private RegistryConfigRegister registryConfigRegister;

    private List<String> zookeeperUrls=new ArrayList<>();

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        List<RegistryConfig> registryConfigs=registryConfigRegister.getConfigs();
        for (RegistryConfig registryConfig : registryConfigs) {
            if(registryConfig.getProtocol().equals("zookeeper")){
                zookeeperUrls.add(registryConfig.getAddress());
            }
        }
    }
}
