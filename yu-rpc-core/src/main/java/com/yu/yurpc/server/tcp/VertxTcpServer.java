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
            String testMessage = "Hello server! , Hello server! , Hello server! , Hello server! ";
            int testMessageLength = testMessage.getBytes().length;

            // 构造 parser
            RecordParser parser = RecordParser.newFixed(testMessageLength);
            parser.setOutput(new Handler<Buffer>() {
                @Override
                public void handle(Buffer buffer) {
                    String str = new String(buffer.getBytes());
                    System.out.println("str = " + str);
                    if (testMessage.equals(str)) {
                        System.out.println("good");
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
