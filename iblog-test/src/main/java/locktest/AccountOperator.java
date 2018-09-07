package locktest;

/**
 * @author youfang
 * @version [1.0.0, 2018-09-07 下午 03:17]
 **/
public class AccountOperator implements Runnable {

    private Account account;

    public AccountOperator(Account account) {
        this.account = account;
    }

    public static void main(String[] args) {
        Account account = new Account(100001L, "zhang san", 10000.00);
        AccountOperator accountOperator = new AccountOperator(account);

        final int THREAD_NUM = 5;
        Thread threads[] = new Thread[THREAD_NUM];
        for (int i = 0; i < THREAD_NUM; i ++) {
            threads[i] = new Thread(accountOperator, "Thread" + i);
            threads[i].start();
        }

    }


    @Override
    public void run() {
        /* 未加锁 */
/*        account.deposit(500);
        account.withdraw(500);
        System.out.println(Thread.currentThread().getName() + ":" + account.getBalance());*/
        /* 加对象锁 */
/*        synchronized (account) {
            account.deposit(500);
            account.withdraw(500);
            System.out.println(Thread.currentThread().getName() + ":" + account.getBalance());
        }*/
        /* 加类锁 */
/*        synchronized (Account.class) {
            account.deposit(500);
            account.withdraw(500);
            System.out.println(Thread.currentThread().getName() + ":" + account.getBalance());
        }*/
        /* this代表当前类 */
        synchronized (this) {
            account.deposit(500);
            System.out.println(Thread.currentThread().getName() + ":" + account.getBalance());
            account.withdraw(500);
            System.out.println(Thread.currentThread().getName() + ":" + account.getBalance());
        }
    }
}
