package com.alibaba.dubbo.spring.boot.condition;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 当dubbo.consumerEnable配置为true 才会触发
 * dubbo消费者配置
 */
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@AutoConfigureAfter(DubboAutoConfiguration.class)
@Configuration
@ConditionalOnProperty(prefix = "dubbo", name = "consumerEnable", havingValue = "true")
public class DubboConsumerAutoConfiguration {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DubboProperties dubboProperties;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private RegistryConfig registryConfig;

    private Map<String, ReferenceBean> referenceConfigMap = new HashMap<>();
    private Map<String, Object> referenceBeanMap = new HashMap<>();

    public Map<String, ReferenceBean> getReferenceConfigMap() {
        return referenceConfigMap;
    }

    public Map<String, Object> getReferenceBeanMap() {
        return referenceBeanMap;
    }

    @PostConstruct
    public void init() throws Exception {
        getReferenceConfigByProfile();

        //TODO 这个之后再做哇，实在是比较麻烦啊！
        DefaultListableBeanFactory listableBeanFactory= (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();

        String[] beanNameClasses=listableBeanFactory.getBeanDefinitionNames();
        System.out.println(ArrayUtils.toString(beanNameClasses));
        for (String beanNameClass:beanNameClasses) {
            BeanDefinition beanDefinition= listableBeanFactory.getBeanDefinition(beanNameClass);

            if(beanDefinition instanceof AnnotatedBeanDefinition){
                AnnotatedBeanDefinition annotatedBeanDefinition= (AnnotatedBeanDefinition) beanDefinition;

                Class clazz= Class.forName(annotatedBeanDefinition.getMetadata().getClassName());
                if(beanNameClass.equalsIgnoreCase("consumerStarter")){
                    System.out.println(clazz);
                    System.out.println(ArrayUtils.toString(clazz.getFields()));
                    System.out.println(ArrayUtils.toString(clazz.getDeclaredFields()));
                }
                for (Field field : clazz.getDeclaredFields()) {
                    Class type = field.getType();
                    String id = type.getCanonicalName();

                    Reference reference = field.getAnnotation(Reference.class);
                    if (reference != null) {
                        if (!referenceBeanMap.containsKey(id)) {
                            try {
                                initReferenceByAnnotation(reference, id,type);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            listableBeanFactory.registerSingleton(id,referenceBeanMap.get(id));
                        } else {
                            throw new BeanCreationException("不允许存在两个或两个以上的注解在同一个需要被引用的dubbo接口服务;该类为：" + clazz.getCanonicalName()
                                    +"   被标注的注解接口类为"+type);
                        }
                    }
                }
            }

        }
    }

    private void getReferenceConfigByProfile() throws Exception {
        ReferenceBean[] references = dubboProperties.getReferences().toArray(new ReferenceBean[0]);
//        ReferenceBean[] references = dubboProperties.getConsumer();
        for (ReferenceBean referenceBean : references) {
            referenceBean.setApplication(applicationConfig);
            referenceBean.setRegistry(registryConfig);
//            dubboConsumerBeanPostProcessor.setConsumerConfig(referenceBean);
            referenceBean.afterPropertiesSet();
            referenceConfigMap.put(referenceBean.getInterface(), referenceBean);
        }
    }


    private ReferenceBean onlyAnnotationReferenceConfig(Reference reference,Class<?> t) {
        ReferenceBean referenceBean = new ReferenceBean(reference);
        referenceBean.setApplication(applicationConfig);
        referenceBean.setRegistry(registryConfig);
        referenceBean.setInterface(t);
        setConsumerConfig(referenceBean);
        return referenceBean;
    }

    public void setConsumerConfig(ReferenceBean referenceBean) {
        if (referenceBean.getConsumer() == null) {
            referenceBean.setConsumer((ConsumerConfig) dubboProperties.getConsumers());
        }

    }

    private void initReferenceByAnnotation(Reference reference, String id, Class<?> t) {
        try {
            ReferenceBean referenceBean = referenceConfigMap.get(id);
            if (referenceBean == null) {
                referenceBean = onlyAnnotationReferenceConfig(reference, t);
            } else {
                ReferenceBean referenceBeanNew = onlyAnnotationReferenceConfig(reference,t);
                if (referenceBean.getConsumer() != null) {
                    referenceBeanNew.setConsumer(referenceBean.getConsumer());
                }
                if (StringUtils.isNotBlank(referenceBean.getInterface())) {
                    referenceBeanNew.setInterface(referenceBean.getInterface());
                }
                if (StringUtils.isNotBlank(referenceBean.getProtocol())) {
                    referenceBeanNew.setProtocol(referenceBean.getProtocol());
                }
                if(StringUtils.isNotBlank(referenceBean.getInterface())){
                    referenceBeanNew.setInterface(t);
                }
                referenceBean = referenceBeanNew;
            }
            referenceBean.afterPropertiesSet();
            referenceConfigMap.put(id, referenceBean);

            Object o=referenceBean.get();
            if(o==null){
                throw new BeanCreationException(id, referenceBean.toString());
            }
            referenceBeanMap.put(id,o);
        } catch (Exception e) {
            System.out.println(e);
            throw new BeanCreationException(id, e);
        }
    }


    @Bean
    BeanPostProcessor DubboConsumerBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                Class clazz = bean.getClass();
                if(clazz.getCanonicalName().contains("demo")){
                    System.out.println(clazz);
                }
                return bean;
            }
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }
        };
    }
}
