package com.convert.cv;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class Area extends AppCompatActivity {

    Factory f;
    View root;
    AreaAdapter areaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_area);



        f = (Factory)getApplication();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == RecyclerView.SCROLL_STATE_IDLE) f.setScroll(false);
                else f.setScroll(true);

            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LinearDecoration(f.getLabelMas(), getResources().getDisplayMetrics().density));

        areaAdapter = new AreaAdapter(this, recyclerView, f);
        recyclerView.setAdapter(areaAdapter);

        root = findViewById(R.id.root);
        setListenerToRootView();


        new Thread(){

            @Override
            public void run() {
                loadAd();
            }

        }.start();


        Toolbar bar = findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        ((TextView)bar.getChildAt(0)).setText(f.getTitle());

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }



   private void setListenerToRootView() {

        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                areaAdapter.update();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_set, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.setting) startActivity(new Intent(this, SettingActivity.class));
        else finish();
        return true;
    }



    private void loadAd(){

      final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;

        MobileAds.initialize(this,"ca-app-pub-8602975786926645~8596990222");

        final AdView ads = new AdView(this);

        ads.setAdSize(AdSize.SMART_BANNER);
        ads.setAdUnitId("ca-app-pub-8602975786926645/1423532626");

        ads.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                root.setPadding(0,0,0, ads.getHeight());

            }
        });

       final AdRequest adRequest = new AdRequest.Builder().addTestDevice("88F766123564FF6AC339DE6384ED2744").build();

        runOnUiThread(new Runnable()
        {
            @Override
            public void run() {
               ads.loadAd(adRequest);
                getWindow().addContentView(ads,layoutParams);

            }
        });


    }

}