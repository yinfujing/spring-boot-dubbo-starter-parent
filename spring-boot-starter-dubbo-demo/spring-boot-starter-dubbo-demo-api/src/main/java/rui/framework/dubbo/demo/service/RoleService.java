package rui.framework.dubbo.demo.service;

import rui.framework.dubbo.demo.Role;

import java.util.List;

public interface RoleService {
    Role getRole(String id);

    List<Role> getMore(int size);
}
