package com.yu.yurpc.retry;

/**
 * 重试策略键名 常量
 */
public interface RetryStrategyKeys {
    /**
     * 不重试
     */
    String NO = "no";
    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";
}
