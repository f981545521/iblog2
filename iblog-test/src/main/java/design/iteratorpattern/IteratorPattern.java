package design.iteratorpattern;

/**
 * 迭代器模式（Iterator Pattern）。
 * 这种模式用于顺序访问集合对象的元素，不需要知道集合对象的底层表示。
 * 迭代器模式属于行为型模式。
 * @author youfang
 * @version [1.0.0, 2018-08-08 下午 12:10]
 **/
public class IteratorPattern {
    public static void main(String[] args) {
        NameRepository namesRepository = new NameRepository();

        for(Iterator iter = namesRepository.getIterator(); iter.hasNext();){
            String name = (String)iter.next();
            System.out.println("Name : " + name);
        }
    }
}
