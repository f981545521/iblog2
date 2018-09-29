package algorithmtest;

import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * 算法
 * @author youfang
 * @version [1.0.0, 2018-09-27 下午 04:56]
 **/
public class Algorithm {

    private int[] numbers;

    @Before
    public void beforeTest(){
        int size = 10;
        int[] newNumbers = new int[size];
        for (int i = 0; i < size; i++){
            newNumbers[i] = RandomUtils.nextInt(1, 10000);
        }
        numbers = Arrays.copyOf(newNumbers, size);
        System.out.println("排序前：" + Arrays.toString(numbers));
    }

    @Test
    public void test1(){
        //bubbleSort(numbers);
        quickSort(numbers, 0, numbers.length - 1);
        //selectSort(numbers);
        //insertSort(numbers);
        //Arrays.sort(numbers);
    }

    @After
    public void afterTest(){
        System.out.println("排序后：" + Arrays.toString(numbers));
    }

    /**
     * 冒泡排序
     * 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
     * 针对所有的元素重复以上的步骤，除了最后一个。
     * 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     * @param numbers numbers
     */
    private static void bubbleSort(int[] numbers) {
        int temp; // 记录临时中间值 
        int size = numbers.length; // 数组大小 
        for (int i = 0; i < size - 1; i++) { 
            for (int j = i + 1; j < size; j++) {
                if (numbers[i] > numbers[j]) { // 交换两数的位置 > 从小到大；< 从大到小
                  temp = numbers[i];
                  numbers[i] = numbers[j];
                  numbers[j] = temp;
                }
            }
        }
    }

    /**
     * 快速排序
     * 从数列中挑出一个元素，称为“基准”
     * 重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分割之后，
     * 该基准是它的最后位置。这个称为分割（partition）操作。
     * 递归地把小于基准值元素的子数列和大于基准值元素的子数列排序。
     *
     * @param numbers
     * @param start
     * @param end
     */
    public static void quickSort(int[] numbers, int start, int end){
        if (start < end) {
            int base = numbers[start]; // 选定的基准值（第一个数值作为基准值）
            int temp; // 记录临时中间值
            int i = start, j = end;
            do {
                while ((numbers[i] < base) && (i < end))
                    i++;
                while ((numbers[j] > base) && (j > start))
                    j--;
                if (i <= j) {
                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                    i++;
                    j--;
                }
            } while (i <= j);

            if (start < j)
                quickSort(numbers, start, j);
            if (end > i)
                quickSort(numbers, i, end);
        }
    }


    /**
     * 选择排序
     * 在未排序序列中找到最小元素，存放到排序序列的起始位置
     * 再从剩余未排序元素中继续寻找最小元素，然后放到排序序列末尾。
     * 以此类推，直到所有元素均排序完毕。
     * @param numbers numbers
     */
    public static void selectSort(int[] numbers) {
        int size = numbers.length, temp;
        for (int i = 0; i < size; i++) {
            int k = i;
            for (int j = size - 1; j > i; j--){
                if (numbers[j] < numbers[k])   k = j;
            }
            temp = numbers[i];
            numbers[i] = numbers[k];
            numbers[k] = temp;
        }
    }


    /**
     * 插入排序
     *
     * 从第一个元素开始，该元素可以认为已经被排序
     * 取出下一个元素，在已经排序的元素序列中从后向前扫描
     * 如果该元素（已排序）大于新元素，将该元素移到下一位置
     * 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置
     * 将新元素插入到该位置中
     * 重复步骤2
     * @param numbers
     */
    public static void insertSort(int[] numbers) {
        int size = numbers.length, temp, j;
        for(int i=1; i<size; i++) {
            temp = numbers[i];
            for(j = i; j > 0 && temp < numbers[j-1]; j--)
                numbers[j] = numbers[j-1];
            numbers[j] = temp;
        }
    }


}
