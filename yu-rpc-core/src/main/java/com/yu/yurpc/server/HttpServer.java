package com.yu.yurpc.server;

/**
 * HTTP 服务器接口
 * 定义统一的服务启动方法，便于扩展
 *      如实现多种不同的web服务器
 */
public interface HttpServer {

    /**
     * 启动服务器
     *
     * @param port 端口号
     */
    void doStart(int port);
}
