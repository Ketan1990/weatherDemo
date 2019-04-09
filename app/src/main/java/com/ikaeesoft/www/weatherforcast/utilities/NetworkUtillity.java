package com.ikaeesoft.www.weatherforcast.utilities;

import android.util.Log;

import java.io.IOException;
import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class NetworkUtillity {
    OkHttpClient client = new OkHttpClient();
    public static final String OPEN_WEATHER_MAP_API_KEY = "59517451c7bba571de5bda0aced99dc1";

    // private final String FORECAST_BASE_URL_Duplicate = "https://api.openweathermap.org/data/2.5/forecast?q=94043&mode=json&units=metric&cnt=7";
    private static final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast";

    public static String  apiKey = "&appid=" + OPEN_WEATHER_MAP_API_KEY;

    private static final String TAG = NetworkUtillity.class.getSimpleName();

    /* The format we want our API to return */
    private static final String format = "json";
    /* The units we want our API to return */
    private static final String units = "metric";
    /* The number of days we want our API to return */
    private static final int numDays = 7;

    final static String QUERY_PARAM = "q";
    final static String LAT_PARAM = "lat";
    final static String LON_PARAM = "lon";
    final static String FORMAT_PARAM = "mode";
    final static String UNITS_PARAM = "units";
    final static String DAYS_PARAM = "cnt";

    public static Request buildWeatherRequestUrl(String locationQuery) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse( FORECAST_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(QUERY_PARAM,locationQuery);
        urlBuilder.addQueryParameter(FORMAT_PARAM,format);
        urlBuilder.addQueryParameter(UNITS_PARAM,units);
        urlBuilder.addQueryParameter(DAYS_PARAM,Integer.toString(numDays));
        urlBuilder.build();

        return new Request.Builder().url(urlBuilder.toString().concat(apiKey)).build();

    }

    public static URL buildUrl(Double lat, Double lon) {
        return null;
    }


    public static String getResponseFromHttpUrl(Request request) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
