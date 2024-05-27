package com.yu.example.consumer;

import com.yu.example.common.model.User;
import com.yu.example.common.service.UserService;
import com.yu.yurpc.proxy.ServiceProxyFactory;

/**
 * 简单服务消费者示例
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        //todo 需要获取UserService 的实现对象
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("yu");
        //调用
        User newUser = userService.getUser(user);
        if (newUser!=null) {
            System.out.println(newUser.getName());
        }else {
            System.out.println("user == null");
        }
    }
}
