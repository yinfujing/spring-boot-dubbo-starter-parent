package com.alibaba.dubbo.spring.boot.autoconfigure;

import com.alibaba.dubbo.spring.boot.autoconfigure.register.RegisterDubboConfig;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class DubboBeanFactory implements BeanPostProcessor {
    @Autowired
    @Setter
    private BeanFactory beanFactory;

    private boolean registerFlag;

    public void register() throws Exception {
        log.debug("dubbo 工厂注册 ");
        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        Map<String,RegisterDubboConfig> registerDubboConfigMap=listableBeanFactory.getBeansOfType(RegisterDubboConfig.class);
        for (RegisterDubboConfig registerDubboConfig : registerDubboConfigMap.values()) {
            registerDubboConfig.registerDubboConfig();
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(!registerFlag){
            try {
//                register();
                registerFlag=true;
            } catch (Exception e) {
                log.error("dubbo 注册失败！",e);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
