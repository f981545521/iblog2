package jodatimetest;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Years;
import org.junit.Test;

import java.util.Calendar;

/**
 * 参考文章：https://www.ibm.com/developerworks/cn/java/j-jodatime.html
 * @author youfang
 * @date 2018-02-07 16:19
 **/
public class JoDaTimeTest {

    @Test
    public void test1(){
        DateTime dateTime = new DateTime().minusDays(1);
        System.out.println(dateTime.toString("yyyy-MM-dd"));
    }

    @Test
    public void test2(){
        //构建一个时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1, 0, 0, 0);

        DateTime dateTime = new DateTime(2018, 03, 13, 0, 0, 0, 0);
        System.out.println(dateTime);
    }
    public static final String TABLE_NAME_PATTERN = "yyyyMMdd";
    public static final String DEFAULT_DAY_FORMAT_PATTERN = "yyyy-MM-dd";
    @Test
    public void test3(){
        DateTime dateTime = new DateTime(2018, 03, 14, 0, 0, 0, 0);
        System.out.println(dateTime);
        DateTime yesterday = dateTime.minusDays(1);
        String startSuffix = yesterday.toString(TABLE_NAME_PATTERN);
        System.out.println(startSuffix);
        System.out.println(dateTime.toString(DEFAULT_DAY_FORMAT_PATTERN));
        System.out.println(yesterday.toString(DEFAULT_DAY_FORMAT_PATTERN));
    }

    @Test
    public void test4(){
        System.out.println(new DateTime().toString("yyyy-MM-dd"));
    }

    @Test
    public void test12(){
        String birth = "1992 01 01";
        String[] birthArr = birth.split(" ");
        int year = Integer.parseInt(birthArr[0]);
        int month = Integer.parseInt(birthArr[1]);
        int day = Integer.parseInt(birthArr[2]);
        DateTime birthDay = new DateTime(year, month, day, 0, 0, 0, 0);
        DateTime nowDay = new DateTime();
        int diffYear = Years.yearsBetween(birthDay, nowDay).getYears();
        int diffDay = Days.daysBetween(birthDay, nowDay).getDays();
        System.out.println(diffYear);
        System.out.println(diffDay);
    }
}
