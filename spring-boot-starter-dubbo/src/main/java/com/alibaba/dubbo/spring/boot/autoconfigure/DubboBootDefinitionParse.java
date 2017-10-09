package com.alibaba.dubbo.spring.boot.autoconfigure;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import static com.alibaba.dubbo.common.utils.StringUtils.isBlank;
import static com.alibaba.dubbo.common.utils.StringUtils.isNotEmpty;

@Data
@RequiredArgsConstructor
public class DubboBootDefinitionParse {

    private BeanDefinitionRegistry registry;

    public BeanDefinition parse(ApplicationConfig applicationConfig,boolean required){
        GenericBeanDefinition beanDefinition=new GenericBeanDefinition();
        beanDefinition.setBeanClass(ApplicationConfig.class);
        beanDefinition.setLazyInit(false);

        String id=getConfigId(true);
        checkIdAndRegister(beanDefinition, id);

        String beanClass=applicationConfig.getClass().getCanonicalName();
        if(ProtocolConfig.class.equals(beanClass)){
//            parseProtocolConfig(applicationConfig);
        }else if (ProviderConfig.class.equals(beanClass)) {
//            parseNested(element, parserContext, ServiceBean.class, true, "service", "provider", id, beanDefinition);
        } else if (ConsumerConfig.class.equals(beanClass)) {
//            parseNested(element, parserContext, ReferenceBean.class, false, "reference", "consumer", id, beanDefinition);
        }

        //TODO 将需要的属性注入到beanDefinition
        return null;
    }




    /**
     * 解析 {@link ProtocolConfig}
     * <p>
     *     获得所有已经注册的bean ，判断是否需要 protocol 。如果需要，判断是否为这个 ProtocolConfig 的id 。是的话，加入动态引用
     * </p>
     * @param protocolConfig 协议配置
     */
    private void parseProtocolConfig(ProtocolConfig protocolConfig){
        for (String name : registry.getBeanDefinitionNames()) {
            BeanDefinition definition =registry.getBeanDefinition(name);
            PropertyValue property = definition.getPropertyValues().getPropertyValue("protocol");
            if (property != null) {
                Object value = property.getValue();
                if (value instanceof ProtocolConfig && protocolConfig.getId().equals(((ProtocolConfig) value).getName())) {
                    definition.getPropertyValues().addPropertyValue("protocol", new RuntimeBeanReference(protocolConfig.getId()));
                }
            }
        }
    }




    public String getConfigId(boolean required){
        ApplicationConfig applicationConfig=null;
        String id="";
//        String id=applicationConfig.getId();
        if(isBlank(id)&&required){
            String generatedBeanName=applicationConfig.getName();
            if(isBlank(generatedBeanName)){
                //TODO ProtocolConfig 特殊处理

                generatedBeanName=applicationConfig.getClass().getCanonicalName();
            }

            /*
            if (generatedBeanName == null || generatedBeanName.length() == 0) {
        	    if (ProtocolConfig.class.equals(beanClass)) {
        	        generatedBeanName = "dubbo";
        	    } else {
        	        generatedBeanName = element.getAttribute("interface");
        	    }
        	}
        	if (generatedBeanName == null || generatedBeanName.length() == 0) {

             */

            id=generatedBeanName;
            int counter = 2;
            while(registry.containsBeanDefinition(id)){
                id=generatedBeanName+(counter++);
            }
        }
        return null;
    }

    /**
     * 校验id 是否重复，如果重复抛出 {@link IllegalStateException} 异常。如果不重复，注册此 beanDefinition
     */
    private void checkIdAndRegister(GenericBeanDefinition beanDefinition, String id) {
        if(isNotEmpty(id)){
            if(registry.containsBeanDefinition(id)){
                throw new IllegalStateException("Duplicate spring bean id " + id);
            }
            registry.registerBeanDefinition(id,beanDefinition);
            beanDefinition.getPropertyValues().addPropertyValue("id",id);
        }
    }

}
