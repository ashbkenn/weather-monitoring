package edu.iu.habahram.weathermonitoring.model;

import org.springframework.stereotype.Component;

@Component
public class HeatIndexDisplay implements Observer, DisplayElement {
    private float temperature;
    private float humidity;
    private float pressure;

    private Subject weatherData;

    public HeatIndexDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this); // Subscribe upon creation
    }

    private float computeHeatIndex(float t, float rh) {
        return (float) (
                16.923 + 0.185212 * t + 5.37941 * rh - 0.100254 * t * rh
                        + 0.00941695 * (t * t) + 0.00728898 * (rh * rh)
                        + 0.000345372 * (t * t * rh) - 0.000814971 * (t * rh * rh)
                        + 0.0000102102 * (t * t * t) - 0.000038646 * (t * t * t * rh)
                        + 0.0000291583 * (rh * rh * rh) + 0.00000142721 * (t * t * t * rh * rh)
                        + 0.000000197483 * (t * rh * rh * rh) - 0.0000000218429 * (t * t * t * rh * rh * rh)
                        + 0.000000000843296 * (t * t * rh * rh * rh * rh)
                        - 0.0000000000481975 * (t * t * t * rh * rh * rh * rh));
    }

    @Override
    public String display() {
        float heatIndex = computeHeatIndex(temperature, humidity);
        String html = "<div style=\"background-image: url(/images/sky.webp); " +
                "height: 400px; width: 647.2px; display:flex; flex-wrap:wrap; " +
                "justify-content:center; align-content:center;\">";
        html += "<section>";
        html += String.format("<label>Average Temperature: %.2f</label><br />", temperature);
        html += String.format("<label>Average Humidity: %.2f</label><br />", humidity);
        html += String.format("<label>Heat Index: %.2f</label><br />", heatIndex);
        html += String.format("<label>Average Pressure: %.2f</label>", pressure);
        html += "</section>";
        html += "</div>";
        return html;
    }

    @Override
    public String name() {
        return "Heat Index Display";
    }

    @Override
    public String id() {
        return "heatIndex";
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public void subscribe() {
        weatherData.registerObserver(this);
    }

    public void unsubscribe() {
        weatherData.removeObserver(this);
    }

}
