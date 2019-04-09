package com.ikaeesoft.www.weatherforcast.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.HttpUrl;
import okhttp3.internal.http2.Http2Connection;

public class WeatherJsonParser {
    private static final String COD = "cod";
    private static final String LIST = "list";
    private static final String WEATHER_TEMPRETURE = "main";
    private static final String WEATHRER_MAX = "temp_max";
    private static final String WEATHER_MIN = "temp_min";
    private static final String OWN_WEATHER = "weather";
    private static final String OWN_DESCIRPTION = "description";

    public static ArrayList<String> getSimpleWeatherStringsFromJson(Context context, String forecastJsonStr)
            throws JSONException {

        ArrayList<String> weatherFoercastList = new ArrayList<String>();
        JSONObject jsonObject = new JSONObject(forecastJsonStr);
        if(jsonObject.has(COD)){
            int responseCode = jsonObject.getInt(COD);
            switch (responseCode){
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return  null;
            }
        }

        long localDate = System.currentTimeMillis();
        long utcDate = SunshineDateUtils.getUTCDateFromLocal(localDate);
        long startDay = SunshineDateUtils.normalizeDate(utcDate);


        JSONArray weatherArray = jsonObject.getJSONArray(LIST);
        for (int i = 0; i < weatherArray.length(); i++) {
            /* Get the JSON object representing the day */
            JSONObject dayForecast = weatherArray.getJSONObject(i);


            /*Date  is generated in locally */
            long dateTimeMillis = startDay + SunshineDateUtils.DAY_IN_MILLIS * i;
            String date = SunshineDateUtils.getFriendlyDateString(context, dateTimeMillis, false);



            JSONObject temperatureObject = dayForecast.getJSONObject(WEATHER_TEMPRETURE);
            double maxTemp = temperatureObject.getDouble(WEATHRER_MAX);
            double minTemp = temperatureObject.getDouble(WEATHER_MIN);
            String highAndLow = SunshineWeatherUtils.formatHighLows(context, maxTemp, minTemp);

            JSONObject descriptionObject = dayForecast.getJSONArray(OWN_WEATHER).getJSONObject(0);
            String description = descriptionObject.getString(OWN_DESCIRPTION);

            String weatherStatement = date + " - " + description + " - " + highAndLow;
            weatherFoercastList.add(weatherStatement);
        }

       return weatherFoercastList;
    }
}
