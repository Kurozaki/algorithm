package core;

import java.util.*;

/**
 * Created by YotWei on 2018/9/8.
 */
public class BKTree<T> {

    private final int radius;

    private Node root;
    private Metric<T> metric;

    public BKTree(int radius, Metric<T> metric) {
        this.radius = radius;
        this.metric = metric;
    }

    public void add(T value) {
        if (root == null)
            root = new Node(value);
        else {
            root.add(value);
        }
    }

    public void addAll(Collection<? extends T> collection) {
        for (T val : collection) {
            add(val);
        }
    }

    public Set<T> search(T value) {
        Set<T> result = new HashSet<>();
        if (root != null)
            root.search(value, result);
        return result;
    }

    class Node {
        private T value;
        private Map<Integer, Node> childs;

        Node(T v) {
            this.value = v;
            this.childs = new HashMap<>();
        }

        void add(T value) {
            int distance = metric.getMetric(this.value, value);
            if (this.childs.containsKey(distance)) {
                this.childs.get(distance).add(value);
            } else {
                this.childs.put(distance, new Node(value));
            }
        }

        void search(T value, Set<T> resultSet) {
            int distance = metric.getMetric(this.value, value);

            if (distance <= radius) {
                /*
                 * 最短编辑距离小于模糊匹配的容许范围，加入结果集
                 */
                resultSet.add(this.value);
            }

            /*
             * 对于BK树的任意一个节点模糊匹配有
             *
             * 推论：最短编辑距离的三角恒等式
             *
             *    d(Q, A) + d(Q, temp) >= d(A, temp)
             * => d(A, temp) <= d - r
             *
             *    d(Q, A) >= d(Q, temp) + d(temp, A)
             * => d(A, temp) >= d + r
             */
            for (int i = Math.max(distance - radius, 1); i <= distance + radius; i++) {
                Node ch = this.childs.get(i);
                if (ch != null)
                    ch.search(value, resultSet);
            }
        }
    }
}
