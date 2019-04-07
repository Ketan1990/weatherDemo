package com.ikaeesoft.www.weatherforcast;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;

public class ForecastListFragment extends Fragment implements ForecastDataAdapter.ListItemClickHandler {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManganger;
    private ForecastDataAdapter mAdpter;
    private Context mcontext;

    private OnListFragmentInteractionListener mListener;

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

        String[] data = {
                "Mon 6/23 - Sunny - 31/17",
                "Tue 6/24 - Foggy - 21/8",
                "Wed 6/25 - Cloudy - 22/17",
                "Thurs 6/26 - Rainy - 18/11",
                "Fri 6/27 - Foggy - 21/10",
                "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
                "Sun 6/29 - Sunny - 20/7"
        };
        ArrayList<String> dataset = new ArrayList<String>(Arrays.asList(data));

        View rootView = inflater.inflate(R.layout.fragment_forecast,container,false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.weather_list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        recyclerView.setHasFixedSize(true);

        layoutManganger = new LinearLayoutManager(mcontext,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManganger);

        mAdpter = new ForecastDataAdapter(this);
        recyclerView.setAdapter(mAdpter);

        mAdpter.setDataSet(dataset);



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
}
