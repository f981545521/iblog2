package design.builderpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 09:57]
 **/
public class Bottle implements Packing {

    @Override
    public String pack() {
        return "Bottle";
    }
}
