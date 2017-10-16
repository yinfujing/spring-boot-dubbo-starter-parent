package com.github.yinfujing.dubbo.demo.service;

import com.github.yinfujing.dubbo.demo.Role;

import java.util.List;

public interface RoleService {
    Role getRole(String id);

    List<Role> getMore(int size);
}
