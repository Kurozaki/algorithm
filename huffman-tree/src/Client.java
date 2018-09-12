import core.HuffmanTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YotWei on 2018/9/12.
 */
public class Client {

    public static void main(String[] args) {
        Map<Character, Integer> map = buildMap();
        HuffmanTree<Character> tree = new HuffmanTree<>(map);

        String encode = tree.encode(new Character[]{'y', 'o', 't', 'w', 'e', 'i'});
        System.out.println(encode);

        ArrayList<Character> decode = tree.decode(encode);
        System.out.println(decode);

        Map<Character, String> paths = tree.getPaths();
        for (Character c : decode) {
            System.out.println(c + " --> " + paths.get(c));
        }
    }

    private static Map<Character, Integer> buildMap() {
        String s = "lpkopmniizqbgyatfvtrdcvwetyiwsadghuysasajiodjsoajdciosjfuidsuifidhsfuihfduisiuidsafi" +
                "hunudisagxcnsaiojdojsiaodsmoisaroqiwerifnpoqalnpoikofjeruihfyuwetcrqwsadcnsahduisahudsx" +
                "sdajisocmsaodkspoadjsahfueiwhuorwwiojqiweoqjsidowjioqdjowqdoijwedjowqijdoqjidjiqoijdoji";
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map;
    }
}
