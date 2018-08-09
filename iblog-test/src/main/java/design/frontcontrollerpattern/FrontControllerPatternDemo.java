package design.frontcontrollerpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:59]
 **/
public class FrontControllerPatternDemo {
    public static void main(String[] args) {
        FrontController frontController = new FrontController();
        frontController.dispatchRequest("HOME");
        frontController.dispatchRequest("STUDENT");
    }
}
