package com.yu.yurpc.loadbalancer;

import com.yu.yurpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 负载 均衡器 （消费者使用）
 */
public interface LoadBalancer {

    /**
     * 选择服务调用
     * @param requestParams
     * @param serviceMetaInfoList
     * @return
     */
    ServiceMetaInfo select(Map<String,Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
