package design.interceptingfilterpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-10 上午 12:02]
 **/
public class FilterManager {
    FilterChain filterChain;

    public FilterManager(Target target){
        filterChain = new FilterChain();
        filterChain.setTarget(target);
    }
    public void setFilter(Filter filter){
        filterChain.addFilter(filter);
    }

    public void filterRequest(String request){
        filterChain.execute(request);
    }
}
