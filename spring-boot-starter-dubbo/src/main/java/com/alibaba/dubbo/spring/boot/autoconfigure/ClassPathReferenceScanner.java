package com.alibaba.dubbo.spring.boot.autoconfigure;

import com.alibaba.dubbo.spring.boot.autoconfigure.reference.ReferenceFactoryBean;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Arrays;
import java.util.Set;

/**
 * 仿照 mybatis 的 实现，当前只实现了部分的操作
 */
@Slf4j
public class ClassPathReferenceScanner extends ClassPathBeanDefinitionScanner {

    public ClassPathReferenceScanner(BeanDefinitionRegistry registry,DubboProperties dubboProperties) {
        super(registry);
        this.dubboProperties=dubboProperties;
    }

    @Setter
    private DubboProperties dubboProperties;
    @Setter
    private ReferenceFactoryBean<?> referenceFactoryBean=new ReferenceFactoryBean<>();

    //此处希望借鉴使用mybatis 对于 spring bean的注入。结果是伤感的
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        System.out.println(beanDefinitions);
        if(beanDefinitions.isEmpty()) {
            logger.warn("在 {} 包下，寻找不到 dubbo 需要查找的 service，请检查你的配置", Arrays.toString(basePackages));
        } else{
            processBeanDefinitions(beanDefinitions); 
        }

        return super.doScan(basePackages);
    }

    /**
     * 解析bean 的定义
     */
    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {


//        GenericBeanDefinition definition;
//        //使用 在idea 使用 iter 可以快速生成 下面的这种 foreach 代码
//        for (BeanDefinitionHolder holder : beanDefinitions) {
//            definition= (GenericBeanDefinition) holder.getBeanDefinition();
//            if(logger.isDebugEnabled()){
//                logger.debug("准备注入一个被dubbo 代理的 bean，bean 名称为{},接口类为 {}",holder.getBeanName(),definition.getBeanClassName());
//            }
//        }

        //这里不能够使用和弃用的原因是应为在此版本中的通过注册方式注册的bean现在已经存在了相关的问题，无法从配置好的配置文件中已经实现的 dubboProperties。
//        List<ReferenceBean> referenceBeans=dubboProperties.getReferences();
//        for (ReferenceBean referenceBean : referenceBeans) {
//            GenericBeanDefinition definition=new GenericBeanDefinition();
//            definition.getConstructorArgumentValues().addGenericArgumentValue(referenceBean);
//            definition.setBeanClass(this.referenceFactoryBean.getClass());
//            BeanDefinitionHolder beanDefinitionHolder=new BeanDefinitionHolder(definition,referenceBean.getId());
//            beanDefinitions.add(beanDefinitionHolder);
//        }

    }
}
