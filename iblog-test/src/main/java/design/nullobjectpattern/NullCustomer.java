package design.nullobjectpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 09:15]
 **/
public class NullCustomer extends AbstractCustomer {

    @Override
    public String getName() {
        return "Not Available in Customer Database";
    }

    @Override
    public boolean isNil() {
        return true;
    }
}
