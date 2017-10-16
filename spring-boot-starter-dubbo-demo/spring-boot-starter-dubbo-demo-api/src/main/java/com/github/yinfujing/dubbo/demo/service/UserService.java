package com.github.yinfujing.dubbo.demo.service;

import com.github.yinfujing.dubbo.demo.User;

import java.util.List;

public interface UserService {
    User query(String id);

    List<User> randomUser(int size);
}
