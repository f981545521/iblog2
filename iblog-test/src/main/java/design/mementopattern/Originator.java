package design.mementopattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:34]
 **/
public class Originator {
    private String state;

    public void setState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }

    public Memento saveStateToMemento(){
        return new Memento(state);
    }

    public void getStateFromMemento(Memento Memento){
        state = Memento.getState();
    }
}