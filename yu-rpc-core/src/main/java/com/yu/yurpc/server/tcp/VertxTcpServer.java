package com.yu.yurpc.server.tcp;

import com.yu.yurpc.server.HttpServer;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;

public class VertxTcpServer implements HttpServer {

    private byte[] handleRequest(byte[] requestData){
        //在这里编写处理请求的逻辑，根据requestData 构造响应数据并返回
        //这里只是一个先例，实际逻辑需要根据具体的业务需求来实现
        return "hello,client".getBytes();
    }
    
    @Override
    public void doStart(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();
        
        //创建TCP服务器
        NetServer server = vertx.createNetServer();
        
        //处理请求
        // server.connectHandler(new TcpServerHandler());
        server.connectHandler(socket ->{
            //处理连接
            socket.handler(buffer -> {
                String testMessage = "Hello server! , Hello server! , Hello server! , Hello server! ";
                int testMessageLength = testMessage.getBytes().length;
                //处理接收到的字节数组
                byte[] requestData = buffer.getBytes();
                if (requestData.length < testMessageLength){
                    System.out.println("半包，length = " + requestData.length);
                    return;
                }
                if (requestData.length > testMessageLength){
                    System.out.println("粘包，length = " + requestData.length);
                    return;
                }
                String str = new String(buffer.getBytes(0,testMessageLength));
                System.out.println("str = "+str);
                if (testMessage.equals(str)){
                    System.out.println("good");
                }
                //在这里进行自定义的字节数组处理逻辑，比如解析请求，调用服务，构造响应等
                byte[] responseData = handleRequest(requestData);
                //发送响应
                socket.write(Buffer.buffer(responseData));
            });
        });
        //启动TCP服务器 并监听指定端口
        server.listen(port,result->{
            if (result.succeeded()){
                System.out.println("TCP server started on port" + port);
            }else {
                System.err.println("Failed to start TCP server " + result.cause());
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpServer().doStart(8888);
    }
}
