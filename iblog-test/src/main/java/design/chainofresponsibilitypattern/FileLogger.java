package design.chainofresponsibilitypattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:03]
 **/
public class FileLogger extends AbstractLogger {

    public FileLogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("File::Logger: " + message);
    }
}
