package threadtest;

/**
 * synchronized修饰对象
 * @author youfang
 * @date 2018-03-28 下午 06:03
 **/
public class ThreadTest4 {

    public static void main(String[] args) {
        /**
         * 在AccountOperator 类中的run方法里，我们用synchronized 给account对象加了锁。
         * 这时，当一个线程访问account对象时，其他试图访问account对象的线程将会阻塞，直到该线程访问account对象结束。
         * 也就是说谁拿到那个锁谁就可以运行它所控制的那段代码。
         *
         * 这里： 其他线程访问对象属性的时候，会等待当前对象锁被释放。
         */
        Account account = new Account("zhang san", 1000.0f);
        AccountOperator accountOperator = new AccountOperator(account);

        final int THREAD_NUM = 2;
        Thread threads[] = new Thread[THREAD_NUM];
        for (int i = 0; i < THREAD_NUM; i ++) {
            threads[i] = new Thread(accountOperator, "Thread" + i);
            threads[i].start();
        }
    }
}
/**
 * 银行账户类
 */
class Account {
    String name;
    float amount;

    public Account(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }
    //存钱
    public  void deposit(float amt) {
        amount += amt;
        try {
            System.out.println(Thread.currentThread().getName() + "存后：" + amount);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //取钱
    public  void withdraw(float amt) {
        amount -= amt;
        try {
            System.out.println(Thread.currentThread().getName() + "取后：" + amount);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public float getBalance() {
        return amount;
    }
}

/**
 * 账户操作类
 */
class AccountOperator implements Runnable{
    private Account account;
    public AccountOperator(Account account) {
        this.account = account;
    }

    public void run() {
        synchronized (account) {
            account.deposit(500);
            account.withdraw(500);
            System.out.println(Thread.currentThread().getName() + ":" + account.getBalance());
        }
    }
}