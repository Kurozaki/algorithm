import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by YotWei on 2018/9/9.
 */
public class TireTree {

    private final Node root = new Node('\0');

    public void insert(String word) {
        if (word.isEmpty()) {
            return;
        }
        char[] arr = word.toCharArray();
        Node cur = root;
        for (char c : arr) {
            if (!cur.children.containsKey(c)) {
                cur.children.put(c, new Node(c));
            }
            cur = cur.children.get(c);
        }
        cur.wordEnd = true;
    }

    public Set<String> search(String prefix) {
        char[] arr = prefix.toCharArray();
        Node cur = root;
        Set<String> resultSet = new HashSet<>();
        for (char c : arr) {
            cur = cur.children.get(c);
            if (cur == null)
                break;
        }
        if (cur != null) {
            getAllWords(cur, prefix, resultSet);
        }
        return resultSet;
    }

    private void getAllWords(Node node, String prefix, Set<String> resultSet) {
        if (node.wordEnd) {
            resultSet.add(prefix);
        }
        for (Map.Entry<Character, Node> e : node.children.entrySet()) {
            getAllWords(e.getValue(), prefix + e.getKey(), resultSet);
        }
    }

    private static class Node {

        Node(char c) {
            this.ch = c;
        }
        
        boolean wordEnd = false;    /*是否是某个单词的最后一个字母*/
        char ch;    /*这个节点所代表的字母*/
        Map<Character, Node> children = new HashMap<>();
    }
}
