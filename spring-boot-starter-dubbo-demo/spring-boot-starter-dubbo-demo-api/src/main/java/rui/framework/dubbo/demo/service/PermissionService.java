package rui.framework.dubbo.demo.service;

import rui.framework.dubbo.demo.Permission;

import java.util.List;

public interface PermissionService {
    Permission query(String id);
    List<Permission> queryMore(int size);
}
