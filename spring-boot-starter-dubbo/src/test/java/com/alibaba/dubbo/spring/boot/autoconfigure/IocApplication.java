package com.alibaba.dubbo.spring.boot.autoconfigure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoServiceImpl.class})
public class IocApplication {
	@Autowired
	private DemoService demoService;

	@Test
	public void iocTest() throws Exception {
		demoService.sayHello();
	}

	@Test
	public void ioc(){
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("/demoService.xml");
		DemoService demoService=applicationContext.getBean(DemoService.class);
		demoService.sayHello();
	}
}
