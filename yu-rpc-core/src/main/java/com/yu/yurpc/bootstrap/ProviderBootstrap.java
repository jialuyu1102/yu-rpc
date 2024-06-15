package com.yu.yurpc.bootstrap;

import com.yu.yurpc.RpcApplication;
import com.yu.yurpc.config.RegistryConfig;
import com.yu.yurpc.config.RpcConfig;
import com.yu.yurpc.model.ServiceMetaInfo;
import com.yu.yurpc.model.ServiceRegisterInfo;
import com.yu.yurpc.registry.LocalRegistry;
import com.yu.yurpc.registry.Registry;
import com.yu.yurpc.registry.RegistryFactory;
import com.yu.yurpc.server.tcp.VertxTcpClient;
import com.yu.yurpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * 服务提供者初始化
 */
public class ProviderBootstrap {
    /**
     * 初始化
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList){
        //RPC框架初始化 （配置和注册中心）
        RpcApplication.init();
        //全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        //注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            //本地注册
            LocalRegistry.register(serviceName,serviceRegisterInfo.getImplClass());

            //注册服务至注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            }catch (Exception e){
                throw new RuntimeException(serviceName + "注册服务失败 ",e);
            }
        }
        //启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
