package com.yu.yurpc.retry;

import com.yu.yurpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 重试策略-不重试
 */
public class NoRetryStrategy implements RetryStrategy{
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
