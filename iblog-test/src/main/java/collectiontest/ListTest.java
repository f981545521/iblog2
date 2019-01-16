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
        System.out.println("first : " + list.get(0));
    }
    @Test
    public void test234(){
        List<String> list = new ArrayList<>();
        list.add("2322");
        list.add("明天333");
        list.add("回家444");
        System.out.println(list);
        list.clear();
        System.out.println(list);

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
    @Test
    public void test5(){
        String addressExt = "";
        List<String> list = Arrays.asList(addressExt.split(","));
        System.out.println(list);
    }

    @Test
    public void test6(){
        String phoneNum = "15132122966";
        List<String> phoneList = Collections.singletonList(phoneNum);
        List<String> phoneList2 = Lists.newArrayList(phoneNum);
        System.out.println(phoneList);
        System.out.println(phoneList2);
    }



}
