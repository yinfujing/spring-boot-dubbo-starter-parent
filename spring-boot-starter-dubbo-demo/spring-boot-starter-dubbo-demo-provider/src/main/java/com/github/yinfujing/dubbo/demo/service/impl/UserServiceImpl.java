package com.github.yinfujing.dubbo.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.github.yinfujing.dubbo.demo.User;
import com.github.yinfujing.dubbo.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.yinfujing.mock.MockUtils.mock;

/**
 * 使用 dubbo提供的注解，扩展了一下protocol
 *
 * 同时使用配置和注解，看最终配置结果是否为配置文件中配置的内容
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public User query(String id) {
        log.debug("根据 id：{} 获得用户信息 ",id);
        User user= mock(User.class);
        assert user != null;
        user.setUid(id);
        return user;
    }

    @Override
    public List<User> randomUser(int size) {
        log.debug("获得某一批用户 ，需要获得的用户数为 {}",size);

        return Stream.generate(() -> mock(User.class))
                .limit(size)
                .collect(Collectors.toList());
    }
}
