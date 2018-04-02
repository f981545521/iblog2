package Interview;

import org.junit.Test;

import java.util.*;

/**
 * @author youfang
 * @date 2018-03-24 下午 07:29
 **/
public class BasicsTest {

    @Test
    public void test1(){
        System.out.println(2<<4);//16  2*2^3
    }

    @Test
    public void test2(){
        short s1 = 1;
        //s1 = s1 + 1;   s1 + 1 的结果是int型，需要强制类型转换
        s1 += 1; //不会报错，会强制把1转为short型
        System.out.println(s1);
    }

    //一个文件中可以存在多个类。但是只能有一个public类，并且此public类必须与文件名相同

    /**
     * 将给定的数组倒序
     */
    @Test
    public void test3(){
        int[] arr = {2,4,67,32,76,88};
        int index = 0;
        int[] result = new int[arr.length];
        for (int i = arr.length -1 ;i >= 0 ;i--){
            result[index] = arr[i];
            index ++;
        }
        System.out.println(Arrays.toString(result));
    }

    /**
     * Math.round() 四舍五入取整法
     * x + 0.5 向下取整
     */
    @Test
    public void test4(){
        System.out.println(Math.round(11.5));
        System.out.println(Math.round(-11.5));
        System.out.println(Math.round(-11.9));
        System.out.println(-11.5 + 0.5);
        System.out.println(-11.9 + 0.5);
    }


    @Test
    public void test5(){
        String str = "RTCG";
        System.out.println(reverseStr(str));
    }

    public String reverseStr(String str){
        if(str.length() <= 1){
            return str;
        }
        return  (str.substring(1)) + str.charAt(0);
    }

    @Test
    public void test6(){
        Map<String, String> map = new TreeMap<>();
        map.put("aaa", "oo");
        map.put("bbb", "aii");
        map.put("ccc", "mni");
        map.put("ddd", "ff");
        map.put("eee", "bvb");
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet){
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("————————————————————————————————————");
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("aaa", "do");
        hashMap.put("bbb", "ci");
        hashMap.put("ccc", "ni");
        hashMap.put("ddd", "ff");
        hashMap.put("eee", "bvb");
        Set<Map.Entry<String, String>> entrySet2 = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet2){
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("————————————————————————————————————");
        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("aaa", "ro");
        linkedHashMap.put("bbb", "vi");
        linkedHashMap.put("ccc", "ni");
        linkedHashMap.put("ddd", "ff");
        linkedHashMap.put("eee", "bvb");
        Set<Map.Entry<String, String>> entrySet3 = linkedHashMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet3){
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

    }


}
