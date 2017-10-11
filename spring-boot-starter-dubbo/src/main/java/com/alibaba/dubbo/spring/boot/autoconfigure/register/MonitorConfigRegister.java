package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import org.springframework.beans.factory.BeanFactory;

public class MonitorConfigRegister extends RegisterDubboConfig<MonitorConfig> {
   public MonitorConfigRegister(BeanFactory listableBeanFactory, DubboProperties dubboProperties) {
        super(listableBeanFactory, dubboProperties);
    }

    @Override
    public Class<MonitorConfig> getTClass() {
        return MonitorConfig.class;
    }

    @Override
    void initConfigs() {
        this.configs = dubboProperties.getMonitors();
    }

    @Override
    MonitorConfig getDefaultBySystem() {
        return new MonitorConfig();
    }

    @Override
    public MonitorConfig compareAndMerge(MonitorConfig source, MonitorConfig target) {
        return target;
    }
}
