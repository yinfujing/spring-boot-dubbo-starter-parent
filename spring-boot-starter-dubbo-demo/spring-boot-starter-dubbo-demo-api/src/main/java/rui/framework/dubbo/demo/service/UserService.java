package rui.framework.dubbo.demo.service;

import rui.framework.dubbo.demo.User;

import java.util.List;

public interface UserService {
    User query(String id);

    List<User> randomUser(int size);
}
