package com.github.yinfujing.dubbo.spring.boot.actuate;

import com.github.yinfujing.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import com.github.yinfujing.dubbo.spring.boot.demo.DemoServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        ZookeeperHealthIndicator.class
        , DubboAutoConfiguration.class, DemoServiceImpl.class
        , EndpointAutoConfiguration.class
})
@ActiveProfiles({"dubbo-standard", "dubbo-consumer", "dubbo-provider"})
public class ZookeeperHealthIndicatorTest {
    @Autowired
    private ZookeeperHealthIndicator zookeeperHealthIndicator;

    @Test
    public void getZookeeperUrls() throws Exception {
        Health.Builder builder = new Health.Builder();
        zookeeperHealthIndicator.getZookeeperUrls().add("10.1.1.1");
        zookeeperHealthIndicator.doHealthCheck(builder);
        zookeeperHealthIndicator.setConnectionTimeout(10);
        assertEquals("UP {10.1.1.1=Unable to connect to zookeeper server within timeout: 1000, 10.1.1.234:2181=true, 10.1.1.153:2181=true}", builder.build().toString());
    }
}