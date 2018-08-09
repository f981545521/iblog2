package design.statepattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:39]
 **/
public class StartState implements State {

    public void doAction(Context context) {
        System.out.println("Player is in start state");
        context.setState(this);
    }

    public String toString(){
        return "Start State";
    }
}
