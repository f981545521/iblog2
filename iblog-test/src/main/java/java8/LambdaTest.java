package java8;

import cn.acyou.iblog.entity.Student;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author youfang
 * @version [1.0.0, 2018-9-13 下午 08:54]
 **/
public class LambdaTest {

    List<Student> s = new ArrayList<>();

    Map<Integer, Student> sMap = new HashMap<>();

    @Before
    public void prepareData(){
        s.add(new Student(1, "张飞", 34, "南京"));
        s.add(new Student(2, "关羽", 35, "谢良"));
        s.add(new Student(3, "赵云", 32, "南京"));
        s.add(new Student(4, "马超", 37, "南京"));
        s.add(new Student(4, "黄忠", 48, "南京"));

        sMap.put(1, new Student(1, "张飞", 34, "南京"));
        sMap.put(2, new Student(2, "关羽", 35, "谢良"));
        sMap.put(3, new Student(3, "赵云", 32, "南京"));
        sMap.put(4, new Student(4, "马超", 37, "南京"));
        sMap.put(5, new Student(4, "黄忠", 48, "南京"));
    }

    @Test
    public void test1(){
        List<String> nameList = s.stream().map(x -> {
            return x.getUserName();
        }).collect(Collectors.toList());
        //同下
        List<String> nameList2 = s.stream().map(Student::getUserName).collect(Collectors.toList());
        System.out.println(nameList2);
    }

    @Test
    public void test2(){
        List<Student> nameList = s.stream().filter(x -> {
            return x.getAddress().equals("南京");
        }).collect(Collectors.toList());
        //同下
        List<Student> nanjingList = s.stream().filter(x -> x.getAddress().equals("南京")).collect(Collectors.toList());

        List<String> nanjingNameList = s.stream().filter(x -> x.getAddress().equals("南京")).map(Student::getUserName).collect(Collectors.toList());

        System.out.println(nanjingNameList);
        System.out.println(nanjingList);
    }

    @Test
    public void test3(){
        List<Student> stuList = sMap.entrySet().stream().map(x->{
            Student student = x.getValue();
            return student;
        }).collect(Collectors.toList());

        System.out.println(stuList);
    }
    @Test
    public void test31(){
        Integer sumAge = s.stream().map(Student::getAge).reduce(0, (prev, curr) -> {
            System.out.println("acc: " + prev);
            System.out.println("item: " + curr);
            System.out.println("--------");
            return prev + curr;

        });
        Optional maxAge = s.stream().map(Student::getAge).reduce((p,c) -> {
            return Integer.max(p,c);
        });

        System.out.println(maxAge);

        System.out.println(sumAge);
    }

    @Test
    public void test4(){
        Optional<Student> stuOpt = s.stream().filter(x -> {
            return x.getAge()< 45;
        }).findAny();

        System.out.println(stuOpt.get());
    }

    @Test
    public void mapErgodic(){
        sMap.entrySet().stream().forEach(x -> System.out.println(x));
        //同下
        sMap.entrySet().stream().forEach(System.out::println);
    }

    @Test
    public void listDistinct(){
        List<String> strList = new ArrayList<>();
        strList.add("aaa");
        strList.add("bbb");
        strList.add("aaa");
        strList.add("vvv");

        List<String> distinctList = strList.stream().distinct().collect(Collectors.toList());
        System.out.println(distinctList);
    }

    @Test
    public void optinal(){
        Integer value1 = null;
        Integer value2 = new Integer(10);

        // Optional.ofNullable - 允许传递为 null 参数
        Optional<Integer> a = Optional.ofNullable(value1);

        // Optional.of - 如果传递的参数是 null，抛出异常 NullPointerException
        Optional<Integer> b = Optional.of(value2);

        Student student = new Student();
        String name = student.getUserName();
        System.out.println(name);
        Optional<String> op = Optional.of(name);

    }


}
