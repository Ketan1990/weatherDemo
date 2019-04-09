package com.ikaeesoft.www.weatherforcast;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ikaeesoft.www.weatherforcast.utilities.NetworkUtillity;
import com.ikaeesoft.www.weatherforcast.utilities.SunShinePreference;
import com.ikaeesoft.www.weatherforcast.utilities.WeatherJsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Request;

public class ForecastListFragment extends Fragment implements ForecastDataAdapter.ListItemClickHandler {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManganger;
    private ForecastDataAdapter mAdpter;
    private Context mcontext;

    private OnListFragmentInteractionListener mListener;
    private ProgressBar progressbar;
    private TextView mErrorMessageDisplay;

    public ForecastListFragment(){

    }

    @Override
    public void onItemClick(String weatherData) {
        mListener.onFragmentInteraction(weatherData);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_forecast,container,false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.weather_list);
        progressbar = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator);

        mErrorMessageDisplay = (TextView) rootView.findViewById(R.id.tv_error_message_display);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        recyclerView.setHasFixedSize(true);

        layoutManganger = new LinearLayoutManager(mcontext,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManganger);

        showWeatherData();
        mAdpter = new ForecastDataAdapter(this);
        recyclerView.setAdapter(mAdpter);

        new FetchWeatherTask().execute(SunShinePreference.getPreferredWeatherLocation(getContext()));



        return rootView;

    }
    public void onAttach(Context context) {
        Log.v("Info","Attached");

        if (context instanceof ForecastListFragment.OnListFragmentInteractionListener) {
            mListener = (ForecastListFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String data);
    }
    public class FetchWeatherTask extends AsyncTask<String,Void,ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            progressbar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            String location = SunShinePreference.getPreferredWeatherLocation(getActivity());

            Request request = NetworkUtillity.buildWeatherRequestUrl(location);
            try {
                String weatherDataJson = NetworkUtillity.getResponseFromHttpUrl(request);
                ArrayList<String> weatherData = WeatherJsonParser.getSimpleWeatherStringsFromJson(getActivity(),weatherDataJson);
                return  weatherData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            progressbar.setVisibility(View.INVISIBLE);
            if(strings != null) {
                showWeatherData();
                mAdpter.setDataSet(strings);

            }else{
                showErrorMessage();
            }
            super.onPostExecute(strings);
        }


    }

    private void showWeatherData() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // COMPLETED (44) Show mRecyclerView, not mWeatherTextView
        /* Then, make sure the weather data is visible */
        recyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {
        /* First, hide the currently visible data */
        recyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
