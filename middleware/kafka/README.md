# kafka client

## implementation

```txt
    implementation 'org.apache.kafka:kafka-clients:0.11.0.0'
```

## usage

### producer

`KafkaProducer`: 需要创建一个生产者对象

`ProducerConfig`: 获取一系列所需的参数

`ProducerRecord`: 每条数据都要封装成一个`ProducerRecord`对象

#### 不带回调函数的api

[1.不带回调函数的api](java-api/src/main/java/org/iproute/middleware/kafka/consumer/CustomConsumer.java)

#### 带回调函数的api

[2.带回调函数的api](java-api/src/main/java/org/iproute/middleware/kafka/consumer/CustomConsumer.java)

- 回调函数会在 producer 收到 ack 时调用，为异步调用

#### 同步发送api

[3.同步发送api](java-api/src/main/java/org/iproute/middleware/kafka/consumer/CustomConsumer.java)

- 同步发送的意思就是，一条消息发送之后，会阻塞当前线程，直至返回 ack
- 由于 send 方法返回的是一个 Future 对象，根据 Future 对象的特点，我们也可以实现同 步发送的效果，只需在调用 Future 对象的 get 方发即可

### consumer

Consumer 消费数据时的可靠性是很容易保证的，因为数据在 Kafka 中是持久化的，故 不用担心数据丢失问题。

由于 consumer 在消费过程中可能会出现断电宕机等故障，consumer 恢复后，需要从故 障前的位置的继续消费，所以 consumer 需要实时记录自己消费到了哪个 offset，以便故障恢 复后继续消费。

<font color=red>所以 offset 的维护是 Consumer 消费数据是必须考虑的问题。</font>

`KafkaConsumer` : 需要创建一个消费者对象，用来消费数

`ConsumerConfig`: 获取所需的一系列配置参数

`ConsuemrRecord` : 每条数据都要封装成一个 ConsumerRecord 对象

Kafka 提供了自动提交 offset 的功能，Kafka 提供了自动提交 offset 的功能

- <font color=red>`enable.auto.commit`</font>: 是否开启自动提交 offset 功能
- <font color=red>`auto.commit.interval.ms`</font>: 自动提交offset的间隔

#### 自动提交offset

[自动提交offset](java-api/src/main/java/org/iproute/middleware/kafka/producer/CustomProducer.java)

#### 手动提交offset

手动提交 offset 的方法有两种：分别是 commitSync（同步提交）和 commitAsync（异步 提交）。<font color=red>两者的相同点是，都会将本次 poll 的一批数据最高的偏移量提交</font>；

不同点是:

- commitSync 阻塞当前线程，一直到提交成功，并且会自动失败重试
    - 由不可控因素导致， 也会出现提交失败
- 而 commitAsync 则没有失败重试机制，故有可能提交失败

[手动提交offset sync](java-api/src/main/java/org/iproute/middleware/kafka/producer/CustomProducer2.java)

[自动提交offset async](java-api/src/main/java/org/iproute/middleware/kafka/producer/CustomProducer3.java)

### <font color=red>数据漏消费和重复消费分析</font>

无论是同步提交还是异步提交 offset，都有可能会造成数据的漏消费或者重复消费。先 提交 offset 后消费，有可能造成数据的漏消费；而先消费后提交 offset，有可能会造成数据 的重复消费。

## usage2

### consumer rebalance

offset 的维护是相当繁琐的，因为需要考虑到消费者的 Rebalace。

<font color=red>当有新的消费者加入消费者组、已有的消费者推出消费者组或者所订阅的主题的分区发 生变化，就会触发到分区的重新分配，重新分配的过程叫做 Rebalance。</font>

消费者发生 Rebalance 之后，每个消费者消费的分区就会发生变化。  
<font color=red>因此消费者要首先获取到自己被重新分配到的分区，并且定位到每个分区最近提交的 offset 位置继续消费。</font>

要实现自定义存储 offset，需要借助 `ConsumerRebalanceListener`

[RebalanceConsumer.java](java-api/src/main/java/org/iproute/middleware/kafka/consumer/rebalance/RebalanceConsumer.java)

### producer interceptors

Producer 拦截器(interceptor)是在 Kafka 0.10 版本被引入的，主要用于实现 clients 端的定 制化控制逻辑。

对于 producer 而言，interceptor 使得用户在消息发送前以及 producer 回调逻辑前有机会 对消息做一些定制化需求，比如<font color=red>修改消息</font>等。同时，producer
允许用户指定多个 interceptor 按序作用于同一条消息从而形成一个拦截链(interceptor chain)。Intercetpor 的实现接口是
`org.apache.kafka.clients.producer.ProducerInterceptor`，其定义的方法包括：

`org.apache.kafka.clients.producer.ProducerInterceptor`

- `configure(configs)`: 获取配置信息和初始化数据时调用。
- `onSend(ProducerRecord)` : 该方法封装进 KafkaProducer.send 方法中, 该方法封装进 KafkaProducer.send 方法中。
    - Producer 确保在消息被序列化以及计算分区前调用该方法。
    - <font color=red>用户可以在该方法中对消息做任何操作，但最好保证不要修改消息所属的 topic 和分区</font>，否则会影响目标分区的计算
- `onAcknowledgement(RecordMetadata, Exception)`: <font color=red>该方法会在消息从 RecordAccumulator 成功发送到 Kafka Broker
  之后，或者在发送过程中失败时调用。</font>
    - 通常是在 producer 回调逻辑触发之前
    - onAcknowledgement 运行在producer的IO线程中，不在在该方法中放入很重的逻辑，否则会拖慢消息发送的逻辑
- `close`： 关闭interceptor 主要用于执行一些清理资源的工作

[InterceptorProducer](java-api/src/main/java/org/iproute/middleware/kafka/producer/interceptor/InterceptorProducer.java)
