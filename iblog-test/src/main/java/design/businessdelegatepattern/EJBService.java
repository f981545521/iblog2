package design.businessdelegatepattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:51]
 **/
public class EJBService implements BusinessService {

    @Override
    public void doProcessing() {
        System.out.println("Processing task by invoking EJB Service");
    }
}
