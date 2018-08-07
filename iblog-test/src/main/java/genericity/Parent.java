package genericity;

import java.io.Serializable;

/**
 * 泛型测试
 * @author youfang
 * @version [1.0.0, 2018-08-07 上午 10:10]
 **/
public class Parent implements Serializable {
    private static final long serialVersionUID = 2481080969456716862L;

    protected String surname;//姓氏

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Parent{");
        sb.append("surname='").append(surname).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
