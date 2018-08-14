package threadtest.staticthread2;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-14 上午 09:50]
 **/
public class StaticThread implements Runnable {
    @Override
    public void run() {
// TODO Auto-generated method stub
        StaticAction.incValue();
    }
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new StaticThread()).start();
        }
        try {
            Thread.sleep(1000); //预留足够的时间让上面的线程跑完
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(StaticAction.i);
    }
}
