package design.observerpattern.demo2;

import java.util.Observable;
import java.util.Observer;

/**
 * @author youfang
 * @version [1.0.0, 2020/5/14]
 **/
public class Watcher1 implements Observer {

    private float temperature;

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof WeatherData) {
            WeatherData wd = (WeatherData) o;
            this.temperature = wd.getTemperature();
            display();
        }
    }

    public void display() {
        System.out.println("当前温度：" + this.temperature + "摄氏度");
    }

}
