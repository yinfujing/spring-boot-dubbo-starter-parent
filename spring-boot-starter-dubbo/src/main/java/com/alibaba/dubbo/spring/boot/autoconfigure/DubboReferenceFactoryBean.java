package com.alibaba.dubbo.spring.boot.autoconfigure;

import com.alibaba.dubbo.config.ReferenceConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class DubboReferenceFactoryBean<T> implements FactoryBean{
    private ReferenceConfig referenceConfig;
    private Class<T> dubboInterface;

    public DubboReferenceFactoryBean(Class<T> dubboInterface) {
        this.dubboInterface = dubboInterface;
    }

    @Override
    public Object getObject() throws Exception {
        if(log.isDebugEnabled()){
            log.debug("{} 通过rpc 方式获得 实例对象",dubboInterface);
        }
        return referenceConfig.get();
    }

    @Override
    public Class<?> getObjectType() {
        return this.dubboInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
