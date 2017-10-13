package com.alibaba.dubbo.spring.boot.actuate;

import com.mvnsearch.spring.boot.dubbo.actuate.DubboEndpoint;
import com.mvnsearch.spring.boot.dubbo.actuate.DubboMetrics;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 当 actuate这个jar包被配置之后才会触发
 * dubbo 的 监控 配置
 * Created by 赵睿 on 2017/6/5.
 */
@Configuration
@ConditionalOnClass(Endpoint.class)
public class DubboActuateConfiguration  {
    @Bean
    public DubboHealthIndicator dubboHealthIndicator() {
        return new DubboHealthIndicator();
    }

    @Bean
    public DubboEndpoint dubboEndpoint() {
        return new DubboEndpoint();
    }

    @Bean
    public DubboMetrics dubboConsumerMetrics() {
        return new DubboMetrics();
    }
}
