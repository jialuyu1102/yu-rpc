package com.yu.example.proveder;


import com.yu.example.common.service.UserService;
import com.yu.yurpc.RpcApplication;
import com.yu.yurpc.registry.LocalRegistry;
import com.yu.yurpc.server.HttpServer;
import com.yu.yurpc.server.VertXHttpServer;

/**
 * 简单服务提供示例
 */
public class EasyProviderExample {
    public static void main(String[] args) {

        RpcApplication.init();

        //服务的全限定名
        String serviceName = UserService.class.getName();
        //注册服务至本地，根据服务名找到对应的实现类
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        //启动web服务
        HttpServer httpServer = new VertXHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
