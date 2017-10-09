package com.alibaba.dubbo.spring.boot.autoconfigure;

import com.alibaba.dubbo.spring.boot.autoconfigure.register.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        DubboBeanFactory.class
        , ApplicationConfigRegister.class, ModuleConfigRegister.class, RegistryConfigRegister.class, ProtocolConfigRegister.class
        , ConsumerConfigRegister.class
})
@EnableConfigurationProperties({DubboProperties.class})
@Slf4j
@ActiveProfiles({"dubbo-standard"})
public class DubboBeanFactoryTest {

    @Test
    public void name() throws Exception {
      log.debug("测试dubboBean Factory");
    }
}