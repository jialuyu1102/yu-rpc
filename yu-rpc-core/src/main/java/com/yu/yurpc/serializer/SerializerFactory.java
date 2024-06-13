package com.yu.yurpc.serializer;

import com.yu.yurpc.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 序列化工厂
 */
public class SerializerFactory {
    // 序列化映射，用于实现单例
    static {
        SpiLoader.load(Serializer.class);
    }


    //默认序列化器
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     */
    public static Serializer getInstance(String key){
        return SpiLoader.getInstance(Serializer.class, key);
    }

}
