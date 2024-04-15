package com.example.weatherforecast;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// класс позволяет получать информацию из инета асинхронно,
// то есть не блокируя работу основного приложения
public class FetchWeatherTask extends AsyncTask<String, Void, List<WeatherItem>> {

    @Override
    protected List<WeatherItem> doInBackground(String... params) {
        String urlString = params[0];
        List<WeatherItem> weatherItemList = new ArrayList<>();

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("list");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject weatherObject = jsonArray.getJSONObject(i);
                String dateTime = weatherObject.getString("dt_txt");
                JSONObject main = weatherObject.getJSONObject("main");
                double temperature = main.getDouble("temp");
                double pressure = main.getDouble("pressure");
                int humidity = main.getInt("humidity");
                JSONObject wind = weatherObject.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");
                JSONArray weatherArray = weatherObject.getJSONArray("weather");
                JSONObject weather = weatherArray.getJSONObject(0);
                String weatherIcon = weather.getString("icon");
                String weatherIconUrl = "https://openweathermap.org/img/wn/" + weatherIcon + ".png";
                WeatherItem weatherItem = new WeatherItem(dateTime, temperature, pressure, humidity, windSpeed, weatherIconUrl);
                weatherItemList.add(weatherItem);
            }

        } catch (IOException | JSONException e) {
        }
        // в ответе выдается погода через 3 часа, но это слишком часто
        // берем погоду через 6 часов
        List<WeatherItem> outItemList = new ArrayList<>();
        for (int i = 0; i < weatherItemList.size(); i++) {
            if(i % 2 == 0)
                outItemList.add(weatherItemList.get(i));
        }
        return outItemList;
    }
}