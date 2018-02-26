package guavatest;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.*;

/**
 * @author youfang
 * @date 2018-02-07 16:41
 **/
public class GuavaTest {


    @Test
    public void test1(){
        List<User> userList = new ArrayList<User>();
        userList.add(new User(23,"张三",24));
        userList.add(new User(24,"李四",15));
        userList.add(new User(25,"王五",53));
        userList.add(new User(26,"王五",56));
        List<String> nameList = Lists.transform(userList, new Function<User, String>() {
            @Override
            public String apply(User user) {
                return user.getName();
            }
        });

        System.out.println(nameList);
        System.out.println(new ArrayList<String>(new HashSet<String>(nameList)));

        //Set<User> userSet = Sets.newHashSet(userList);
        //System.out.println(userList);
        //System.out.println(Maps.immutableEntry("23","213"));

    }

    @Test
    public void test2(){
        Map<String,User> userMap = new HashMap<String, User>();
        userMap.put("1",new User(23,"张三",24));
        userMap.put("2",new User(24,"李四",15));
        userMap.put("3",new User(25,"王五",53));
        System.out.println(userMap.get(String.valueOf(3)));
    }

    @Test
    public void test3(){
        List<String> list = new ArrayList<String>();
        list.add("22");
        list.add("22");
        list.add("33");
        System.out.println(list);
        Set<String> set = Sets.newHashSet(list);
        Lists.newArrayList(set);
        System.out.println();
    }

    //按照给定常量快速构建一个数组
    @Test
    public void test10(){
        List<String> list = new ArrayList<String>(){{add("e");add("3");}};
        List<String> stringList = new ArrayList<>(Arrays.asList("12","234"));
        List<Integer> stringList2 = ImmutableList.of(12,34);//不可改变的数组，当在尝试add、remove的时候UnsupportedOperationException
        System.out.println(list);
        System.out.println(stringList);
        System.out.println(stringList2);
    }
}
