package jodatimetest;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

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

    @Test
    public void test23(){
        String dateStr = "2018-07-18 12:23:23";
        DateTime dateTime = new DateTime().minusDays(1);
        System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime yesterDay = DateTime.parse(dateStr, format);
        System.out.println(yesterDay.toLocalDate().equals(dateTime.toLocalDate()));

    }




    @Test
    public void testFormat() {

        System.out.println(format(new Date()));     //今天

        System.out.println(format(new DateTime(new Date()).minusDays(1).toDate())); //昨天

        System.out.println(format(new DateTime(new Date()).minusDays(2).toDate())); //2天前

        System.out.println(format(new DateTime(new Date()).plusDays(1).toDate())); //1天后
    }

    private String format(Date date) {
        DateTime now = new DateTime();
        DateTime today_start = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0, 0);
        DateTime today_end = today_start.plusDays(1);
        DateTime yesterday_start = today_start.minusDays(1);

        if(date.after(today_start.toDate()) && date.before(today_end.toDate())) {
            return String.format("今天 %s", new DateTime(date).toString("HH:mm"));
        } else if(date.after(yesterday_start.toDate()) && date.before(today_start.toDate())) {
            return String.format("昨天 %s", new DateTime(date).toString("HH:mm"));
        }

        return new DateTime(date).toString("yyyy-MM-dd HH:mm");



    }
}
