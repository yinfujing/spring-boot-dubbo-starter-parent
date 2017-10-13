package com.alibaba.dubbo.spring.boot.autoconfigure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DubboProperties.class})
@EnableConfigurationProperties({DubboProperties.class})
@ActiveProfiles({"default", "dubbo-standard", "dubbo-consumer", "dubbo-provider"})
public class DubboPropertiesTest {
    @Autowired
    private DubboProperties dubboProperties;

    @Test
    public void getDubboProperties() throws Exception {
        assertNotNull(dubboProperties);
        System.out.println(dubboProperties);
    }

    @Test
    public void getApplications() throws Exception {
        assertNotNull(dubboProperties.getApplications());
        System.out.println(dubboProperties.getApplications());
    }

    @Test
    public void getModules() throws Exception {
        assertNotNull(dubboProperties.getModules());
        System.out.println(dubboProperties.getModules());
    }

    @Test
    public void getRegistries() throws Exception {
        assertNotNull(dubboProperties.getRegistries());
        System.out.println(dubboProperties.getRegistries());
    }

    @Test
    public void getMonitors() throws Exception {
        assertNotNull(dubboProperties.getMonitors());
        System.out.println(dubboProperties.getMonitors());
    }

    @Test
    public void getProtocols() throws Exception {
        assertNotNull(dubboProperties.getProtocols());
        System.out.println(dubboProperties.getProtocols());
    }


    @Test
    public void getProviders() throws Exception {
        assertNotNull(dubboProperties.getProviders());
        System.out.println(dubboProperties.getProviders());
    }
    @Test
    public void getServices() throws Exception {
        assertNotNull(dubboProperties.getServices());
        System.out.println(dubboProperties.getServices());
    }

    @Test
    public void getConsumers() throws Exception {
        assertNotNull(dubboProperties.getConsumers());
        System.out.println(dubboProperties.getConsumers());
    }

    @Test
    public void getReferences() throws Exception {
        assertNotNull(dubboProperties.getReferences());
        System.out.println(dubboProperties.getReferences());
    }

    @Test
    public void getAnnotations() throws Exception {
        assertNotNull(dubboProperties.getAnnotations());
        System.out.println(dubboProperties.getAnnotations());
    }

}