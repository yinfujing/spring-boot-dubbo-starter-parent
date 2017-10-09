package com.alibaba.dubbo.spring.boot.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;

@Configuration
@EnableConfigurationProperties(DubboProperties.class)
public class DubboBeanRegisterPostProcessor implements BeanDefinitionRegistryPostProcessor,PriorityOrdered {
    private final static Logger logger= LoggerFactory.getLogger(DubboBeanRegisterPostProcessor.class);

    @Autowired
    private DubboProperties dubboProperties;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        logger.debug("dubbo 配置属性");


    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        logger.debug("dubbo 服务端需要注册bean 的地址");




//        registry.registerBeanDefinition("application",new DubboBeanDefinitionParser(ApplicationConfig.class,true));

//        List<ApplicationConfig> applicationConfigs=dubboProperties.getApplications();
//        for (ApplicationConfig applicationConfig:applicationConfigs) {
//            AnnotatedGenericBeanDefinition beanDefinition=new AnnotatedGenericBeanDefinition(applicationConfig.getClass());
//
//
//        }

//        registry.registerBeanDefinition(dubboProperties.ge.getName(),new RootBeanDefinition(dubboProperties.getApplication().getClass()));

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
