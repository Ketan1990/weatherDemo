package com.ikaeesoft.www.weatherforcast;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;
import java.util.zip.Inflater;

public class DetailActivity extends AppCompatActivity {

    private TextView textView;
    private String weather;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar toolbar = getSupportActionBar();
        if( toolbar != null){
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        weather = intent.getStringExtra(getString(R.string.wether_data));
        textView = (TextView)findViewById(R.id.detail);
        textView.setText(weather);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater().inflate(R.menu.menu2, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, weather);
            sendIntent.setType("text/plain");

            startActivity(Intent.createChooser(sendIntent, getString(R.string.send_to)));
        }
        return super.onOptionsItemSelected(item);
    }
}
