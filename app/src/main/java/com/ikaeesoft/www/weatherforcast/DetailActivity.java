package com.ikaeesoft.www.weatherforcast;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private TextView textView;

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
        String weather = intent.getStringExtra(getString(R.string.wether_data));
        textView = (TextView)findViewById(R.id.detail);
        textView.setText(weather);
    }
}
