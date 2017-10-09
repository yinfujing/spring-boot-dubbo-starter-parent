package rui.framework.dubbo;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rui.framework.dubbo.demo.service.UserService;

/**
 * dubbo 通过xml 获得资源信息
 */
public class DubboXmlTest {
    private ClassPathXmlApplicationContext applicationContext;
    @Before
    public void setUp() throws Exception {
        System.setProperty("dubbo.application.logger","slf4j");
        applicationContext =new ClassPathXmlApplicationContext("classpath*:/dubbo-consumer.xml");
        applicationContext.start();
    }

    @Test
    public void sayHello() throws Exception {
        UserService userService=applicationContext.getBean(UserService.class);
        System.out.println(userService.sayHello("rui"));
    }
}