package com.alibaba.dubbo.spring.boot.autoconfigure.register;

import com.alibaba.dubbo.spring.boot.autoconfigure.DubboBeanFactory;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import com.alibaba.dubbo.spring.boot.demo.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        DubboBeanFactory.class
        , MonitorConfigRegister.class
        , ApplicationConfigRegister.class, ModuleConfigRegister.class, RegistryConfigRegister.class, ProtocolConfigRegister.class
        , ConsumerConfigRegister.class
        , ReferenceConfigRegister.class

})
@EnableConfigurationProperties({DubboProperties.class})
@Slf4j
@ActiveProfiles({"dubbo-standard", "dubbo-consumer"})
public class ReferenceConfigRegisterTest {
    @Autowired
    private DemoService demoService;

    @Test
    public void reference() throws Exception {
        demoService.sayHello();
    }
}