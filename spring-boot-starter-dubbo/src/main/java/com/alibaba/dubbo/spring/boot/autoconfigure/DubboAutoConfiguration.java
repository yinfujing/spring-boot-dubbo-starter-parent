package com.alibaba.dubbo.spring.boot.autoconfigure;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.alibaba.dubbo.spring.boot.autoconfigure.reference.ReferenceFactoryBean;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
@Configuration
@ConditionalOnClass({ReferenceBean.class, ServiceBean.class})
@EnableConfigurationProperties(DubboProperties.class)
public class DubboAutoConfiguration implements BeanFactoryAware{
    private final static Logger logger= LoggerFactory.getLogger(DubboAutoConfiguration.class);

    private final DubboProperties dubboProperties;

    @Setter
    private BeanFactory beanFactory;


    public DubboAutoConfiguration(DubboProperties dubboProperties) {
        this.dubboProperties = dubboProperties;
    }

    @PostConstruct
    public void checkDubboProperties(){
        logger.debug("dubbo 配置文件 校验");
        Assert.notNull(this.dubboProperties,"dubbo 在spring boot 中的配置文件不能为空");
        DefaultListableBeanFactory listableBeanFactory= (DefaultListableBeanFactory) beanFactory;

        List<ApplicationConfig> applicationConfigs=dubboProperties.getApplications();

        for (ApplicationConfig applicationConfig : applicationConfigs) {
            listableBeanFactory.registerSingleton(applicationConfig.getId(),applicationConfig);
        }



    }



    @Configuration
    @Import({AutoConfiguredScannerRegistrar.class})
    @ConditionalOnMissingBean(ReferenceFactoryBean.class)
    public static class  ScannerRegisterConfiguration{
        @PostConstruct
        public void afterPropertiesSet() {
            logger.debug("No {} found.", ReferenceFactoryBean.class.getName());

        }
    }


    @Setter
    public static class AutoConfiguredScannerRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar,ResourceLoaderAware {

        private final static Logger logger= LoggerFactory.getLogger(AutoConfiguredScannerRegistrar.class);

        private BeanFactory beanFactory;
        private ResourceLoader resourceLoader;


        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

            logger.debug("注册 dubbo 引用其他服务的接口 ");



            boolean required =true;
//        //TODO 获得配置文件的配置 。从配置文件开始定义数据
//        List<ApplicationConfig> applicationConfigs =dubboProperties.getApplications();
//        for (ApplicationConfig applicationConfig : applicationConfigs) {
//
////            registry.re
//
//
//
//        }


//
//        genericBeanDefinition.setSource(referenceBean);
//        BeanDefinitionHolder beanDefinitionHolder=new BeanDefinitionHolder(genericBeanDefinition,referenceBean.getId());
//        beanDefinitions.add(beanDefinitionHolder);


//            ClassPathReferenceScanner scanner=new ClassPathReferenceScanner(registry,null);

//            if(this.resourceLoader!=null){
//                scanner.setResourceLoader(this.resourceLoader);
//            }
//
////        List<String> packages= AutoConfigurationPackages.get(this.beanFactory);
////        if(logger.isDebugEnabled()){
////            for (String pkg : packages) {
////                logger.debug("使用 自动配置加载的 基本包 {}",pkg);
////            }
////        }
//            //TODO  具体的serivce的类/包，
//            //TODO  可以多种，如何排错?,注册中心找不到，就报错拉
//            scanner.doScan("rui");
        }
        //TODO 注册 ApplicationConfig


    }
}
