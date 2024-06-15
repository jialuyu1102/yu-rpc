package com.yu.yurpc.tolerant;

import com.yu.yurpc.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 容错策略-静默处理异常
 */
public class FailSafeTolerantStrategy implements TolerantStrategy{
    private static final Logger log = LoggerFactory.getLogger(FailSafeTolerantStrategy.class);

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常",e);
        return new RpcResponse();
    }
}
