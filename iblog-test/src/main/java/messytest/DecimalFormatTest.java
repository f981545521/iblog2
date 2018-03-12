package messytest;

import org.junit.Test;

import java.text.DecimalFormat;

/**
 * @author youfang
 * @date 2018-03-12 15:27
 **/
public class DecimalFormatTest {

    @Test
    public void test1(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String str = decimalFormat.format(456.34D);
        System.out.println(str);
    }
}
