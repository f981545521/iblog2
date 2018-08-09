package design.statepattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:39]
 **/
public class StopState implements State {

    public void doAction(Context context) {
        System.out.println("Player is in stop state");
        context.setState(this);
    }

    public String toString(){
        return "Stop State";
    }
}
