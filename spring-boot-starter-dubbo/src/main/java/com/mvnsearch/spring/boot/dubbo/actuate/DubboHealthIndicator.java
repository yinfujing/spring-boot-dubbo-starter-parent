package com.mvnsearch.spring.boot.dubbo.actuate;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.mvnsearch.spring.boot.dubbo.listener.ConsumerSubscribeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.context.ApplicationContext;

/**
 * dubbo 健康标志
 * Created by 赵睿 on 2017/6/5.
 */
public class DubboHealthIndicator extends AbstractHealthIndicator{

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {

        //TODO 对消费者的记录
        if(!ConsumerSubscribeListener.subscribedInterfaces.isEmpty()){
            for (Class clazz : ConsumerSubscribeListener.subscribedInterfaces) {
                EchoService echoService = (EchoService) applicationContext.getBean(clazz);
                echoService.$echo("Hello");
                builder.withDetail(clazz.getCanonicalName(), true);
            }
        }

        //TODO 对提供者的健康检测！
        //TODO zk信息！
        builder.up();

    }

}
