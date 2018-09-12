//package core;

import core.BKTree;
import core.StringEditMetric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by YotWei on 2018/9/8.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        BKTree<String> bkTree = new BKTree<>(1, new StringEditMetric());
        bkTree.addAll(getDictionary());

        while (sc.hasNext()) {
            String word = sc.next().trim();
            System.out.printf("matching set: %s\n", bkTree.search(word));
        }
    }

    private static Set<String> getDictionary() throws IOException {
        Set<String> dictionary = new HashSet<>();
        BufferedReader br = new BufferedReader(new FileReader(new File("bktree/src/words")));
        String temp;
        while ((temp = br.readLine()) != null) {
            dictionary.add(temp);
        }
        br.close();

        return dictionary;
    }
}
