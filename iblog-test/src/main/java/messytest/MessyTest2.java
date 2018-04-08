package messytest;

import org.junit.Test;

import java.util.Scanner;

/**
 * @author youfang
 * @date 2018-04-08 下午 01:34
 **/
public class MessyTest2 {

    @Test
    public void test1(){
        System.out.println(5.3+(int)(8.5+4.6)/3%4);//5.3，
        System.out.println(1+2*3);//7，先乘除后加减
        int i =3;
        double d = 4.3;
        double result2 = i + d;//返回的double类型
        int result = (int) (i + d);//不同类型之间的计算，返回大类型
        System.out.println(result);
        System.out.println((int)(5.65 + 3.9));
        System.out.println(Math.PI);
        System.out.println((double) 3/2 );
        System.out.println(2/3*4.0);
    }

    @Test
    public void test2(){
        int i = 2;
        int i2 = 3;
        //int c = i > i2 ? i++ : i2++;
        System.out.println( i ++ > i2 ++ & i++ > i2++);
        //System.out.println( i ++ < i2 ++ && i++ > i2++);
        System.out.println(i);
        System.out.println(i2);
    }

    @Test
    public void test3(){
        boolean b1 = true;
        boolean b2 = true;
        boolean b3 = false;
        boolean result = !b1 && b2 || b3;   //false && true || false
        System.out.println(result);
        System.out.println(true && false || true);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int i = scan.nextInt();
        System.out.println(i);
    }
}
