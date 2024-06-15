package com.yu.yurpc.server.tcp;

import cn.hutool.core.util.IdUtil;
import com.yu.yurpc.RpcApplication;
import com.yu.yurpc.model.RpcRequest;
import com.yu.yurpc.model.RpcResponse;
import com.yu.yurpc.model.ServiceMetaInfo;
import com.yu.yurpc.protocol.*;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.util.concurrent.CompletableFuture;

public class VertxTcpClient {

    public static RpcResponse doRequest(RpcRequest rpcRequest, ServiceMetaInfo serviceMetaInfo) throws Exception {
        // 发送TCP 请求
        Vertx vertx = Vertx.vertx();
        NetClient netClient = vertx.createNetClient();
        CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
        netClient.connect(serviceMetaInfo.getServicePort(), serviceMetaInfo.getServiceHost(),
                result -> {
                    if (!result.succeeded()) {
                        System.out.println("Failed to connect TCP server");
                        return;
                    }
                    NetSocket socket = result.result();
                    // 发送消息
                    // 构造数据
                    ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
                    ProtocolMessage.Header header = new ProtocolMessage.Header();
                    header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
                    header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
                    header.setSerializer((byte) ProcotolMessageSerializerEnum.
                            getEnumByValue(RpcApplication.getRpcConfig().getSerializer()).getKey());
                    header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
                    // 生成 全局请求 ID
                    header.setRequestId(IdUtil.getSnowflakeNextId());
                    protocolMessage.setHeader(header);
                    protocolMessage.setBody(rpcRequest);

                    // 编码请求
                    try {
                        Buffer encode = ProtocolMessageEncoder.encode(protocolMessage);
                        socket.write(encode);
                    } catch (Exception e) {
                        throw new RuntimeException("协议消息编码错误");
                    }

                    // 接受响应
                    TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(
                            buffer -> {
                                try {
                                    ProtocolMessage<RpcResponse> rpcResponseProtocolMessage =
                                            (ProtocolMessage<RpcResponse>) ProtocolMessageDecoder.decode(buffer);
                                    responseFuture.complete(rpcResponseProtocolMessage.getBody());
                                } catch (Exception e) {
                                    throw new RuntimeException("协议消息解码错误");
                                }
                            });
                    socket.handler(bufferHandlerWrapper);
                });
        RpcResponse rpcResponse = responseFuture.get();
        netClient.close();
        return rpcResponse;
    }


    public void start() {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();
        vertx.createNetClient().connect(8888, "localhost", result -> {
            if (result.succeeded()) {
                System.out.println("Connected to TCP server");
                NetSocket socket = result.result();
                // 发送数据
                for (int i = 0; i < 10; i++) {
                    Buffer buffer = Buffer.buffer();
                    String str = "Hello server! " + i + ", Hello server! , Hello server! , Hello server! ";
                    buffer.appendInt(0);
                    buffer.appendInt(str.getBytes().length);
                    buffer.appendBytes(str.getBytes());
                    socket.write(buffer);
                }
                // socket.write("Hello server");
                // 接受响应
                socket.handler(buffer -> {
                    System.out.println("Received response from server:" + buffer.toString());
                });
            } else {
                System.err.println("Faild to connect TCP Server");
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpClient().start();
    }
}
