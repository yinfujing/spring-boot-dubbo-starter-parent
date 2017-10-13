package com.alibaba.dubbo.spring.boot.autoconfigure.register;


import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboReferenceFactoryBean;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.StringUtils;

import java.util.List;


@Slf4j
public class ReferenceConfigRegister extends RegisterDubboConfig<ReferenceConfig> {
    public ReferenceConfigRegister(BeanFactory listableBeanFactory, DubboProperties dubboProperties) {
        super(listableBeanFactory, dubboProperties);
    }

    @Override
    public Class<ReferenceConfig> getTClass() {
        return ReferenceConfig.class;
    }

    @Override
    void initConfigs() {
        this.configs = dubboProperties.getReferences();
    }

    @Override
    ReferenceConfig getDefaultBySystem() {
        return null;
    }

    @Override
    public ReferenceConfig compareAndMerge(ReferenceConfig source, ReferenceConfig target) {
        return source;
    }

    @lombok.Getter
    @Setter
    private DubboReferenceFactoryBean<?> dubboReferenceFactoryBean= new DubboReferenceFactoryBean<>();

    @Override
    void initConfigId(ReferenceConfig config) {
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

    @Override
    public void registerDubboConfig() throws Exception {
        if (configs == null) {
            return;
        }
        log.debug("注册 {}", getTClass().getCanonicalName());
        for (int i = 0; i < configs.size(); i++) {
            ReferenceConfig config =configs.get(i);
            initConfigId(config);
            config = initProperties(config);
            beanFactory.registerSingleton(this.getTClass().getSimpleName() + "-" + config.getId(), config);

            if (log.isDebugEnabled()) {
                log.debug("创建 DubboReferenceFactoryBean ，id为 {}, and 实现 {} 的 interface"
                        , config.getId(), config.getInterfaceClass());
            }
            registerDubboReferenceDefinition(config);
            configs.set(i, config);
        }
    }

    private void registerDubboReferenceDefinition(ReferenceConfig config) {
        GenericBeanDefinition definition = new GenericBeanDefinition();

        definition.getConstructorArgumentValues().addGenericArgumentValue(config.getInterfaceClass());
        definition.setBeanClass(this.dubboReferenceFactoryBean.getClass());
        definition.getPropertyValues().add("referenceConfig",config);
        definition.getPropertyValues().add("dubboInterface",config.getInterfaceClass());
        definition.setLazyInit(true);
        definition.setScope(BeanDefinition.SCOPE_SINGLETON);
        beanFactory.registerBeanDefinition(config.getId(), definition);
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
    private ConsumerConfigRegister consumerConfigRegister;

    private ReferenceConfig initProperties(ReferenceConfig config) {
        applicationConfigRegister.initConfig(config);
        moduleConfigRegister.initConfig(config);
        registryConfigRegister.initConfig(config);
        monitorConfigRegister.initConfig(config);
        consumerConfigRegister.initConfig(config);
        return config;
    }
}
