package design.chainofresponsibilitypattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:03]
 **/
public class ConsoleLogger extends AbstractLogger {

    public ConsoleLogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Standard Console::Logger: " + message);
    }
}
