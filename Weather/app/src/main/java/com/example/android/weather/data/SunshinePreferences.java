package com.example.android.weather.data;

import android.content.Context;

/**
 * Created by zhuangzhili on 2018-01-07.
 */

public class SunshinePreferences {

    public static final String PREF_CITY_NAME = "city_name";

    public static final String PREF_COORD_LAT = "coord_lat";
    public static final String PREF_COORD_LONG = "coord_long";

    private static final String DEFAULT_WEATHER_LOCATION = "94043,USA";
    private static final double[] DEFAULT_WEATHER_COORDINATES = {37.4284, 122.0724};
    private static final String DEFAULT_MAP_LOCATION = "1600 Amphitheatre Parkway, Mountain View, CA 94043";

    public static void setLocationDetails(Context c, String cityName, double lat, double lon) {

    }

    public static void setLocation(Context c, String locationSetting, double lat, double lon) {

    }

    public static void resetLocationCoordinates(Context c) {

    }

    public static String getPreferredWeatherLocation(Context context) {
        return getDefaultWeatherLocation();
    }

    public static boolean isMetric(Context context) {
        return true;
    }

    public static double[] getLocationCoordinates(Context context) {
        return getDefaultWeatherCoordinates();
    }

    public static boolean isLocationLatLonAvailable(Context context) {
        return false;
    }

    private static String getDefaultWeatherLocation() {
        return DEFAULT_WEATHER_LOCATION;
    }

    private static double[] getDefaultWeatherCoordinates() {
        return DEFAULT_WEATHER_COORDINATES;
    }
}
