package design.nullobjectpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 09:15]
 **/
public abstract class AbstractCustomer {
    protected String name;
    public abstract boolean isNil();
    public abstract String getName();
}
