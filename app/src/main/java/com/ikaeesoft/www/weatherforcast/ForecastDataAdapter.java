package com.ikaeesoft.www.weatherforcast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ForecastDataAdapter extends RecyclerView.Adapter<ForecastDataAdapter.WeatherDataViewHolder> {

    private ArrayList<String> mDataset;

    private ListItemClickHandler listItemClickHandler;

    public interface ListItemClickHandler {
        void onItemClick(String weather);
    }


    public ForecastDataAdapter(ListItemClickHandler context) {
        listItemClickHandler = context;

    }

    public void setDataSet(ArrayList<String> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }


    //layout manager call the onCreateViewHolder and onBindViewHolder


    @NonNull
    @Override
    public WeatherDataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.forecast_list_item,viewGroup,shouldAttachToParentImmediately);

        return new WeatherDataViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull WeatherDataViewHolder weatherDataViewHolder, int index) {
        String weatherData = mDataset.get(index);
        weatherDataViewHolder.text.setText(weatherData);

    }

    @Override
    public int getItemCount() {
        if(!mDataset.isEmpty())
        return mDataset.size();

        return 0;
    }

    public   class WeatherDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView text;

        public WeatherDataViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.weather_data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int index = getAdapterPosition();
           String data= mDataset.get(index);
            Log.v("1",data);
            listItemClickHandler.onItemClick(data);
        }
    }
}
