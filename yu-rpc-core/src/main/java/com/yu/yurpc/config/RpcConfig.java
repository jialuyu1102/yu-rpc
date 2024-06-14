package com.yu.yurpc.config;


import com.yu.yurpc.serializer.Serializerkeys;
import lombok.Data;

/**
 * RPC 框架默认配置
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "yu-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = Serializerkeys.JDK;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();

}
