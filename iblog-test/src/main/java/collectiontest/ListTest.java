package collectiontest;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;

/**
 * @author youfang
 * @date 2018-03-14 15:45
 **/
public class ListTest {

    @Test
    public void test1(){
        List<String> list = new ArrayList<>();
        list.add("23");
        list.add("明天");
        list.add("回家");
        //将List转为数组
        String[] ss = list.toArray(new String[list.size()]);
        System.out.println(ss);
    }


    /**
     *  将数组转为List
     */
    @Test
    public void test2(){
        String[] str = new String[]{"明天","你","要回家"};
        List<String> list = Lists.newArrayList(str);
        List<String> list2 = Arrays.asList(str);
        List<String> list3 = new ArrayList<>();
        Collections.addAll(list3,str);
        System.out.println(list);
        System.out.println(list2);
        System.out.println(list3);
    }

    /**
     *  LinkedList
     */
    @Test
    public void test3(){
        String[] str = new String[]{"明天","你","要回家"};
        List<String> list = Lists.newArrayList(str);
        LinkedList<String> linked = new LinkedList<>();
        linked.addAll(list);
        linked.pop();
        System.out.println(linked);


    }


    @Test
    public void test4(){
        String[] str = new String[]{"明天","你","要回家"};
        List<String> list = Lists.newArrayList(str);
        String temp = "";
        for (int i = 0; i <list.size(); i++){
            temp += list.get(i);
        }
        System.out.println(temp);
    }



}
