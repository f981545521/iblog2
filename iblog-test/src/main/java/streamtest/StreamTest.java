package streamtest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<String> strings = stringList.parallelStream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(stringList);
    }
}
