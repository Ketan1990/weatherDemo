package com.ikaeesoft.www.weatherforcast;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ikaeesoft.www.weatherforcast.utilities.SunShinePreference;

public class MainActivity extends AppCompatActivity implements ForecastListFragment.OnListFragmentInteractionListener
{
    private ForecastListFragment forecastFragment;

    private static final String  TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

         forecastFragment = new ForecastListFragment();
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,forecastFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_refresh:
                forecastFragment.restartLoader();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this,Setting.class);
                startActivity(intent);
                return true;
            case R.id.action_map:
                openLocationInMap();

            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }
    private void openLocationInMap() {
        String addressString = SunShinePreference.getPreferredWeatherLocation(this);
        Uri geoLocation = Uri.parse("geo:0,0?q=" + addressString);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(TAG, "Couldn't call " + geoLocation.toString() + ", no receiving apps installed!");
        }
    }



    @Override
    public void onFragmentInteraction(String data) {
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra(getString(R.string.wether_data),data);
        startActivity(intent);
    }
}
