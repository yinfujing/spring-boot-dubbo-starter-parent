package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class ReferenceBeanRegister extends RegisterDubboConfig<ReferenceBean> {
    public ReferenceBeanRegister(BeanFactory listableBeanFactory, DubboProperties dubboProperties) {
        super(listableBeanFactory, dubboProperties);
    }

    @Override
    public Class<ReferenceBean> getTClass() {
        return ReferenceBean.class;
    }


    @Override
    void initConfigs() {
        this.configs=dubboProperties.getReferences();
        init();
    }

    @Override
    ReferenceBean getDefaultBySystem() {
        return null;
    }


    @Override
    public ReferenceBean compareAndMerge(ReferenceBean source, ReferenceBean target) {
        return source;
    }


    @Autowired
    private ApplicationConfigRegister applicationConfigRegister;
    @Autowired
    private RegistryConfigRegister registryConfigRegister;


    public void init(){
        for (ReferenceBean referenceBean : configs) {
            initApplicationConfig(referenceBean);

            //? 多注册中心可能有麻烦
            RegistryConfig registryConfig=referenceBean.getRegistry();
            if(registryConfig==null){
                referenceBean.setRegistry(registryConfigRegister.getDefault());
            }else{
                if(StringUtils.isEmpty(registryConfig.getAddress())){
                    registryConfig=registryConfigRegister.getAbstractConfigById(registryConfig.getId());
                }else{
                    registryConfig=registryConfigRegister.merge(registryConfig);
                }
            }
            referenceBean.setRegistry(registryConfig);
        }
    }

    private void initApplicationConfig(ReferenceBean referenceBean) {
        ApplicationConfig applicationConfig=referenceBean.getApplication();
        if(applicationConfig==null){
            applicationConfig=applicationConfigRegister.getDefault();
        }else {
            if(StringUtils.isEmpty(applicationConfig.getName())){
                applicationConfig=applicationConfigRegister.getAbstractConfigById(applicationConfig.getId());
            }else{
                applicationConfig=applicationConfigRegister.merge(applicationConfig);
            }
        }
        referenceBean.setApplication(applicationConfig);
    }
}
