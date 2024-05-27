package com.yu.yurpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地注册中心
 * 使用 ConcurrentHashMap 存储服务的注册信息，key 为服务名称，value 为服务的实现类
 * 作用：根据服务名称，获取对应的实现类
 *
 */
public class LocalRegistry {

    /**
     * 注册信息存储
     */
    public static final Map<String ,Class<?>> map = new ConcurrentHashMap<String, Class<?>>();

    /**
     * 注册服务
     * @param serviceName
     * @param implClass
     */
    public static void register(String serviceName, Class<?> implClass){
        map.put(serviceName,implClass);
    }

    /**
     * 获取服务
     * @param serviceName
     * @return
     */
    public static Class<?> get(String serviceName){
        return map.get(serviceName);
    }

    /**
     * 删除服务
     * @param serviceName
     */
    public static void remove(String serviceName){
        map.remove(serviceName);
    }
}
