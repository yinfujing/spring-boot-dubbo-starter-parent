package com.alibaba.dubbo.spring.boot.autoconfigure;

import com.alibaba.dubbo.spring.boot.autoconfigure.register.*;
import com.alibaba.dubbo.spring.boot.demo.DemoService;
import com.alibaba.dubbo.spring.boot.demo.DemoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        DubboBeanFactory.class
        , MonitorConfigRegister.class
        , ApplicationConfigRegister.class, ModuleConfigRegister.class, RegistryConfigRegister.class, ProtocolConfigRegister.class
        , ConsumerConfigRegister.class, ReferenceConfigRegister.class
        , ServiceConfigRegister.class, DemoServiceImpl.class

})
@EnableConfigurationProperties({DubboProperties.class})
@Slf4j
@ActiveProfiles({"dubbo-standard", "dubbo-consumer","dubbo-provider"})
public class DubboBeanFactoryTest {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DubboProperties dubboProperties;

    @Autowired
    @Qualifier("demoService")
    private DemoService demoService;

    @Test
    @SuppressWarnings("unchecked")
    public void exportAndReference() throws Exception {
        assertNotNull(applicationContext.getBeansOfType(dubboProperties.getServices().get(0).getInterfaceClass()));
        log.debug("服务发布成功！");
        demoService.sayHello();
        demoService.sayHello();
        demoService.sayHello();
    }
}