package design.chainofresponsibilitypattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:03]
 **/
public class ErrorLogger extends AbstractLogger {

    public ErrorLogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Error Console::Logger: " + message);
    }
}