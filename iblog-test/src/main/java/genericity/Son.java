package genericity;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-07 上午 10:12]
 **/
public class Son extends Parent {
    private static final long serialVersionUID = 9205547756939465277L;

    private String appellation;//名字

    public Son() {

    }
    public Son(String appellation) {
        this.appellation = appellation;
    }

    public Son(String appellation, String surname) {
        this.appellation = appellation;
        super.surname = surname;
    }

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Son{");
        sb.append("appellation='").append(appellation).append('\'');
        if (StringUtils.isNotEmpty(surname)){
            sb.append("surname='").append(surname).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }
}
