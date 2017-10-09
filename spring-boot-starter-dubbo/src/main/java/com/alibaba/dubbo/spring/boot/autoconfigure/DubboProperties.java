package com.alibaba.dubbo.spring.boot.autoconfigure;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.alibaba.dubbo.config.spring.schema.DubboNamespaceHandler;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;


/**
 * dubbo的配置文件
 * {@link DubboNamespaceHandler#init()}
 * Created by 赵睿 on 2017/5/16.
 */
@ConfigurationProperties(prefix = "dubbo")
@ConditionalOnClass(ApplicationConfig.class)
@ConditionalOnProperty(name = "",havingValue = "dubbo")
@Data
public class DubboProperties {
    private List<ApplicationConfig> applications;
    private List<ModuleConfig> modules;

    private List<RegistryConfig> registries;
    private List<MonitorConfig> monitors;

    private List<ProtocolConfig> protocols;

    private List<ProviderConfig> providers;
    private List<ServiceBean> services;
    private List<ConsumerConfig> consumers;
    private List<ReferenceBean> references;

    private List<AnnotationBean> annotations;


    @Override
    public String toString() {
        return "DubboProperties{" +
                "applications=" + applications +
                ", modules=" + modules +
                ", registries=" + registries +
                ", monitors=" + monitors +
                ", providers=" + providers +
                ", consumers=" + consumers +
                ", protocols=" + protocols +
                ", services=" + services +
                ", references=" + references +
                ", annotations=" + annotations +
                '}';
    }
}
