package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class RegisterDubboConfig<T extends AbstractConfig> {

    final DefaultListableBeanFactory beanFactory;
    final DubboProperties dubboProperties;
    public T defaultConfig;
    public List<T> configs;

    public abstract Class<T> getTClass();


    RegisterDubboConfig(BeanFactory listableBeanFactory, DubboProperties dubboProperties) {
        this.beanFactory = (DefaultListableBeanFactory) listableBeanFactory;
        this.dubboProperties = dubboProperties;
        initConfigs();
        getDefault();
    }

    abstract void initConfigs();
    abstract T getDefaultBySystem();

    public void registerDubboConfig(){
        log.debug("注册 {}",getTClass().getCanonicalName());
        for (T config : configs) {
            beanFactory.registerSingleton(config.getId(),config);
        }
    }



    @SuppressWarnings({"JavaReflectionMemberAccess"})
    T getDefault() {
        Class<T> tClass = getTClass();
        Method method=getIsDefaultMethod();
        //校验，是否存在 isDefault() 这个方法,不存在将不处理！
        if(method!=null){
            if ((configs != null) && (configs.size() != 0)) {
                if(!isContainsDefaultInList(method)){
                    defaultConfig=configs.get(0);
                }
                setFirstElementAsDefault(tClass);
            }else{
                initByDefault();
            }
        }
        return defaultConfig;
    }

    @SuppressWarnings("JavaReflectionMemberAccess")
    private Method getIsDefaultMethod(){
        try {
             return  getTClass().getDeclaredMethod("isDefault");
        } catch (NoSuchMethodException e) {
            log.debug("{} 没有 isDefault 方法", getTClass().getCanonicalName());
            return null;
        }
    }

    /**
     * list 都为空， 自动启用系统初始化配置
     */
    private void initByDefault() {
        configs=new ArrayList<>();
        defaultConfig=getDefaultBySystem();
        configs.add(defaultConfig);
    }

    @SuppressWarnings({"JavaReflectionMemberAccess", "JavaReflectionInvocation"})
    private void setFirstElementAsDefault(Class<T> tClass) {
        try {
            tClass.getDeclaredMethod("setDefault").invoke(defaultConfig,true);
            configs.set(0,defaultConfig);
        } catch (NoSuchMethodException | IllegalAccessException |InvocationTargetException e) {
            log.debug("{} 没有 isDefault 方法", tClass.getCanonicalName());
        }
    }

    /**
     * 在 list　中是否存在　默认的　config
     * @param method isDefault 方法
     * @return true 找到了，false 为没有
     */
    private boolean isContainsDefaultInList(Method method) {
        try {
            for (T config : configs) {
                Boolean result = (Boolean) method.invoke(config);
                if (result != null && result) {
                    defaultConfig = config;
                    return true;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("【未期望】无法获得 isDefault", e);
        }
        return false;
    }


    public T getAbstractConfigById(String id) {
        T t = null;
        if (StringUtils.isEmpty(id)) {
            t = defaultConfig;
        } else {
            for (T config : configs) {
                if (id.equals(config.getId())) {
                    t = config;
                    break;
                }
            }
        }
        if (t == null) {
            throw new BeanDefinitionStoreException("此 ApplicationConfig 的id 没有配置，需要的bean的id为{}", id);
        }
        return t;
    }

    public T merge(T source) {
        T target = null;
        try {
            target = getAbstractConfigById(source.getId());
            target = compareAndMerge(source, target);
        } catch (BeanDefinitionStoreException e) {
            configs.add((T) target);
        }
        return target;
    }

    /**
     * 比较并 merge ,甚至可能涉及到合并等的操作
     */
    public abstract T compareAndMerge(T source, T target);
}
