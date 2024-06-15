package com.yu.example.consumer;

import com.yu.example.common.model.User;
import com.yu.example.common.service.UserService;
import com.yu.yurpc.bootstrap.ConsumerBootstrap;
import com.yu.yurpc.config.RpcConfig;
import com.yu.yurpc.proxy.ServiceProxyFactory;
import com.yu.yurpc.utils.ConfigUtils;

/**
 * 简单服务消费者示例
 */
public class ConsumerExample {
    public static void main(String[] args) {
        //服务消费者初始化
        ConsumerBootstrap.init();
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
        short number = userService.getNumber();
        System.out.println(number);
    }
}
