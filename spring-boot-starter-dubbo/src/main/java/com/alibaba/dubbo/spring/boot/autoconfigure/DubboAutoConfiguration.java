package com.alibaba.dubbo.spring.boot.autoconfigure;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.config.spring.ServiceBean;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *  这个
 */
@Configuration
@ConditionalOnClass({ReferenceBean.class, ServiceBean.class})
@EnableConfigurationProperties(DubboProperties.class)
@ImportAutoConfiguration({DubboBeanFactory.class})
public class DubboAutoConfiguration{



}
