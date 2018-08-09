package design.builderpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 09:58]
 **/
public class ChickenBurger extends Burger {

    @Override
    public float price() {
        return 50.5f;
    }

    @Override
    public String name() {
        return "Chicken Burger";
    }
}
