package design.interceptingfilterpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-10 上午 12:02]
 **/
public class Target {
    public void execute(String request){
        System.out.println("Executing request: " + request);
    }
}
