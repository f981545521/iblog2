package exceptiontest;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-20 上午 09:51]
 **/
public class SimpleExceptionTest {
    public static void main(String[] args) {
        Integer count = 8;
        boolean inauspicious = true;//默认不顺利
        do {
            try {
                System.out.println("--------------------------------------");
                for (int i = 0; i < 10; i++){
                    System.out.println(i);
                    if (i == count){
                        throw new Exception();
                    }
                }
                inauspicious = false;
            }catch (Exception e){
                System.out.println("捕捉到异常" + count);
                count = 20;
            }
            System.out.println("执行完成" + count);
        }while (inauspicious);
    }
}
