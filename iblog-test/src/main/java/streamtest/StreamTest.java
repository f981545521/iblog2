package streamtest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * @author youfang
 * @version [1.0.0, 2018-07-06 下午 12:58]
 **/
public class StreamTest {
    @Test
    public void test1(){
        List<String> stringList = new ArrayList<>();
        stringList.add("sdf");
        stringList.add("wqeqwe");
        stringList.add("sdewrwf");
        stringList.add("rtet");
        stringList.add("bvcb");
        //List<String> strings = stringList.parallelStream().map(String::toUpperCase).collect(Collectors.toList());
        stringList.stream().map(n -> n.toUpperCase()).forEach(p -> {
            System.out.println(p);
        });

        //stringList.stream().map(String::toUpperCase).forEach(System.out::println);
        System.out.println(stringList);
        stringList.forEach(p -> {
            if (p.equalsIgnoreCase("rtet")){
                System.out.println(p);
            }
        });
    }

    @Test
    public void test2(){
        List<String> stringList = new ArrayList<>();
        stringList.add("sdf");
        stringList.add("wqeqwe");
        stringList.add("sdewrwf");
        stringList.add("rtet");
        stringList.add("bvcb");
        stringList.add("sdf");
        //去重
        List<String> list = stringList.stream().distinct().collect(toList());
        System.out.println(list);
    }

    @Test
    public void test3(){
        List<String> stringList = new ArrayList<>();
        stringList.add("sdf");
        stringList.add("wqeqwe");
        stringList.add("sdewrwf");
        stringList.add("rtet");
        stringList.add("bvcb");
        stringList.add("sdf");
        //截取前3个
        List<String> list = stringList.stream().limit(3).collect(toList());
        System.out.println(list);
    }

    @Test
    public void test4(){
        List<String> stringList = new ArrayList<>();
        stringList.add("sdf");
        stringList.add("wqeqwe");
        stringList.add("sdewrwf");
        stringList.add("rtet");
        stringList.add("bvcb");
        stringList.add("sdf");
        //跳过前3个
        List<String> list = stringList.stream().skip(3).collect(toList());
        System.out.println(list);
    }
    @Test
    public void test5(){
        List<Integer> stringList = new ArrayList<>();
        stringList.add(1);
        stringList.add(2);
        stringList.add(3);
        stringList.add(4);
        stringList.add(5);
        stringList.add(7);
        //filter 过滤
        //List<Integer> list = stringList.stream().filter(x -> x < 5).collect(toList());
        List<Integer> list = stringList.stream().filter(new Predicate<Integer>(){
            @Override
            public boolean test(Integer integer) {
                System.out.println(integer);
                return integer > 5;
            }
        }).collect(toList());
        System.out.println(list);
    }

    @Test
    public void test56(){
        List<Integer> stringList = new ArrayList<>();
        stringList.add(1);
        stringList.add(2);
        stringList.add(3);
        stringList.add(4);
        stringList.add(5);
        stringList.add(7);
        //filter 过滤
        Integer opt = stringList.stream().filter(x -> x == 4).findAny().orElse(null);
        System.out.println(opt);

    }











}
