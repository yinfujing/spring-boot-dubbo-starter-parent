package com.alibaba.dubbo.spring.boot.actuate;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.mvnsearch.spring.boot.dubbo.listener.ConsumerSubscribeListener;
import com.mvnsearch.spring.boot.dubbo.listener.ProviderExportListener;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * dubbo 健康标志
 * Created by 赵睿 on 2017/6/5.
 */
@Component
@Setter
public class DubboHealthIndicator extends AbstractHealthIndicator{

    @Autowired
    private ApplicationContext applicationContext;

    @SuppressWarnings("unchecked")
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        if(!ConsumerSubscribeListener.subscribedInterfaces.isEmpty()){
            for (Class clazz : ConsumerSubscribeListener.subscribedInterfaces) {
                EchoService echoService = (EchoService) applicationContext.getBean(clazz);
                echoService.$echo("Hello");
                builder.withDetail("consumer:"+clazz.getCanonicalName(), true);
            }
            builder.withDetail("consumer",true);
        }

        if(!ProviderExportListener.exportedInterfaces.isEmpty()){
            for (Class clazz : ProviderExportListener.exportedInterfaces) {
                EchoService echoService = (EchoService) applicationContext.getBean(clazz);
                echoService.$echo("Hello");
                builder.withDetail("provider:"+clazz.getCanonicalName(), true);
            }
            builder.withDetail("provider",true);
        }
        builder.up();
    }

}
