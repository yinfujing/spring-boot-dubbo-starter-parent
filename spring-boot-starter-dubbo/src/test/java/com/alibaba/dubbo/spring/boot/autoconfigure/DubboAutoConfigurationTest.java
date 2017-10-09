package com.alibaba.dubbo.spring.boot.autoconfigure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DubboAutoConfiguration.class})
public class DubboAutoConfigurationTest {

    @Test
    public void name() throws Exception {
        System.in.read();
    }
}