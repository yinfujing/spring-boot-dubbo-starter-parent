package com.github.yinfujing.dubbo.demo.service;

import com.github.yinfujing.dubbo.demo.Permission;

import java.util.List;

public interface PermissionService {
    Permission query(String id);
    List<Permission> queryMore(int size);
}
