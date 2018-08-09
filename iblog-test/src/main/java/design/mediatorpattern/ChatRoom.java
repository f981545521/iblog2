package design.mediatorpattern;

import java.util.Date;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:31]
 **/
public class ChatRoom {
    public static void showMessage(User user, String message){
        System.out.println(new Date().toString()
                + " [" + user.getName() +"] : " + message);
    }
}
