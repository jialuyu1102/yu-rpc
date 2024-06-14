package com.yu.yurpc;

import com.yu.yurpc.config.RegistryConfig;
import com.yu.yurpc.config.RpcConfig;
import com.yu.yurpc.constant.RpcConstant;
import com.yu.yurpc.registry.Registry;
import com.yu.yurpc.registry.RegistryFactory;
import com.yu.yurpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * RPC 框架应用
 * 想到于 holder，存放了项目全局用到的变量，双重检测单例模式 实现
 */
@Slf4j
public class RpcApplication {
    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化，支持传入自定义配置
     *
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init,config = {}", newRpcConfig.toString());
        //注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init,config = {}", registryConfig);
    }

    /**
     * 初始化
     */
    public static void init() {
        //RPC相关配置：主机名、端口号、序列化、注册中心等
        RpcConfig newRpcConfig;
        try {
            //从配置文件 application{-environment}.properties 中读取配置
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            //e.printStackTrace();
            newRpcConfig = new RpcConfig();
        }
        //根据配置文件和默认进行初始化
        init(newRpcConfig);
    }


    /**
     * 获取配置
     *
     * @return
     */
    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
