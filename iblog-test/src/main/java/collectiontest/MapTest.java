package collectiontest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.Test;

import java.util.*;

/**
 * @author youfang
 * @date 2018-03-07 19:21
 **/
public class MapTest {

    private Map<Integer,String> preparedMap(){
        Map<Integer,String> map = new HashMap<>();
        map.put(2,"张三");
        map.put(3,"李四");
        map.put(4,"王麻");
        return map;
    }

    private Map<Integer,String> preparedMap2(){
        Map<Integer,String> map = new HashMap<>();
        map.put(2,"张老三");
        map.put(3,"李小四");
        map.put(5,"王二麻");
        return map;
    }

    @Test
    public void test11(){
        Map<Integer,Map<Integer,String>> filterSameMap = Maps.newHashMap();
        Map<Integer,String> value = new HashMap<>();
        value.put(22,"小飞");
        filterSameMap.put(2,value);
        filterSameMap.get(2).put(33,"大飞");
        Map<Integer,String> value2 = new HashMap<>();
        value2.put(22,"小x飞");
        filterSameMap.put(3,value2);
        filterSameMap.get(3).put(33,"大x飞");
        List<String> realList = Lists.newArrayList();
        List<String> realList2 = Lists.newArrayList();
        for (Map.Entry<Integer,Map<Integer,String>> entry : filterSameMap.entrySet()){
            Map<Integer,String> entryMap = entry.getValue();
            for (Map.Entry<Integer,String> subEntry : entryMap.entrySet()){
                realList.add(subEntry.getValue());
            }
        }
        System.out.println(realList);
        for (Map.Entry<Integer,Map<Integer,String>> entry : filterSameMap.entrySet()){
            realList2.addAll(entry.getValue().values());
        }
        System.out.println(realList2);
    }

    @Test
    public void test11_1(){
        Multimap<Integer, String> multimap = ArrayListMultimap.create();
        multimap.put(2,"22");
        multimap.put(3,"33");
        System.out.println(multimap);
    }





    @Test
    public void test1(){
        Map<Integer,String> map = new HashMap<>();
        map.put(2,"张三");
        map.put(3,"李四");
        map.put(4,"王二麻");
        System.out.println(map.get(null));
        for (Map.Entry<Integer,String> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }
    }

    @Test
    public void test2(){
        Map<Integer,String> map = preparedMap();
        // 遍历方法一 hashmap entrySet() 遍历
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key=" + key + " value=" + value);
        }
    }

    @Test
    public void test3(){
        Map<Integer,String> map = preparedMap();
        // JDK1.5中,应用新特性For-Each循环
        // 遍历方法二
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            System.out.println("key=" + key + " value=" + value);
        }
    }
    @Test
    public void test4(){
        Map<Integer,String> map = preparedMap();
        // 遍历方法三 hashmap keySet() 遍历
        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            Object obj = i.next();
            System.out.println(obj);// 循环输出key
            System.out.println("key=" + obj + " value=" + map.get(obj));
        }
        for (Iterator i = map.values().iterator(); i.hasNext();) {
            Object obj = i.next();
            System.out.println(obj);// 循环输出value
        }
    }

    @Test
    public void test5(){
        Map<Integer,String> map = preparedMap();
        // 遍历方法四 treemap keySet()遍历
        for (Object o : map.keySet()) {
            System.out.println("key=" + o + " value=" + map.get(o));
        }
    }


    @Test
    public void test6(){
        Map<Integer, String> map = new HashMap<>();
        System.out.println(map.size());
    }

    @Test
    public void test7(){
        Map<Integer, String> map = preparedMap();
        System.out.println(map.get(null));
    }

    @Test
    public void test8(){
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

    @Test
    public void test213(){
        Map<Integer,String> map = preparedMap();
        System.out.println(map.toString());
    }

    @Test
    public void mergeMap(){
        Map<Integer, String> simpleMap = preparedMap();
        Map<Integer, String> complexMap = preparedMap2();
        simpleMap.putAll(complexMap);//将目标Map加入调用者
        System.out.println(simpleMap.toString());
        System.out.println(complexMap.toString());
    }

    @Test
    public void test2133(){
        String jsonStr = "{'颜色':['红色','白色','黑色'],'内存':['6+64','6+128']}";
        LinkedHashMap<String, List<String>> listMap = JSON.parseObject(jsonStr, new TypeReference<LinkedHashMap<String, List<String>>>(){});
        System.out.println(listMap);
        for (Map.Entry<String, List<String>> stringListEntry : listMap.entrySet()) {
            System.out.println(stringListEntry.getKey());
            System.out.println(stringListEntry.getValue());
        }
    }





}
