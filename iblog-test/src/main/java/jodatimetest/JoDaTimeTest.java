package jodatimetest;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * @author youfang
 * @date 2018-02-07 16:19
 **/
public class JoDaTimeTest {

    @Test
    public void test1(){
        DateTime dateTime = new DateTime().minusDays(1);
        System.out.println(dateTime.toString("yyyy-MM-dd"));
    }
}
