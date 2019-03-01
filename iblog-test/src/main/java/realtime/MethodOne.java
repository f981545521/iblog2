package realtime;

/**
 * @author youfang
 * @version [1.0.0, 2019-03-01 下午 05:02]
 **/
public class MethodOne {
    public static void main(String[] args) {
        // run in a second
        final long timeInterval = 1000;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // ------- code for task to run
                    System.out.println("Hello !!");
                    // ------- ends here
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
