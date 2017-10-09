package com.alibaba.dubbo.spring.boot.autoconfigure.condition;

import com.alibaba.dubbo.spring.boot.condition.DubboBeanRegisterPostProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        DubboBeanRegisterPostProcessor.class
})
public class DubboBeanRegisterPostProcessorTest {
    @Test
    public void postProcessBeanFactory() throws Exception {
        System.in.read();
    }

    @Test
    public void postProcessBeanDefinitionRegistry() throws Exception {
    }

}