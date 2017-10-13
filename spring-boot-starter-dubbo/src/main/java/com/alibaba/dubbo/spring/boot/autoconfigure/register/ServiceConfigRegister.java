package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@Slf4j
public class ServiceConfigRegister extends RegisterDubboConfig<ServiceConfig> implements ApplicationListener {
    public ServiceConfigRegister(BeanFactory listableBeanFactory, DubboProperties dubboProperties) {
        super(listableBeanFactory, dubboProperties);
    }

    @Override
    public Class<ServiceConfig> getTClass() {
        return ServiceConfig.class;
    }

    @Override
    void initConfigs() {
        this.configs = dubboProperties.getServices();
    }

    @Override
    ServiceConfig getDefaultBySystem() {
        return null;
    }

    @Override
    public void registerDubboConfig() throws Exception {
        if (configs == null) {
            return;
        }
        log.debug("注册 {}", getTClass().getCanonicalName());

        for (int i = 0; i < configs.size(); i++) {
            initConfigId(configs.get(i));
            ServiceConfig config=initProperties(configs.get(i));
            configs.set(i,config);
            beanFactory.registerSingleton(config.getId(), config);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    void initConfigId(ServiceConfig config) {
        if(config.getId().equals(config.getInterfaceClass().getName())){
            String id=config.getInterfaceClass().getSimpleName();
            id=id.substring(0,1).toLowerCase()+id.substring(1);
            String name=id;
            int counter=2;
            while(beanFactory.containsBean(id)){
                id=name+counter;
                counter++;
            }
            config.setId(id);
        }
    }

    @Autowired
    private ApplicationConfigRegister applicationConfigRegister;
    @Autowired
    private ModuleConfigRegister moduleConfigRegister;
    @Autowired
    private RegistryConfigRegister registryConfigRegister;
    @Autowired
    private MonitorConfigRegister monitorConfigRegister;
    @Autowired
    private ProtocolConfigRegister protocolConfigRegister;

    @SuppressWarnings("unchecked")
    private ServiceConfig initProperties(ServiceConfig config) {
        applicationConfigRegister.initConfig(config);
        moduleConfigRegister.initConfig(config);
        registryConfigRegister.initConfig(config);
        monitorConfigRegister.initConfig(config);
        protocolConfigRegister.initConfig(config);

        if (config.getRef() == null) {
            config.setRef(beanFactory.getBean(config.getInterfaceClass()));
        } else {
            String name = config.getRef().toString();
            if (beanFactory.containsBean(name)) {
                config.setRef(beanFactory.getBean(name));
                return config;
            }
            try {
                Class beanClass = Class.forName(name);
                config.setRef(beanFactory.getBean(beanClass));
            } catch (ClassNotFoundException e) {
                log.error("无法获得实际实现 ,配置为"+config,e);
            }
        }
        return config;
    }

    @Override
    public ServiceConfig compareAndMerge(ServiceConfig source, ServiceConfig target) {
        return null;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (ContextRefreshedEvent.class.getName().equals(event.getClass().getName())) {
            if(configs==null||configs.size()==0){
                return ;
            }
            for (ServiceConfig config : configs) {
                if (isDelay(config) && !config.isExported() && !config.isUnexported()) {
                    if (log.isInfoEnabled()) {
                        log.info("服务 {} 开始提供服务!",config.getId());
                    }
                    config.export();
                }
            }
        }
    }

    private boolean isDelay(ServiceConfig serviceConfig) {
        Integer delay = serviceConfig.getDelay();
        ProviderConfig provider = serviceConfig.getProvider();
        if (delay == null && provider != null) {
            delay = provider.getDelay();
        }
        return delay == null || delay == -1;
    }
}
