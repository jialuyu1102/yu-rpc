package com.yu.yurpc.model;

/**
 * 注册信息
 */
public class ServiceMetaInfo {
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 服务版本
     */
    private String serviceVersion;
    /**
     * 服务域名
     */
    private String serviceHost;
    /**
     * 服务端口号
     */
    private String servicePort;
    /**
     * 服务分组
     */
    private String serviceGroup = "default";


    /**
     * 获取服务键名
     */
    public String getServiceKey(){
        /**
         * 后续可拓展服务分组
         * return String.format("%s:%s:%s", serviceName, serviceVersion,serviceGroup);
         */
        return String.format("%s:%s", serviceName, serviceVersion);
    }
    /**
     * 获取服务注册节点键名
     */
    public String getServiceNodeKey(){
        return String.format("%s/%s:%s", getServiceKey(), serviceHost, servicePort);
    }
}
