package org.iproute.middleware.kafka.metadata;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * NewTopicMain
 *
 * @author tech@intellij.io
 * @since 2025-04-16
 */
public class NewTopicMain {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka.vpn.eblssmart.com:19092");


        // 实例化 AdminClient
        try (AdminClient adminClient = AdminClient.create(props)) {
            // 定义 topic 配置
            String topicName = "metadata-test-topic";
            int partitions = 3;
            short replicationFactor = 1;

            // 创建 NewTopic 对象
            NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);

            // 执行创建操作
            adminClient.createTopics(Collections.singleton(newTopic)).all().get();

            System.out.println("Topic " + topicName + " 创建成功!");
        } catch (InterruptedException | ExecutionException e) {
            System.err.println(e.getMessage());
        }

    }
}
