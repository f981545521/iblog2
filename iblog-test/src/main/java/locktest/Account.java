package locktest;

import java.io.Serializable;

/**
 * @author youfang
 * @version [1.0.0, 2018-09-07 下午 03:12]
 **/
public class Account implements Serializable {
    private static final long serialVersionUID = 1165406767773791511L;
    private Long accountId;//账户

    private String name;//户主

    private Double balance;//余额

    public Account() {
    }

    public Account(Long accountId, String name, Double balance) {
        this.accountId = accountId;
        this.name = name;
        this.balance = balance;
    }

    //存钱
    public void deposit(float amt) {
        balance += amt;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //取钱
    public void withdraw(float amt) {
        balance -= amt;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Account{");
        sb.append("accountId=").append(accountId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", balance=").append(balance);
        sb.append('}');
        return sb.toString();
    }
}
