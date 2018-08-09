package design.statepattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:39]
 **/
public class Context {
    private State state;

    public Context(){
        state = null;
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }
}
