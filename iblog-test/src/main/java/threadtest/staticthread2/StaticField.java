package threadtest.staticthread2;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-14 上午 09:50]
 **/
public class StaticField {
    public static Integer count = 0;


    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(new ThreadRun()).start();
        }
        try {
            Thread.sleep(5000); //预留足够的时间让上面的线程跑完
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
class ThreadRun implements Runnable{
    @Override
    public void run() {
        synchronized (StaticField.count +=20){
            System.out.println(StaticField.count);
        }
    }
}