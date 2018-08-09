package design.builderpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 09:58]
 **/
public abstract class ColdDrink implements Item {

    @Override
    public Packing packing() {
        return new Bottle();
    }

    @Override
    public abstract float price();
}
