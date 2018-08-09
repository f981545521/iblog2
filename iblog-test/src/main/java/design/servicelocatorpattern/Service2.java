package design.servicelocatorpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-10 上午 12:06]
 **/
public class Service2 implements Service {
    public void execute(){
        System.out.println("Executing Service2");
    }

    @Override
    public String getName() {
        return "Service2";
    }
}
