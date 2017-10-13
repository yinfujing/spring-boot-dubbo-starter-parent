package com.alibaba.dubbo.spring.boot.actuate;

import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.spring.boot.autoconfigure.register.RegistryConfigRegister;
import lombok.Getter;
import lombok.Setter;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Setter
@ConditionalOnClass({ZkClient.class})
public class ZookeeperHealthIndicator extends AbstractHealthIndicator {
    @Autowired
    private RegistryConfigRegister registryConfigRegister;

    private final String protocol="zookeeper";
    private int connectionTimeout =1000;

    @Getter
    private List<String> zookeeperUrls = new ArrayList<>();

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        initZkUrls();
        checkZkConnection(builder);
        builder.up();
    }

    private void checkZkConnection(Health.Builder builder) {
        for (String zookeeperUrl : zookeeperUrls) {
            try {
               new ZkClient(zookeeperUrl,connectionTimeout);
               builder.up().withDetail(zookeeperUrl,true);
            } catch (ZkTimeoutException e) {
                builder.down().withDetail(zookeeperUrl,e.getMessage());
            }
        }
    }

    private void initZkUrls() {
        List<RegistryConfig> registryConfigs = registryConfigRegister.getConfigs();
        for (RegistryConfig registryConfig : registryConfigs) {
            if (registryConfig.getProtocol().equals(protocol)) {
                zookeeperUrls.add(registryConfig.getAddress());
            }
        }
    }
}
