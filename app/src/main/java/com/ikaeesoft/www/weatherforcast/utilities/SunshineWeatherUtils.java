package com.ikaeesoft.www.weatherforcast.utilities;

import android.content.Context;

import com.ikaeesoft.www.weatherforcast.R;

public class SunshineWeatherUtils {

    public static String formatHighLows(Context context, double maxTemp, double minTemp) {
        long roundedHigh = Math.round(maxTemp);
        long roundedLow = Math.round(minTemp);

        String formatedHigh = formatTemperature(context,roundedHigh);
        String formatedLow = formatTemperature(context,roundedLow);
        String highLowStr = formatedHigh+"/"+formatedLow;

        return highLowStr;
    }

    private static String formatTemperature(Context context,double temperature) {
        int temperatureFormatResourceId = R.string.format_temperature_celsius;

        if (!SunShinePreference.isMetric(context)) {
            temperature = celsiusToFahrenheit(temperature);
            temperatureFormatResourceId = R.string.format_temperature_fahrenheit;
        }

        /* For presentation, assume the user doesn't care about tenths of a degree. */
        return String.format(context.getString(temperatureFormatResourceId), temperature);

    }

    private static double celsiusToFahrenheit(double temperatureInCelsius) {
        return (temperatureInCelsius * 1.8) + 32;
    }
}
