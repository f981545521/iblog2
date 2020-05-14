package design.observerpattern.demo2;

import java.util.Observable;

/**
 * @author youfang
 * @version [1.0.0, 2020/5/14]
 **/
public class WeatherData extends Observable {
    private float temperature;

    public void tempChanged() {
        setChanged();
        notifyObservers();
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
        tempChanged();
    }


}
