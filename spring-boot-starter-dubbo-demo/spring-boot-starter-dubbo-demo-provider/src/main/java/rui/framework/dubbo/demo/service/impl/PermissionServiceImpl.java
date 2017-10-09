package rui.framework.dubbo.demo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import rui.framework.dubbo.demo.service.PermissionService;

import java.util.Arrays;
import java.util.List;

/**
 * 使用provider配置
 */
@Component
@Service
public class PermissionServiceImpl implements PermissionService {
    @Override
    public List<String> showPermission(String name) {
        return Arrays.asList("/api","account");
    }
}
