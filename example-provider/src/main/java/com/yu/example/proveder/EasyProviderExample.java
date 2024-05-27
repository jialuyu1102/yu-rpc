package com.yu.example.proveder;


import com.yu.yurpc.server.HttpServer;
import com.yu.yurpc.server.VertXHttpServer;

/**
 * 简单服务提供示例
 */
public class EasyProviderExample {
    public static void main(String[] args) {

        //启动web服务
        HttpServer httpServer = new VertXHttpServer();
        httpServer.doStart(8080);
    }
}
