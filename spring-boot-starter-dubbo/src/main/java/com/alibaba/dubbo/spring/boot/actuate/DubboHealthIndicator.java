package com.alibaba.dubbo.spring.boot.actuate;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.dubbo.spring.boot.autoconfigure.register.ReferenceConfigRegister;
import com.alibaba.dubbo.spring.boot.autoconfigure.register.ServiceConfigRegister;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * dubbo 健康标志
 * Created by 赵睿 on 2017/6/5.
 */
@Component
@Setter
@ConditionalOnClass({ReferenceConfigRegister.class,ServiceConfigRegister.class})
public class DubboHealthIndicator extends AbstractHealthIndicator{
    @Autowired
    private ReferenceConfigRegister referenceConfigRegister;
    @Autowired
    private ServiceConfigRegister serviceConfigRegister;
    @Autowired
    private ApplicationContext applicationContext;

    @SuppressWarnings("unchecked")
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        checkConsumer(builder);
        checkProvider(builder);
    }

    private void checkProvider(Health.Builder builder) {
        List<ServiceConfig> serviceConfigs=serviceConfigRegister.getConfigs();
        if(serviceConfigs!=null&&serviceConfigs.size()!=0){
            for (ServiceConfig serviceConfig : serviceConfigs) {
                String id=serviceConfig.getId();
                if(applicationContext.containsBean(id)){
                    builder.up().withDetail("provider:"+id,serviceConfig.getInterfaceClass().getCanonicalName());
                }else{
                    builder.down().withDetail("provider:"+id,"不在Spring 容器中！！！");
                }
            }
        }
    }

    private void checkConsumer(Health.Builder builder) {
        List<ReferenceConfig> referenceConfigs=referenceConfigRegister.getConfigs();
        if(referenceConfigs!=null&&referenceConfigs.size()!=0){
            for (ReferenceConfig referenceConfig : referenceConfigs) {
                String id=referenceConfig.getId();
                try {
                    EchoService echoService= (EchoService) applicationContext.getBean(id);
                    echoService.$echo("Hello");
                    builder.up().withDetail("consumer:" + id,referenceConfig.getInterfaceClass().getCanonicalName());
                } catch (Exception e) {
                    builder.down().withDetail("consumer:" + id,e.getMessage());
                }
            }
        }
    }
}
