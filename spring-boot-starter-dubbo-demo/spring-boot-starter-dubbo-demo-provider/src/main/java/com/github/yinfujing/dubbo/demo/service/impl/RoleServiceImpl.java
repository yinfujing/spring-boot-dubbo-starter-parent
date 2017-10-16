package com.github.yinfujing.dubbo.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.github.yinfujing.dubbo.demo.Role;
import com.github.yinfujing.dubbo.demo.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.yinfujing.mock.MockUtils.mock;

/**
 * 只使用配置文件
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {


    @Override
    public Role getRole(String id) {
        log.debug("根据 id：{} 获得角色信息 ",id);
        Role role   = mock(Role.class);
        assert role != null;
        role.setName(id);
        return role;
    }

    @Override
    public List<Role> getMore(int size) {
        log.debug("获得某一批 Role ，需要获得的 数为 {}",size);
        return Stream.generate(() -> mock(Role.class))
                .limit(size)
                .collect(Collectors.toList());
    }
}
