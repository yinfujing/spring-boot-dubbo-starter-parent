package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ConsumerConfigRegister extends RegisterDubboConfig {
    @Getter
    private ConsumerConfig defaultConsumerConfig;
    @Getter
    private List<ConsumerConfig> consumerConfigs;

    public ConsumerConfigRegister(BeanFactory listableBeanFactory, DubboProperties dubboProperties) {
        super(listableBeanFactory, dubboProperties);
        this.consumerConfigs=dubboProperties.getConsumers();

    }

    @Override
    public void registerDubboConfig() {
        for (ConsumerConfig consumerConfig : consumerConfigs) {
            beanFactory.registerSingleton(consumerConfig.getId(),consumerConfig);
        }
    }

    private void getDefault(List<ConsumerConfig> consumerConfigs){
        if(consumerConfigs!=null&&consumerConfigs.size()!=0){
            //循环得到默认，如果没有默认，则设置第一个为默认
            for (ConsumerConfig consumerConfig : consumerConfigs) {
                if(consumerConfig.isDefault()!=null&&consumerConfig.isDefault()){
                    defaultConsumerConfig =consumerConfig;
                    return ;
                }
            }
            if(defaultConsumerConfig ==null){
                defaultConsumerConfig = consumerConfigs.get(0);
            }
        }else{
            consumerConfigs=new ArrayList<>();
            defaultConsumerConfig =new ConsumerConfig();
            defaultConsumerConfig.setId("dubbo-consumer");
            defaultConsumerConfig.setTimeout(1000);
            defaultConsumerConfig.setRetries(2);
            consumerConfigs.add(defaultConsumerConfig);
        }
        defaultConsumerConfig.setDefault(true);
    }
}
