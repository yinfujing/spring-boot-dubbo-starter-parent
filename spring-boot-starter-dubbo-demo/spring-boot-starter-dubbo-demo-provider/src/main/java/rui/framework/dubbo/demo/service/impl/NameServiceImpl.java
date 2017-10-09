package rui.framework.dubbo.demo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import rui.framework.dubbo.demo.service.NameService;

import java.util.Arrays;
import java.util.List;

/**
 * 只使用注解方式，通过 127.0.0.1:1025/dubbo 查看是否生效
 *
 * 使用注解无需配置接口等，会默认获得第一个接口，所以接口属性还是需要注意一下
 */
@Service(protocol = {"dubbo:20403"})
@Component
public class NameServiceImpl implements NameService {

    @Override
    public String getName(String userName) {
        return "I am a User "+"我是一个用户";
    }
}
