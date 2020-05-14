package design.observerpattern.demo2;

/**
 * @author youfang
 * @version [1.0.0, 2020/5/14]
 **/
public class WeatherDataTest {
    public static void main(String[] args) {
        //实例化主题
        WeatherData wd = new WeatherData();
        //增加观察者
        wd.addObserver(new Watcher1());
        //每次改变温度时，都会通知观察者更新输出。

        wd.setTemperature(20);
        wd.setTemperature(22);
    }
}
