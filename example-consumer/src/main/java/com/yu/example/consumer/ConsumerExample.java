package com.yu.example.consumer;

import com.yu.yurpc.config.RpcConfig;
import com.yu.yurpc.utils.ConfigUtils;

/**
 * 简单服务消费者示例
 */
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
