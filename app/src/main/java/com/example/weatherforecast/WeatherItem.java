package com.example.weatherforecast;

// класс для хранения записи о погоде
public class WeatherItem {
    private String dateTime;
    private double temperature;
    private double pressure;
    private int humidity;
    private double windSpeed;
    private String weatherIconUrl;

    public WeatherItem(String dateTime, double temperature, double pressure, int humidity, double windSpeed, String weatherIconUrl) {
        this.dateTime = dateTime;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.weatherIconUrl = weatherIconUrl;
    }


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(String weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }
}
