package com.yu.yurpc.serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * 序列化工厂
 */
public class SerializerFactory {
    // 序列化映射，用于实现单例
    private static final Map<String, Serializer> KEY_SERIALIZER_MAP = new HashMap<String, Serializer>(){{
        put(Serializerkeys.JDK,new JdkSerializer());
        put(Serializerkeys.KRYO,new KryoSerializer());
        put(Serializerkeys.HESSIAN,new HessianSerializer());
        put(Serializerkeys.JSON,new JsonSerializer());
    }};

    //默认序列化器
    private static final Serializer DEFAULT_SERIALIZER = KEY_SERIALIZER_MAP.get("jdk");

    /**
     * 获取实例
     */
    public static Serializer getInstance(String key){
        return KEY_SERIALIZER_MAP.getOrDefault(key,DEFAULT_SERIALIZER);
    }

}
