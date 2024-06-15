import com.yu.yurpc.model.RpcResponse;
import com.yu.yurpc.retry.NoRetryStrategy;
import com.yu.yurpc.retry.RetryStrategy;
import org.junit.Test;

/**
 * 重试策略重试
 */
public class RetryStrategyTest {
    RetryStrategy retryStrategy = new NoRetryStrategy();

    @Test
    public void doRetry() {
        try {
            RpcResponse rpcResponse = retryStrategy.doRetry(() -> {
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println(rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}
