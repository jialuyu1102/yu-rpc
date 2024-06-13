package com.yu.yurpc.server;

import com.yu.yurpc.RpcApplication;
import com.yu.yurpc.model.RpcRequest;
import com.yu.yurpc.model.RpcResponse;
import com.yu.yurpc.registry.LocalRegistry;
import com.yu.yurpc.serializer.JdkSerializer;
import com.yu.yurpc.serializer.Serializer;
import com.yu.yurpc.serializer.SerializerFactory;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * http 请求处理
 * 1. 反序列化请求为对象，并从请求对象中获取参数
 * 2. 根据服务名称从本地注册器中获取对应的服务实现类
 * 3. 通过反射机制调用方法，得到返回结果
 * 4. 对结果进行封装和序列化，并写入到响应中
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {
    @Override
    public void handle(HttpServerRequest request) {
        //指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        //记录日志
        System.out.println("HttpServerHandler接收到请求 :" + request.method() +" "+request.uri());

        //一步处理http请求
        request.bodyHandler(body->{
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                //反序列化
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //构造响应结果
            RpcResponse rpcResponse = new RpcResponse();
            if (rpcRequest == null){
                rpcResponse.setMessage("请求体为null");
                doResponse(request,rpcResponse,serializer);
                return;
            }

            try {
                //通过反射获取需要调用的服务实现类
                //从本地注册器中获取服务实现类
                Class<?> implclass = LocalRegistry.get(rpcRequest.getServiceName());
                //通过反射进行调用
                Method method = implclass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParamterTypes());
                Object result = method.invoke(implclass.newInstance(), rpcRequest.getArgs());
                //封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            //响应
            doResponse(request,rpcResponse,serializer);
        });

    }

    void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response().putHeader("content-type", "application.json");
        try {
            byte[] serialized = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }
}
