package design.mementopattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:34]
 **/
public class Memento {
    private String state;

    public Memento(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }
}
