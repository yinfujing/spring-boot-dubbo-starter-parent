package rui.framework.dubbo.demo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rui.framework.dubbo.demo.service.UserService;
import rui.framework.dubbo.demo.service.NameService;
import rui.framework.dubbo.demo.service.RoleService;

import java.util.ArrayList;
import java.util.List;


/**
 * 演示demo的控制器
 * Created by 赵睿 on 2017/6/6.
 */
@RestController
public class DemoController {
    @Reference
    private UserService userService;
    @Autowired
    @Reference
    private NameService nameService;
    @Autowired
    @Reference
    private RoleService roleService;

    @RequestMapping("/say/{name}")
    public List<String> hello(@PathVariable("name") String name){
        List<String> responseMsg=new ArrayList<>();
        responseMsg.add(userService.sayHello(name));
        responseMsg.add(nameService.getName(name));
        responseMsg.add(roleService.getRoles(name).toString());
        return responseMsg;
    }

}
