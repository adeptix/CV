package com.convert.cv;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);


        Toolbar bar = findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        ((TextView)bar.getChildAt(0)).setText(getString(R.string.menu_set));




    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
