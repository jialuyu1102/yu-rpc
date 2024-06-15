package com.yu.yurpc.server.tcp;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

public class VertxTcpClient {
    public void start(){
        //创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();
        vertx.createNetClient().connect(8888,"localhost",result->{
            if (result.succeeded()){
                System.out.println("Connected to TCP server");
                NetSocket socket = result.result();
                //发送数据
                for (int i = 0; i < 10; i++) {
                    Buffer buffer = Buffer.buffer();
                    String str = "Hello server! , Hello server! , Hello server! , Hello server! ";
                    buffer.appendInt(0);
                    buffer.appendInt(str.getBytes().length);
                    buffer.appendBytes(str.getBytes());
                    socket.write(buffer);
                }
                // socket.write("Hello server");
                //接受响应
                socket.handler(buffer -> {
                    System.out.println("Received response from server:" + buffer.toString());
                });
            }else {
                System.err.println("Faild to connect TCP Server");
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpClient().start();
    }
}
