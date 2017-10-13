package com.alibaba.dubbo.spring.boot;

import com.alibaba.dubbo.spring.boot.actuate.DubboActuateConfiguration;
import com.alibaba.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import com.alibaba.dubbo.spring.boot.demo.DemoService;
import com.alibaba.dubbo.spring.boot.demo.DemoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        DubboAutoConfiguration.class
        , DubboActuateConfiguration.class
})
@Import(DemoServiceImpl.class)
public class ApplicationStarterTest {
    @Autowired
    @Qualifier("demoService")
    private DemoService demoService;

    @Autowired
    @Qualifier("com.alibaba.dubbo.spring.boot.demo.DemoServiceImpl")
    private DemoService demoServiceImpl;
    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void sayHello() throws Exception {
        demoService.sayHello();
        demoServiceImpl.sayHello();
    }


}
