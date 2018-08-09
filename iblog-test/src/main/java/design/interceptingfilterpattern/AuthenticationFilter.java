package design.interceptingfilterpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-10 上午 12:01]
 **/
public class AuthenticationFilter implements Filter {
    public void execute(String request){
        System.out.println("Authenticating request: " + request);
    }
}
