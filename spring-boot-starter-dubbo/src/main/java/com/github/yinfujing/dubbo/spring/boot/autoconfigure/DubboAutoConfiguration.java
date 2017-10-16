package com.github.yinfujing.dubbo.spring.boot.autoconfigure;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.github.yinfujing.dubbo.spring.boot.autoconfigure.register.*;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 *  这个
 */
@Configuration
@ConditionalOnClass({ReferenceConfig.class, ReferenceConfig.class})
@EnableConfigurationProperties(DubboProperties.class)
@Setter
@Order(HIGHEST_PRECEDENCE)
public class DubboAutoConfiguration implements BeanFactoryAware {
    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private DubboProperties dubboProperties;


    @Bean
    ApplicationConfigRegister applicationConfigRegister(){
        return new ApplicationConfigRegister(beanFactory,dubboProperties);
    }

    @Bean
    ModuleConfigRegister moduleConfigRegister(){
        return new ModuleConfigRegister(beanFactory,dubboProperties);
    }

    @Bean
    RegistryConfigRegister registryConfigRegister(){
        return new RegistryConfigRegister(beanFactory,dubboProperties);
    }

    @Bean
    ProtocolConfigRegister protocolConfigRegister(){
        return new ProtocolConfigRegister(beanFactory,dubboProperties);
    }

    @Bean
    ConsumerConfigRegister consumerConfigRegister(){
        return new ConsumerConfigRegister(beanFactory,dubboProperties);
    }

    @Bean
    ReferenceConfigRegister referenceConfigRegister(){
        return new ReferenceConfigRegister(beanFactory,dubboProperties);
    }

    @Bean
    ServiceConfigRegister serviceConfigRegister(){
        return new ServiceConfigRegister(beanFactory,dubboProperties);
    }

    @Bean
    MonitorConfigRegister monitorConfigRegister(){
        return new MonitorConfigRegister(beanFactory,dubboProperties);
    }
}
