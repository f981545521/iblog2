package design.builderpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 09:57]
 **/
public interface Item {
    public String name();
    public Packing packing();
    public float price();
}
