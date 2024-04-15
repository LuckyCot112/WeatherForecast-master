package com.example.weatherforecast;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// основной класс программы
public class MainActivity extends AppCompatActivity {
    private Spinner citySpinner;
    private Button getWeatherButton;
    private RecyclerView recyclerView;
    private WeatherAdapter weatherAdapter;

    // Карта с координатами городов
    private Map<String, String[]> cityCoordinates;

    // тут обрабатываются элементы формы
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        citySpinner = findViewById(R.id.citySpinner);
        getWeatherButton = findViewById(R.id.getWeatherButton);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Инициализация карты с координатами городов
        cityCoordinates = new HashMap<>();
        cityCoordinates.put("Курган", new String[]{"55.2700", "65.2000"});
        cityCoordinates.put("Тюмень", new String[]{"57.1522", "65.5272"});
        cityCoordinates.put("Ханты-Мансийск", new String[]{"61.0042", "69.0019"});
        cityCoordinates.put("Челябинск", new String[]{"55.0944", "61.2411"});
        cityCoordinates.put("Москва", new String[]{"55.7558", "37.6173"});
        cityCoordinates.put("Санкт-Петербург", new String[]{"59.9343", "30.3351"});
        cityCoordinates.put("Екатеринбург", new String[]{"56.8380", "60.5970"});
        cityCoordinates.put("Новосибирск", new String[]{"55.0084", "82.9357"});
        cityCoordinates.put("Красноярск", new String[]{"56.0153", "92.8932"});


        // Задаем список городов
        Set<String> citySet = cityCoordinates.keySet();
        String[] cities = citySet.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        citySpinner.setAdapter(adapter);

        // Обработчик нажатия кнопки
        getWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName = (String) citySpinner.getSelectedItem();
                String[] coordinates = cityCoordinates.get(cityName);
                if (coordinates != null) {
                    fetchWeatherData(coordinates[0], coordinates[1]);
                }
            }
        });
    }

    // функция выполняет запрос в интернет
    @SuppressLint("StaticFieldLeak")
    private void fetchWeatherData(String lat, String lon) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&units=metric&lang=ru&appid=64170cb0a78e4c5dfe5260327b5b3181";
        new FetchWeatherTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(List<WeatherItem> weatherItems) {
                super.onPostExecute(weatherItems);

                if (weatherItems != null && !weatherItems.isEmpty()) {
                    weatherAdapter = new WeatherAdapter(MainActivity.this, weatherItems);
                    recyclerView.setAdapter(weatherAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Не удалось получить данные", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(apiUrl);
    }
}