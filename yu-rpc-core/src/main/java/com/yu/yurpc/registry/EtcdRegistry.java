package com.yu.yurpc.registry;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Etcd测试
 */
public class EtcdRegistry {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建 etcd连接
        Client client = Client.builder().endpoints("http://localhost:2379").build();
        // 获取kv客户端
        KV kvClient = client.getKVClient();
        //准备键值对数据
        ByteSequence key = ByteSequence.from("test_key".getBytes());
        ByteSequence value = ByteSequence.from("test_value".getBytes());
        // 插入数据
        kvClient.put(key,value).get();
        // 获取数据
        CompletableFuture<GetResponse> future = kvClient.get(key);
        GetResponse response = future.get();
        System.out.println(response.getKvs().get(0).getValue().toString());
        //删除键值对
        kvClient.delete(key).get();
    }
}
