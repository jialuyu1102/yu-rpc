package com.yu.example.proveder;

import com.yu.example.common.service.UserService;
import com.yu.yurpc.RpcApplication;
import com.yu.yurpc.bootstrap.ProviderBootstrap;
import com.yu.yurpc.config.RegistryConfig;
import com.yu.yurpc.config.RpcConfig;
import com.yu.yurpc.model.ServiceMetaInfo;
import com.yu.yurpc.model.ServiceRegisterInfo;
import com.yu.yurpc.registry.LocalRegistry;
import com.yu.yurpc.registry.Registry;
import com.yu.yurpc.registry.RegistryFactory;
import com.yu.yurpc.server.HttpServer;
import com.yu.yurpc.server.VertXHttpServer;
import com.yu.yurpc.server.tcp.VertxTcpServer;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务提供者
 */
public class ProviderExample {
    public static void main(String[] args) {
        //要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo<UserServiceImpl> serviceRegisterInfo = new ServiceRegisterInfo<>(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);
        //服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
