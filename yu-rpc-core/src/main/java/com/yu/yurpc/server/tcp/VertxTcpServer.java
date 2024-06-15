package com.yu.yurpc.server.tcp;

import com.yu.yurpc.server.HttpServer;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.parsetools.RecordParser;

public class VertxTcpServer implements HttpServer {

    private byte[] handleRequest(byte[] requestData) {
        // 在这里编写处理请求的逻辑，根据requestData 构造响应数据并返回
        // 这里只是一个先例，实际逻辑需要根据具体的业务需求来实现
        return "hello,client".getBytes();
    }

    @Override
    public void doStart(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建TCP服务器
        NetServer server = vertx.createNetServer();

        // 处理请求
        // server.connectHandler(new TcpServerHandler());
        server.connectHandler(socket -> {
            // 构造 parser
            RecordParser parser = RecordParser.newFixed(8);
            parser.setOutput(new Handler<Buffer>() {
                //初始化
                int size = -1;
                //一次完整的读取（头+体）
                Buffer resultBuffer = Buffer.buffer();

                @Override
                public void handle(Buffer buffer) {
                    if (-1 == size){
                        //读取消息体长度
                        size = buffer.getInt(4);
                        parser.fixedSizeMode(size);
                        //写入头信息到结果
                        resultBuffer.appendBuffer(buffer);
                        System.out.println("size = -1 :"+resultBuffer.toString());
                    }else {
                        //写入 消息体信息至结果
                        resultBuffer.appendBuffer(buffer);
                        System.err.println("size != -1 :" + resultBuffer.toString());
                        //重置一轮
                        parser.fixedSizeMode(8);
                        size = -1;
                        resultBuffer = Buffer.buffer();
                    }
                }
            });

            socket.handler(parser);

        });
        // 启动TCP服务器 并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("TCP server started on port" + port);
            } else {
                System.err.println("Failed to start TCP server " + result.cause());
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpServer().doStart(8888);
    }
}
