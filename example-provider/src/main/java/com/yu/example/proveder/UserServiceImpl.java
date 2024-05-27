package com.yu.example.proveder;

import com.yu.example.common.model.User;
import com.yu.example.common.service.UserService;

/**
 * 用户实现类
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
