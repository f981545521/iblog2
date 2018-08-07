package genericity;

import org.apache.commons.lang3.StringUtils;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-07 上午 10:13]
 **/
public class Daughter extends Parent{
    private static final long serialVersionUID = -8913791202832612806L;

    private String appellation;//名字

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
