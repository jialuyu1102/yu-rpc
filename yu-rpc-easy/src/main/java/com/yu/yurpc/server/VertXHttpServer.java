package com.yu.yurpc.server;

import io.vertx.core.Vertx;

/**
 * 基于Vert.x实现的web服务器，能够监听接口并响应
 */
public class VertXHttpServer implements HttpServer {
    @Override
    public void doStart(int port) {
        //创建 Vert.x实例
        Vertx vertx = Vertx.vertx();

        //创建HTTP服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        //监听端口 并处理请求
        server.requestHandler(request -> {
            //处理 HTTP请求
            System.out.println("接受到请求 :" + request.method() + "--" + request.uri());

            // 发送HTTP响应
            request.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x HTTP Server");
        });
        //启动 HTTP 服务器并监听指定端口
        server.listen(port,result->{
            if (result.succeeded()){
                System.out.println("服务器正在监听端口： "+ port);
            }else {
                System.err.println("无法启动服务 : "+ result.cause());
            }
        });
    }
}
