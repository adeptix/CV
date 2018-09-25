package com.convert.cv;


import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity{


      private int realWidth;
      private String url;

      int columner(){
          DisplayMetrics res = Resources.getSystem().getDisplayMetrics();

          int width = res.widthPixels;
          int height = res.heightPixels;

          realWidth = (height > width ? width : height)/3;
          int absol = (int)(0.75 * getResources().getDisplayMetrics().densityDpi);

          if(realWidth < absol) absol = realWidth;

          double maxD = width/absol;
          int max = (int)maxD;

          if(maxD - max >= 0.75) max++;

          return max;
      }

      protected void onCreate(Bundle bundle) {
          super.onCreate(bundle);


          try {
              //Check whether Google Play store is installed or not:
              getPackageManager().getPackageInfo("com.android.vending", 0);

              url = "market://details?id=";

          } catch (final Exception e) {
              url = "https://play.google.com/store/apps/details?id=";
          }

          url = url.concat(getApplicationContext().getPackageName());


          setContentView(R.layout.activity_main);


          int[] imgs = new int[]{R.drawable.length, R.drawable.area, R.drawable.volume, R.drawable.speed,
                  R.drawable.weight, R.drawable.energy, R.drawable.time, R.drawable.data,
                  R.drawable.temp,0};


          Factory f = (Factory)getApplication();
          f.factory(this);

          String save_digits = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_digits", null);
          String save_divider = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_divider", null);
          if (save_digits != null) f.setDigits(save_digits);
          if (save_divider != null) f.setDivider(save_divider);


          RecyclerView recyclerView = findViewById(R.id.recycler_enter);
          recyclerView.setLayoutManager(new GridLayoutManager(this, columner()));
          recyclerView.setAdapter(new EnterAdapter(this, imgs, realWidth, f));


          Toolbar bar = findViewById(R.id.toolbar);
          setSupportActionBar(bar);
          getSupportActionBar().setDisplayShowTitleEnabled(false);
          ((TextView)bar.getChildAt(0)).setText(getString(R.string.app_name));


      }


    @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater().inflate(R.menu.menu_main, menu);
          return true;
      }

      @Override
      public boolean onOptionsItemSelected(MenuItem item) {

          switch (item.getItemId()) {
              case R.id.settings:
              startActivity(new Intent(this, SettingActivity.class));
              break;

              case R.id.info:
                  final AlertDialog dialog = new AlertDialog.Builder(this).
                          setView(getLayoutInflater().inflate(R.layout.custom_dialog, null)).create();

                  dialog.show();

                  ((TextView)dialog.findViewById(R.id.version)).setText("v ".concat(BuildConfig.VERSION_NAME));

                  dialog.findViewById(R.id.dis).setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          dialog.dismiss();
                      }
                  });

                  TextView link = dialog.findViewById(R.id.link);

                  link.setText(Html.fromHtml(
                          "<a href=\"".concat(url).concat("\">").concat(getResources().getString(R.string.problem)).concat("</a>")));
                  link.setMovementMethod(LinkMovementMethod.getInstance());
              break;



              case R.id.mark:
                  final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                  startActivity(intent);
              break;
          }

          return true;
      }


}

