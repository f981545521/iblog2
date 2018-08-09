package design.interceptingfilterpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-10 上午 12:02]
 **/
public class Client {
    FilterManager filterManager;

    public void setFilterManager(FilterManager filterManager){
        this.filterManager = filterManager;
    }

    public void sendRequest(String request){
        filterManager.filterRequest(request);
    }
}
