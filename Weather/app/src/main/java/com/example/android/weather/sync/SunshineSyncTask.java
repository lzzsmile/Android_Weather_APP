package com.example.android.weather.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.text.format.DateUtils;

import com.example.android.weather.data.SunshinePreferences;
import com.example.android.weather.data.WeatherContract;
import com.example.android.weather.utilities.NetworkUtils;
import com.example.android.weather.utilities.NotificationUtils;
import com.example.android.weather.utilities.OpenWeatherJsonUtils;

import java.net.URL;

/**
 * Created by zhuangzhili on 2018-01-09.
 */

public class SunshineSyncTask {

    synchronized  public static void syncWeather(Context context) {
        try {
            URL weatherRequestUrl = NetworkUtils.getUrl(context);
            String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
            ContentValues[] weatherValues = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, jsonWeatherResponse);
            if (weatherValues != null && weatherValues.length != 0) {
                ContentResolver sunshineContentResolver = context.getContentResolver();
                sunshineContentResolver.delete(WeatherContract.WeatherEntry.CONTENT_URI, null, null);
                sunshineContentResolver.bulkInsert(WeatherContract.WeatherEntry.CONTENT_URI, weatherValues);
                boolean notificationsEnabled = SunshinePreferences.areNotificationsEnabled(context);
                long timeSinceLastNotification = SunshinePreferences.getEllapsedTimeSinceLastNotification(context);
                boolean oneDayPassedSinceLastNotification = false;
                if (timeSinceLastNotification >= DateUtils.DAY_IN_MILLIS) {
                    oneDayPassedSinceLastNotification = true;
                }

                if (notificationsEnabled && oneDayPassedSinceLastNotification) {
                    NotificationUtils.notifyUserOfNewWeather(context);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
