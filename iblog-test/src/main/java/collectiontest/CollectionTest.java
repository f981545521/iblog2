package collectiontest;

import org.junit.Test;

import java.util.*;

/**
 * @author youfang
 * @version [1.0.0, 2019-03-20 下午 04:32]
 **/
public class CollectionTest {

    @Test
    public void test1(){
        List<String> stringList = new ArrayList<>();
        stringList.add("aaa");
        stringList.add("ddd");
        stringList.add("eee");
        stringList.add("aaa");
        System.out.println(stringList.size());
        Set<String> stringSet = new HashSet<>(stringList);
        System.out.println(stringSet.size());
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("aaa", "aaa");
        stringMap.put("ddd", "ddd");
        stringMap.put("aaa", "aaa");
        System.out.println(stringMap.size());
        String s = "aaa";
        System.out.println(s.length());
        String[] strings = {"ss", "dd"};
        System.out.println(strings.length);
    }

    @Test
    public void test2(){
        String[][] strings = new String[2][3];
        strings[0][0] = "sss";
        strings[0][1] = "aaa";
        strings[0][2] = "fff";
        System.out.println(Arrays.toString(strings[0]));
        System.out.println(Arrays.toString(strings[1]));
    }
}
