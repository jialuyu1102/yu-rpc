package com.yu.yurpc.tolerant;

import com.yu.yurpc.model.RpcResponse;

import java.util.Map;

/**
 * 容错策略-快速失败
 */
public class FailFastTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错",e);
    }
}
