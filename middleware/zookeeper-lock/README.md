# zookeeper 实现分布式锁

## code

```
// 设置zk客户端重试策略，每隔5秒重试一次，最多重试10次
RetryPolicy policy=new ExponentialBackoffRetry(5000,10);

CuratorFramework client=CuratorFrameworkFactory.builder()
    .connectString("shdq02:2181")
    .retryPolicy(policy)
    .build();
// 建立socket连接

client.start();

final InterProcessMutex mutex=new InterProcessMutex(client,"/lock/shoe");

```

```

try {
    // 获取锁
    mutex.acquire();
    // ....... do something ......
} finally {
    // 释放锁
    mutex.release();
}
```

