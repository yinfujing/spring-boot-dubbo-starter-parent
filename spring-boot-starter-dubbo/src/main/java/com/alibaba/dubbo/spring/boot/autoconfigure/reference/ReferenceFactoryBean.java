package com.alibaba.dubbo.spring.boot.autoconfigure.reference;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 引用工厂类 获得 从dubbo 中 获得 构建service 实例
 */
public class ReferenceFactoryBean<T> implements FactoryBean<T> ,InitializingBean,DisposableBean{

    private ReferenceBean<T> referenceBean;

    @Override
    public T getObject() throws Exception {
        return referenceBean.get();
    }

    @Override
    public Class<?> getObjectType() {
        return referenceBean.getObjectType();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void destroy() throws Exception {
        referenceBean.destroy();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //TODO 可以增加一些配置之类的东西

    }
}
