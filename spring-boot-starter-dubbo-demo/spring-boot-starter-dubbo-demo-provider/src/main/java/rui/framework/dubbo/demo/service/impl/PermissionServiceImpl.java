package rui.framework.dubbo.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import rui.framework.dubbo.demo.Permission;
import rui.framework.dubbo.demo.Role;
import rui.framework.dubbo.demo.service.PermissionService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static rui.framework.mock.MockUtils.mock;

/**
 * 使用provider配置
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    @Override
    public Permission query(String id) {
        log.debug("根据 id：{} 获得Permission 信息 ",id);
        Permission permission   = mock(Permission.class);
        assert permission != null;
        permission.setName(id);
        return permission;
    }

    @Override
    public List<Permission> queryMore(int size) {
        log.debug("获得某一批 Permission ，需要获得的 数为 {}",size);
        return Stream.generate(() -> mock(Permission.class))
                .limit(size)
                .collect(Collectors.toList());
    }
}
