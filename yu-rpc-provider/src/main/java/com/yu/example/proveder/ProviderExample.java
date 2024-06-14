package com.yu.example.proveder;

import com.yu.example.common.service.UserService;
import com.yu.yurpc.RpcApplication;
import com.yu.yurpc.config.RegistryConfig;
import com.yu.yurpc.config.RpcConfig;
import com.yu.yurpc.model.ServiceMetaInfo;
import com.yu.yurpc.registry.LocalRegistry;
import com.yu.yurpc.registry.Registry;
import com.yu.yurpc.registry.RegistryFactory;
import com.yu.yurpc.server.HttpServer;
import com.yu.yurpc.server.VertXHttpServer;
import com.yu.yurpc.server.tcp.VertxTcpServer;

/**
 * 服务提供者
 */
public class ProviderExample {
    public static void main(String[] args) {
        //PRC 项目初始化
        RpcApplication.init();

        //注册服务
        String serviceName = UserService.class.getName();
        //本地注册
        LocalRegistry.register(serviceName,UserServiceImpl.class);
        //注册服务至注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        //基于SPI 获取注册中心，此处 默认 etcd
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        //注册中心-服务信息
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(rpcConfig.getVersion());
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        }catch (Exception e){
            throw new RuntimeException();
        }

        //启动Web 服务-TCP服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(8080);
    }
}
