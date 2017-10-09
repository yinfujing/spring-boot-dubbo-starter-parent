package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

public class ReferenceBeanRegister extends RegisterDubboConfig {

    private List<ReferenceBean> referenceBeans;
    @Autowired
    private ApplicationConfigRegister applicationConfigRegister;
    @Autowired
    private RegistryConfigRegister registryConfigRegister;

    public ReferenceBeanRegister(BeanFactory listableBeanFactory, DubboProperties dubboProperties) {
        super(listableBeanFactory, dubboProperties);
        referenceBeans=dubboProperties.getReferences();
    }

    @Override
    public void registerDubboConfig() {
        for (ReferenceBean referenceBean : referenceBeans) {
           beanFactory.registerSingleton(referenceBean.getId(),referenceBean);
        }
    }

    public void init(){
        for (ReferenceBean referenceBean : referenceBeans) {
            initApplicationConfig(referenceBean);

            RegistryConfig registryConfig=referenceBean.getRegistry();
            if(registryConfig==null){
                referenceBean.setRegistry(registryConfigRegister.getDefaultRegistryConfig());
            }else{
//                if()
            }
        }
    }

    private void initApplicationConfig(ReferenceBean referenceBean) {
        ApplicationConfig applicationConfig=referenceBean.getApplication();
        if(applicationConfig==null){
            applicationConfig=applicationConfigRegister.getDefaultApplicationConfig();
        }else {
            if(StringUtils.isEmpty(applicationConfig.getName())){
                applicationConfig=applicationConfigRegister.getApplicationConfigById(applicationConfig.getId());
            }else{
                applicationConfig=applicationConfigRegister.merge(applicationConfig);
            }
        }
        referenceBean.setApplication(applicationConfig);
    }


}
