package com.alibaba.dubbo.spring.boot.condition;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;

/**
 * dubbo 自动配置类
 * Created by 赵睿 on 2017/6/5.
 */
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableConfigurationProperties({DubboProperties.class})
public class DubboAutoConfiguration {
    @Autowired
    private DubboProperties dubboProperties;
    @Autowired
    private Environment env;

    @Bean
    @ConditionalOnMissingBean
    ApplicationConfig applicationConfig() {
//        ApplicationConfig applicationConfig = dubboProperties.getApplication();
//        if (applicationConfig == null) {
//            String name=env.getProperty("spring.application.name");
//            if(StringUtils.isBlank(name)){
//                name="dxhy.project";
//            }
//            applicationConfig = new ApplicationConfig(name);
//            applicationConfig.setOwner("dxhy");
//        }
//        return applicationConfig;
        return null;
    }

    @Bean
    @ConditionalOnMissingBean
    public RegistryConfig dubboRegistryConfig() {
//        RegistryConfig registryConfig = dubboProperties.getRegistry();
//        registryConfig.setCheck(true);
//        return registryConfig;
        return null;
    }

    @Bean
    @ConditionalOnMissingBean
    public ProviderConfig dubboProtocolConfig() {
//        ProviderConfig providerConfig= dubboProperties.getProvider();
//        if(providerConfig==null){
//            providerConfig=new ProviderConfig();
//        }
//        return  providerConfig;
        return null;
    }

    //TODO MonitorConfig

//    @Bean
//    @ConditionalOnMissingBean
//    @ConditionalOnProperty(prefix = "spring.dubbo", name = "monitor")
//    public MonitorConfig dubboMonitorConfig() {
//        MonitorConfig monitorConfig = new MonitorConfig();
//        monitorConfig.setAddress(properties.getMonitor());
//        return monitorConfig;
//    }


}
