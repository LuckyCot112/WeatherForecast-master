package com.example.weatherforecast;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

// этот класс выводит данные в список
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private Context context;
    private List<WeatherItem> weatherItemList;

    public WeatherAdapter(Context context, List<WeatherItem> weatherItemList) {
        this.context = context;
        this.weatherItemList = weatherItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherItem weatherItem = weatherItemList.get(position);
        String formattedDateTime = DateUtils.formatDateTimeString(weatherItem.getDateTime());
        holder.dateTimeTextView.setText(formattedDateTime);

        // Установка цвета текста в зависимости от значения переменной
        int temperatureColor = getTemperatureColor(weatherItem.getTemperature());
        holder.temperatureTextView.setText("Температура: " + weatherItem.getTemperature() + "°C");
        holder.temperatureTextView.setTextColor(temperatureColor);

        holder.pressureTextView.setText("Давление: " + weatherItem.getPressure() + " гПа");

        // Установка влажности текста в зависимости от значения переменной
        int humidityColor = getHumidityColor(weatherItem.getHumidity());
        holder.humidityTextView.setText("Влажность: " + weatherItem.getHumidity() + "%");
        holder.humidityTextView.setTextColor(humidityColor);

        // Установка скорости ветра текста в зависимости от значения переменной
        int windSpeedColor = getWindSpeedColor(weatherItem.getWindSpeed());
        holder.windSpeedTextView.setText("Скорость ветра: " + weatherItem.getWindSpeed() + " м/с");
        holder.windSpeedTextView.setTextColor(windSpeedColor);

        // Загрузка иконки погоды
        Picasso.get().load(weatherItem.getWeatherIconUrl()).into(holder.weatherIconImageView);
    }

    // Динамический цвет текста для температуры
    private int getTemperatureColor(double temperature) {
        // Определение цвета температуры
        int redColor = modifyColor(Color.RED, calculateRedShadeTemperature(temperature));
        int blueColor = modifyColor(Color.BLUE, calculateBlueShadeTemperature(temperature));

        return temperature < 0 ? blueColor : redColor;
    }

    // Модификатор цвета для синего у температуры (при -50 максимально синий цвет, при 0 - белый)
    private float calculateBlueShadeTemperature(double temperature) {
        return (float) (((temperature * -1) + 10 ) / 60);
    }

    private float calculateRedShadeTemperature(double temperature) {
        return (float) ((temperature + 10) / 60.0);
    }

    private int getWindSpeedColor(double wind_speed) {
        return modifyColor(Color.RED, calculateRedShadeTemperature(wind_speed * 2));
    }

    private int getHumidityColor(double humidity) {
        int yellowColor = modifyColor(Color.YELLOW, calculateYellowShade(humidity));
        int blueColor = modifyColor(Color.BLUE, calculateBlueShade(humidity));

        return humidity < 40 ? yellowColor : blueColor;
    }

    private float calculateYellowShade(double humidity) {
        return (float) ((40 - humidity) / 40.0);
    }

    private float calculateBlueShade(double humidity) {
        return (float) ((humidity - 40) / 60.0);
    }

    private int modifyColor(int color, float factor) {
        // Изменение цвета с использованием ColorUtils
        return ColorUtils.blendARGB(Color.WHITE, color, factor);
    }


    @Override
    public int getItemCount() {
        return weatherItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTimeTextView, temperatureTextView, pressureTextView, humidityTextView, windSpeedTextView;
        ImageView weatherIconImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
            temperatureTextView = itemView.findViewById(R.id.temperatureTextView);
            pressureTextView = itemView.findViewById(R.id.pressureTextView);
            humidityTextView = itemView.findViewById(R.id.humidityTextView);
            windSpeedTextView = itemView.findViewById(R.id.windSpeedTextView);
            weatherIconImageView = itemView.findViewById(R.id.weatherIconImageView);
        }
    }
}