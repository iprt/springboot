package org.iproute.middleware.kafka.metadata;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.Node;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * MetadataMain
 *
 * @author tech@intellij.io
 * @since 2025-04-16
 */
public class MetadataMain {

    public static void main(String[] args) {
        // 配置Kafka连接属性
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka.vpn.eblssmart.com:19092");

        // 创建AdminClient
        try (AdminClient adminClient = AdminClient.create(props)) {
            // 1. 获取集群信息
            printClusterInfo(adminClient);

            // 2. 获取主题列表
            Set<String> topics = getTopicList(adminClient);

            // 3. 获取指定主题的元数据
            if (!topics.isEmpty()) {
                String topicName = topics.iterator().next(); // 获取第一个主题作为示例
                printTopicMetadata(adminClient, topicName);
            }

        } catch (Exception e) {
            System.err.println("获取Kafka元数据时发生错误: " + e.getMessage());
        }
    }

    /**
     * 打印集群信息
     */
    private static void printClusterInfo(AdminClient adminClient) throws ExecutionException, InterruptedException {
        System.out.println("========== Kafka集群信息 ==========");
        DescribeClusterResult clusterResult = adminClient.describeCluster();

        // 获取集群ID
        String clusterId = clusterResult.clusterId().get();
        System.out.println("集群ID: " + clusterId);

        // 获取Controller节点
        Node controller = clusterResult.controller().get();
        System.out.println("Controller: " + controller.id() + " (" + controller.host() + ":" + controller.port() + ")");

        // 获取Broker节点列表
        Collection<Node> nodes = clusterResult.nodes().get();
        System.out.println("Broker数量: " + nodes.size());
        for (Node node : nodes) {
            System.out.println("  - Broker: " + node.id() + " (" + node.host() + ":" + node.port() + ")");
        }
    }

    /**
     * 获取主题列表
     */
    private static Set<String> getTopicList(AdminClient adminClient) throws ExecutionException, InterruptedException {
        System.out.println("\n========== 主题列表 ==========");
        ListTopicsResult topicsResult = adminClient.listTopics();
        Set<String> topics = topicsResult.names().get();

        System.out.println("主题数量: " + topics.size());
        for (String topic : topics) {
            System.out.println("  - " + topic);
        }

        return topics;
    }

    /**
     * 打印指定主题的元数据
     */
    private static void printTopicMetadata(AdminClient adminClient, String topicName) throws ExecutionException, InterruptedException {
        System.out.println("\n========== 主题 '" + topicName + "' 的元数据 ==========");
        DescribeTopicsResult topicResult = adminClient.describeTopics(Collections.singletonList(topicName));
        Map<String, TopicDescription> topicDescriptionMap = topicResult.all().get();

        TopicDescription topicDescription = topicDescriptionMap.get(topicName);
        System.out.println("名称: " + topicDescription.name());
        System.out.println("分区数: " + topicDescription.partitions().size());
        System.out.println("是否内部主题: " + topicDescription.isInternal());

        System.out.println("分区信息:");
        topicDescription.partitions().forEach(partition -> {
            System.out.println("  分区ID: " + partition.partition());
            System.out.println("  分区Leader: " + partition.leader().id() + " (" + partition.leader().host() + ":" + partition.leader().port() + ")");

            System.out.println("  副本: ");
            partition.replicas().forEach(replica ->
                    System.out.println("    - replica.id =" + replica.id() + "| (" + replica.host() + ":" + replica.port() + ")")
            );

            System.out.println("  ISR: ");
            partition.isr().forEach(isr ->
                    System.out.println("    -     isr.id =" + isr.id() + "| (" + isr.host() + ":" + isr.port() + ")")
            );
            System.out.println();
        });
    }

}
