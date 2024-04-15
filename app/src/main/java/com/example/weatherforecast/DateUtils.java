package com.example.weatherforecast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    // Метод для форматирования строки с датой и временем
    public static String formatDateTimeString(String inputDateTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());

            Date date = inputFormat.parse(inputDateTime);
            return outputFormat.format(date);
        } catch (ParseException e) {
        }
        return "";
    }
}
