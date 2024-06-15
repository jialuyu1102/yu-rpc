package com.yu.yurpc.retry;

import com.github.rholder.retry.*;
import com.yu.yurpc.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 重试策略-固定时间间隔
 */
public class FixedIntervalRetryStrategy implements RetryStrategy{
    private static final Logger log = LoggerFactory.getLogger(FixedIntervalRetryStrategy.class);

    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
                //重试条件：当出现Exception异常时重试
                .retryIfExceptionOfType(Exception.class)
                //重试等待策略：fixedWait固定时间间隔
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS))
                //重试停止策略：stopAfterAttempt超过最大重试次数停止
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                //重试工作：监听：每次重试时除了再次执行任务外，还可以打印当前重试 次数
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("重试测试 {}", attempt.getAttemptNumber());
                    }
                })
                .build();
        return retryer.call(callable);
    }
}
