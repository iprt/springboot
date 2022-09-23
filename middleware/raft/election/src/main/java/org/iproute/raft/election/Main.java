package org.iproute.raft.election;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main
 *
 * @author zhuzhenjie
 * @since 2022/9/22
 */
public class Main {


    public static void main(String[] args) {
        test11();
    }

    static void test3() {
        // 模拟选举
        Node a = new Node("A");

        Node b = new Node("B");

        Node c = new Node("C");

        List<Node> clusterInfo = new ArrayList<>();
        clusterInfo.add(a);
        clusterInfo.add(b);
        clusterInfo.add(c);


        a.setClusterInfo(clusterInfo);
        b.setClusterInfo(clusterInfo);
        c.setClusterInfo(clusterInfo);


        ExecutorService es = Executors.newFixedThreadPool(3);

        es.execute(a);
        es.execute(b);
        es.execute(c);
    }

    static void test5() {
        // 模拟选举
        Node a = new Node("A");

        Node b = new Node("B");

        Node c = new Node("C");

        Node d = new Node("D");

        Node e = new Node("E");

        List<Node> clusterInfo = new ArrayList<>();
        clusterInfo.add(a);
        clusterInfo.add(b);
        clusterInfo.add(c);
        clusterInfo.add(d);
        clusterInfo.add(e);


        a.setClusterInfo(clusterInfo);
        b.setClusterInfo(clusterInfo);
        c.setClusterInfo(clusterInfo);
        d.setClusterInfo(clusterInfo);
        e.setClusterInfo(clusterInfo);


        ExecutorService es = Executors.newFixedThreadPool(5);


        es.execute(a);
        es.execute(b);
        es.execute(c);
        es.execute(d);
        es.execute(e);
    }

    static void test11() {
        List<Node> nodes = new ArrayList<>();
        List<Node> clusterInfo = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            Node x = new Node("Node" + i);

            nodes.add(x);
            clusterInfo.add(x);
        }

        for (Node node : nodes) {
            node.setClusterInfo(clusterInfo);
        }

        ExecutorService es = Executors.newFixedThreadPool(9);

        for (Node node : nodes) {
            es.execute(node);
        }

    }
}
