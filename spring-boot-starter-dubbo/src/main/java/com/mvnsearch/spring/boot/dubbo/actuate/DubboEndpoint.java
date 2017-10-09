package com.mvnsearch.spring.boot.dubbo.actuate;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import com.alibaba.dubbo.spring.boot.condition.DubboServiceAutoConfiguration;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * dubbo 终端
 * Created by 赵睿 on 2017/6/5.
 */
@Component
public class DubboEndpoint extends AbstractEndpoint {
    @Autowired
    private DubboProperties dubboProperties;
    @Autowired
    private ApplicationContext applicationContext;

    //TODO 只能使用中间数据
//    @Autowired
    private DubboServiceAutoConfiguration dubboServiceAutoConfiguration;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private RegistryConfig registryConfig;
    @Autowired
    private ProviderConfig providerConfig;

    public DubboEndpoint() {
        super("dubbo", false, true);
    }

    @Override
    public Object invoke() {
        Map<String, Object> info=new HashMap<>();

        info.put("applicationConfig",applicationConfig.toString());
        info.put("registryConfig",registryConfig.toString());

        Map<String,String> providerConfigMap =new HashMap<>();
        providerConfigMap.put("dubbo:provider", providerConfig.toString());
        providerConfigMap.put("dubbo:provider:protocols",ArrayUtils.toString(providerConfig.getProtocols()));
        info.put("providerConfig", providerConfigMap);

        getServiceConfigurationInformation(info);
        getConsumerConfigurationInformation(info);

        return info;
    }

    private void getServiceConfigurationInformation(Map<String, Object> info) {
//        if(dubboProperties.isServiceEnable()){
            info.put("server",true);
            Map<String,Object> serviceConfig=new HashMap<>();
            for (Map.Entry<String,ServiceBean> entry: dubboServiceAutoConfiguration.getServiceConfigMap().entrySet()) {
                Map<String,String> serviceMap=new HashMap<>();
                serviceMap.put("dubbo:service",entry.getValue().toString());
                serviceMap.put("dubbo:protocols", ArrayUtils.toString(entry.getValue().getProtocols()));
                serviceMap.put("methods",ArrayUtils.toString(entry.getValue().getInterfaceClass().getMethods()));
                serviceConfig.put(entry.getKey(),serviceMap);
            }
            info.put("serverConfig",serviceConfig);
//        }else{
//            info.put("server",false);
//        }
    }

    private void getConsumerConfigurationInformation(Map<String, Object> info) {
//        //TODO 消费者配置
//        if(dubboProperties.isConsumerEnable()){
            info.put("consumer",true);
//        }else{
//            info.put("consumer",false);
//        }
    }


}
