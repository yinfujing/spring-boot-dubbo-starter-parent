package rui.framework.dubbo.demo.service.impl;

import org.springframework.stereotype.Component;
import rui.framework.dubbo.demo.service.RoleService;

import java.util.Arrays;
import java.util.List;

/**
 * 只使用配置文件
 */
@Component
public class RoleServiceImpl implements RoleService {
    @Override
    public List<String> getRoles(String name) {
        return Arrays.asList("admin","user");
    }
}
