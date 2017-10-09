package rui.framework.dubbo.demo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import rui.framework.dubbo.demo.service.UserService;

/**
 * 使用 dubbo提供的注解，扩展了一下protocol
 *
 * 同时使用配置和注解，看最终配置结果是否为配置文件中配置的内容
 */
@Service(protocol = {"dubbo:20403"})
//@org.springframework.stereotype.Service
@Component
public class UserServiceImpl implements UserService {
    @Override
    public String sayHello(String name) {
        return "Hello "+ name;
    }
}
