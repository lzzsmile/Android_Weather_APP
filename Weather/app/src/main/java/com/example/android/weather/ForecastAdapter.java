package com.example.android.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhuangzhili on 2018-01-08.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder>{

    private String[] mWeatherData;

    private final ForecastAdapterOnClickHandler mClickHandler;

    public interface ForecastAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }

    public ForecastAdapter(ForecastAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /*cache of the children views for a forecast list item*/
    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mWeatherTextView;

        public ForecastAdapterViewHolder(View view) {
            super(view);
            mWeatherTextView = (TextView) view.findViewById(R.id.tv_weather_data);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = mWeatherData[adapterPosition];
            mClickHandler.onClick(weatherForDay);
        }
    }

    /*return a new ForecastAdapterViewHolder that holds the view for each list item. Called when new ViewHOlder is created*/
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.forecast_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ForecastAdapterViewHolder(view);
    }

    /*called by the recyclerview to display the data at the specified position*/
    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder holder, int position) {
        String weatherForThisDay = mWeatherData[position];
        holder.mWeatherTextView.setText(weatherForThisDay);
    }

    /*return the number of items available in the forecast*/
    @Override
    public int getItemCount() {
        if (mWeatherData == null) {
            return 0;
        }
        return mWeatherData.length;
    }

    /*save the weatherData to mWeatherData*/
    public void setWeatherData(String[] weatherData) {
        mWeatherData = weatherData;
        notifyDataSetChanged();
    }
}
