package design.abstractfactorypattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 09:45]
 **/
public class Red implements Color {

    @Override
    public void fill() {
        System.out.println("Inside Red::fill() method.");
    }
}
