package design.nullobjectpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 09:15]
 **/
public class RealCustomer extends AbstractCustomer {

    public RealCustomer(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isNil() {
        return false;
    }
}
