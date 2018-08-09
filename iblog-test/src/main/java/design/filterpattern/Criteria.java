package design.filterpattern;

import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 09:24]
 **/
public interface Criteria {
    public List<Person> meetCriteria(List<Person> persons);
}
