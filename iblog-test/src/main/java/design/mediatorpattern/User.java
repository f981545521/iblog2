package design.mediatorpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:31]
 **/
public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name){
        this.name  = name;
    }

    public void sendMessage(String message){
        ChatRoom.showMessage(this,message);
    }
}