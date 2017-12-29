package com.github.yinfujing.dubbo.spring.boot.actuate;

import com.github.yinfujing.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import com.github.yinfujing.dubbo.spring.boot.demo.DemoServiceImpl;
import org.junit.jupiter.api.DisplayName;
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
        DubboHealthIndicator.class
        , DubboAutoConfiguration.class, DemoServiceImpl.class
        , EndpointAutoConfiguration.class
})
@ActiveProfiles({"dubbo-standard", "dubbo-consumer", "dubbo-provider"})
@DisplayName("验证dubbo的health节点")
public class DubboHealthIndicatorTest {
    @Autowired
    private DubboHealthIndicator dubboHealthIndicator;

    @Test
    public void check() throws Exception {
        Health.Builder builder = new Health.Builder();
        dubboHealthIndicator.doHealthCheck(builder);
        assertEquals("UP {consumer:demoService=com.alibaba.dubbo.spring.boot.demo.DemoService" +
                        ", provider:com.alibaba.dubbo.spring.boot.demo.DemoService=com.alibaba.dubbo.spring.boot.demo.DemoService}"
                , builder.build().toString());
    }
}