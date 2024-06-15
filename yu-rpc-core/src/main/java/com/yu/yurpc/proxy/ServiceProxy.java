package com.yu.yurpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.yu.yurpc.RpcApplication;
import com.yu.yurpc.config.RpcConfig;
import com.yu.yurpc.constant.RpcConstant;
import com.yu.yurpc.model.RpcRequest;
import com.yu.yurpc.model.RpcResponse;
import com.yu.yurpc.model.ServiceMetaInfo;
import com.yu.yurpc.protocol.*;
import com.yu.yurpc.registry.Registry;
import com.yu.yurpc.registry.RegistryFactory;
import com.yu.yurpc.serializer.JdkSerializer;
import com.yu.yurpc.serializer.Serializer;
import com.yu.yurpc.serializer.SerializerFactory;
import com.yu.yurpc.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 服务代理（JDK动态代理）
 */
public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        String serviceName = method.getDeclaringClass().getName();
        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .paramterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 从注册中心获取服务提供者的请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(rpcConfig.getVersion());
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            // 暂时先取第一个
            ServiceMetaInfo selectServiceMetaInfo = serviceMetaInfoList.get(0);

            // 发送 TCP请求
            RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest, selectServiceMetaInfo);
            return rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("调用失败");
        }
    }
}
