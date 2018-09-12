package core;


import java.util.*;

/**
 * Created by YotWei on 2018/9/12.
 */
public class HuffmanTree<T> {

    private Node treeRoot;
    private Map<T, String> paths;

    public HuffmanTree(Map<T, Integer> weightMap) {
        Node[] arr = parseToNodeArray(weightMap);
        treeRoot = buildHuffmanTree(arr);
    }

    private Node[] parseToNodeArray(Map<T, Integer> map) {
        Node[] arr = new Node[map.size()];
        int pos = 0;
        for (Map.Entry<T, Integer> entry : map.entrySet()) {
            LeafNode ln = new LeafNode();
            ln.weight = entry.getValue();
            ln.data = entry.getKey();
            arr[pos++] = ln;
        }
        Arrays.sort(arr, Comparator.comparingInt(node -> node.weight));
        return arr;
    }

    private Node buildHuffmanTree(Node[] arr) {
        Node root = null;
        for (int i = 0; i < arr.length - 1; i++) {
            root = new Node();
            root.left = arr[i];
            root.right = arr[i + 1];
            root.weight = root.left.weight + root.right.weight;

            int j = i + 1;
            for (; j < arr.length - 1; j++) {
                if (root.weight > arr[j + 1].weight) {
                    arr[j] = arr[j + 1];
                } else
                    break;
            }
            arr[j] = root;
        }
        return root;
    }

    public Map<T, String> getPaths() {
        if (paths == null) {
            paths = new HashMap<>();
            getPaths(treeRoot, "", paths);
        }
        return paths;
    }

    @SuppressWarnings("unchecked")
    private void getPaths(Node root, String path, Map<T, String> paths) {
        if (root instanceof LeafNode) {
            paths.put((T) ((LeafNode) root).data, path);
        } else {
            getPaths(root.left, path + "0", paths);
            getPaths(root.right, path + "1", paths);
        }
    }

    public String encode(T[] arr) {
        StringBuilder sb = new StringBuilder();
        Map<T, String> paths = getPaths();
        for (T t : arr) {
            String code = paths.get(t);
            if (code == null) {
                throw new RuntimeException("Encode failed: Uncoded object '" + t + "'");
            }
            sb.append(code);
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<T> decode(String huffmanCode) {
        ArrayList<T> result = new ArrayList<>();

        Node cur = treeRoot;
        StringBuilder path = new StringBuilder();

        for (char c : huffmanCode.toCharArray()) {
            cur = c == '0' ? cur.left : cur.right;
            path.append(c);

            if (cur instanceof LeafNode) {
                result.add((T) ((LeafNode) cur).data);
                cur = treeRoot;
                path.delete(0, path.length());
            }
        }
        if (cur != treeRoot) {
            throw new RuntimeException(String.format("Decode failed: Incomplete path: '%s'", path));
        }
        return result;
    }

    private static class Node {
        int weight;
        Node left, right;
    }

    private static class LeafNode extends Node {
        Object data;
    }
}
