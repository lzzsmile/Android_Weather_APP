package com.example.android.weather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.weather.data.SunshinePreferences;
import com.example.android.weather.utilities.NetworkUtils;
import com.example.android.weather.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements ForecastAdapter.ForecastAdapterOnClickHandler {

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private RecyclerView mRecyclerView;

    private ForecastAdapter mForecastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_forecast);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mForecastAdapter = new ForecastAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mForecastAdapter);

        loadWeatherData();
    }

    @Override
    public void onClick(String weatherForDay) {
        Context context = this;
        Toast.makeText(context, weatherForDay, Toast.LENGTH_SHORT).show();
    }

    private void loadWeatherData() {
        showWeatherDataView();
        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        new FetchWeatherTask().execute(location);
    }

    private void showWeatherDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }

            String location = strings[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
                String[] simpleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);
                return simpleJsonWeatherData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] strings) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (strings != null) {
                showWeatherDataView();
                mForecastAdapter.setWeatherData(strings);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.forecast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            mForecastAdapter.setWeatherData(null);
            loadWeatherData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
