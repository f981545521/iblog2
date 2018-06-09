package cn.acyou.iblog.so;

import cn.acyou.iblog.annotation.BaseValid;

/**
 * @author youfang
 * @date 2018-03-31 下午 02:17
 **/
public class ActiveCodeSo extends So{

    private static final long serialVersionUID = -2609494598723815479L;

    @BaseValid
    private String email;

    private String used;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }
}
