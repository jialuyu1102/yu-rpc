package com.yu.examplespringbootconsumer;

import com.yu.example.common.model.User;
import com.yu.example.common.service.UserService;
import com.yu.yurpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl {
    /**
     * 使用 Rpc 框架注入
     */
    @RpcReference
    private UserService userService;

    /**
     * 测试方法
     */
    public void test() {
        User user = new User();
        user.setName("yu");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }
}
