package com.github.yinfujing.dubbo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.yinfujing.dubbo.demo.service.PermissionService;
import com.github.yinfujing.dubbo.demo.service.UserService;
import com.github.yinfujing.dubbo.demo.service.RoleService;

import java.util.List;

/**
 * 演示demo的控制器
 * Created by 赵睿 on 2017/6/6.
 */
@RestController
public class DemoController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("user/{uid}")
    public User queryUserById(@PathVariable String uid){
        return userService.query(uid);
    }
    @RequestMapping("users/{size}")
    public List<User> queryUsersBySize(@PathVariable int size){
        return userService.randomUser(size);
    }
    @RequestMapping("role/{id}")
    public Role queryRoleById(@PathVariable String id){
        return roleService.getRole(id);
    }
    @RequestMapping("roles/{size}")
    public List<Role> queryRoleBySize(@PathVariable int size){
        return roleService.getMore(size);
    }
    @RequestMapping("permission/{id}")
    public Permission queryPermissionById(@PathVariable String id){
        return permissionService.query(id);
    }
    @RequestMapping("permissions/{size}")
    public List<Permission> queryPermissionBySize(@PathVariable int size){
        return permissionService.queryMore(size);
    }
}
