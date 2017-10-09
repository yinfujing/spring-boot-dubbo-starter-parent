package com.alibaba.dubbo.spring.boot.condition;

import com.alibaba.dubbo.config.spring.ServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 当 dubbo.serviceEnable 配置为true 才会触发
 * <p>
 * dubbo 服务提供者 自动注册配置
 */
@Configuration
@AutoConfigureAfter(DubboAutoConfiguration.class)
@ConditionalOnProperty(prefix = "dubbo", name = "serviceEnable", havingValue = "true")
public class DubboServiceAutoConfiguration {
    @Autowired
    private ApplicationContext applicationContext;
//    @Autowired
//    private DubboProperties dubboProperties;
//    @Autowired
//    private ApplicationConfig applicationConfig;
//    @Autowired
//    private ProviderConfig providerConfig;
//    @Autowired
//    private RegistryConfig registryConfig;

    private Map<String, ServiceBean> serviceConfigMap = new HashMap<>();

    public Map<String, ServiceBean> getServiceConfigMap() {
        return serviceConfigMap;
    }

    @PostConstruct
    public void init() throws Exception {
//        getServiceConfigByProfile();
//        getServiceConfigByAnnotation();
//        for (ServiceBean serviceBean : serviceConfigMap.values()) {
//            serviceBean.export();
//        }
    }

//    /**
//     * 配置文件优于注解
//     */
//    private void getServiceConfigByProfile() throws Exception {
//        ServiceBean[] service = dubboProperties.getService();
//        for (ServiceBean serviceConfig : service) {
//            serviceConfig.setApplication(applicationConfig);
//            serviceConfig.setRegistry(registryConfig);
//            setProtocols(serviceConfig);
//            serviceConfig.setProvider(providerConfig);
//            serviceConfig.setRef(applicationContext.getBean((String) serviceConfig.getRef()));
//            serviceConfig.afterPropertiesSet();
//            serviceConfigMap.put(serviceConfig.getInterface(), serviceConfig);
//        }
//    }
//
//    private void getServiceConfigByAnnotation() throws Exception {
//        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Service.class);
//        for (Map.Entry<String, Object> entry : beans.entrySet()) {
//            Service service = applicationContext.findAnnotationOnBean(entry.getKey(), Service.class);
//            ServiceBean serviceConfig = serviceConfigMap.get(entry.getValue().getClass().getInterfaces()[0].getCanonicalName());
//            if (serviceConfig == null) {
//                serviceConfig = onlyAnnotationServiceConfig(entry, service);
//                serviceConfig.afterPropertiesSet();
//            } else {
//                serviceConfig = getServiceBeanFromAnnotationWhereProfileAlreadyHave(entry, service, serviceConfig);
//            }
//            serviceConfigMap.put(serviceConfig.getInterface(), serviceConfig);
//        }
//    }
//
//    private ServiceBean getServiceBeanFromAnnotationWhereProfileAlreadyHave(Map.Entry<String, Object> entry, Service service, ServiceBean serviceConfig) {
//        ServiceBean serviceConfigNew = onlyAnnotationServiceConfig(entry, service);
//        if (serviceConfig.getProtocols() != null) {
//            serviceConfigNew.setProtocols(serviceConfig.getProtocols());
//        }
//        if (serviceConfig.getRef() != null) {
//            serviceConfigNew.setRef(serviceConfig.getRef());
//        }
//        if (serviceConfig.getProvider() != null) {
//            serviceConfigNew.setProvider(serviceConfig.getProvider());
//        }
//        if (serviceConfig.getInterface() != null) {
//            serviceConfigNew.setInterface(serviceConfig.getInterfaceClass());
//        }
//        if (serviceConfig.getConnections() != null) {
//            serviceConfigNew.setConnections(serviceConfig.getConnections());
//        }
//        if (serviceConfig.getTimeout() != null) {
//            serviceConfigNew.setTimeout(serviceConfig.getTimeout());
//        }
//        serviceConfig=serviceConfigNew;
//        return serviceConfig;
//    }
//
//
//    /**
//     * 读取注解信息，配置属性
//     */
//    private ServiceBean onlyAnnotationServiceConfig(Map.Entry<String, Object> entry, Service service) {
//        ServiceBean serviceConfig = new ServiceBean(service);
//        serviceConfig.setApplication(applicationConfig);
//        serviceConfig.setRegistry(registryConfig);
//        parseAnnotationProtocol(service, serviceConfig);
//        serviceConfig.setInterface(entry.getValue().getClass().getInterfaces()[0]);
//        serviceConfig.setRef(entry.getValue());
//        return serviceConfig;
//    }
//
//    private void parseAnnotationProtocol(Service service, ServiceBean serviceConfig) {
//        String[] protocol = service.protocol();
//        if (!ArrayUtils.isEmpty(protocol)) {
//            for (String aProtocol : protocol) {
//                ProtocolConfig protocolConfig;
//                if (aProtocol.contains(":")) {
//                    String[] params = aProtocol.split(":");
//                    protocolConfig = new ProtocolConfig(params[0], Integer.parseInt(params[1]));
//                } else {
//                    protocolConfig = new ProtocolConfig(aProtocol);
//                }
//                serviceConfig.setProtocol(protocolConfig);
//            }
//        } else {
//            setProtocols(serviceConfig);
//        }
//    }


//    /**
//     * 如果  serviceConfig 中没有关于协议的配置，使用默认的配置
//     */
//    private void setProtocols(ServiceBean serviceConfig) {
//        if (serviceConfig.getProtocols() == null) {
//            serviceConfig.setProtocols(providerConfig.getProtocols());
//        }
//    }
}
