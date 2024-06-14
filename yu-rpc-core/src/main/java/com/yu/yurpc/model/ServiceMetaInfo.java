package com.yu.yurpc.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * 注册信息
 */
@Data
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
    private Integer servicePort;
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

    /**
     * 获取服务完整地址
     * @return
     */
    public String getServiceAddress(){
        if (!StrUtil.contains(serviceHost,"http")){
            return String.format("http://%s:%s", serviceHost, servicePort);
        }
        return String.format("%s:%s", serviceHost, servicePort);
    }
}
