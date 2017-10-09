package com.alibaba.dubbo.spring.boot.autoconfigure.reference;

import com.alibaba.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        DubboAutoConfiguration.class
})
@Import({DubboAutoConfiguration.class})
@ActiveProfiles({"dubbo-consumer","dubbo-standard"})
@EnableConfigurationProperties({DubboProperties.class})
public class ReferenceFactoryBeanTest {
    @Autowired
    private DubboProperties dubboProperties;
    @Test
    public void name() throws Exception {
        System.out.println(dubboProperties);
    }
}