package com.github.yinfujing.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.*;
import com.github.yinfujing.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;

@Slf4j
public class ConsumerConfigRegister extends RegisterDubboConfig <ConsumerConfig>{

    public ConsumerConfigRegister(BeanFactory listableBeanFactory, DubboProperties dubboProperties) {
        super(listableBeanFactory, dubboProperties);

    }
    @Override
    public Class<ConsumerConfig> getTClass() {
        return ConsumerConfig.class;
    }
    @Override
    void initConfigs() {
        this.configs=dubboProperties.getConsumers();
    }

    @Override
    ConsumerConfig getDefaultBySystem() {
        ConsumerConfig  defaultConsumerConfig =new ConsumerConfig();
        defaultConsumerConfig.setId("dubbo-consumer");
        defaultConsumerConfig.setTimeout(1000);
        defaultConsumerConfig.setRetries(2);
        defaultConsumerConfig.setDefault(true);
        return defaultConsumerConfig;
    }


    @Override
    public ConsumerConfig compareAndMerge(ConsumerConfig source, ConsumerConfig target) {
        return target;
    }

    void initConfig(ReferenceConfig referenceConfig) {
        ConsumerConfig consumerConfig = referenceConfig.getConsumer();
        referenceConfig.setConsumer(getDefault(consumerConfig));
    }

}
