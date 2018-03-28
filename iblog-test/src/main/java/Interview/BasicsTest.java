package Interview;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author youfang
 * @date 2018-03-24 下午 07:29
 **/
public class BasicsTest {

    @Test
    public void test1(){
        System.out.println(2<<4);//16  2*2^3
    }

    @Test
    public void test2(){
        short s1 = 1;
        //s1 = s1 + 1;   s1 + 1 的结果是int型，需要强制类型转换
        s1 += 1; //不会报错，会强制把1转为short型
        System.out.println(s1);
    }

    //一个文件中可以存在多个类。但是只能有一个public类，并且此public类必须与文件名相同

    /**
     * 将给定的数组倒序
     */
    @Test
    public void test3(){
        int[] arr = {2,4,67,32,76,88};
        int index = 0;
        int[] result = new int[arr.length];
        for (int i = arr.length -1 ;i >= 0 ;i--){
            result[index] = arr[i];
            index ++;
        }
        System.out.println(Arrays.toString(result));
    }
}