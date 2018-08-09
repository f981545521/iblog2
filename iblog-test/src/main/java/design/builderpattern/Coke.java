package design.builderpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 09:58]
 **/
public class Coke extends ColdDrink {

    @Override
    public float price() {
        return 30.0f;
    }

    @Override
    public String name() {
        return "Coke";
    }
}
