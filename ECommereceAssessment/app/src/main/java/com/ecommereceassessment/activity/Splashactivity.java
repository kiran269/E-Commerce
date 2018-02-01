package com.ecommereceassessment.activity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ecommereceassessment.R;
import com.ecommereceassessment.dialog.AppDialog;


public class Splashactivity extends BaseActivity {

    // using UI handler for dealy screen
    private android.os.Handler handler = new android.os.Handler();

    AlertDialog dailog;
    AlertDialog.Builder build;
    private Boolean checkgprs=false;
    private LocationManager locationManager;
    private TextView splash_text;

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_splash);



        initView();

        build = new AlertDialog.Builder(Splashactivity.this);

        locationManager = (LocationManager)Splashactivity.this
                .getSystemService(LOCATION_SERVICE);

        checkInternetOrGprs();

    }

    private void initView() {
      /*  splash_text=(TextView)findViewById(R.id.splash_text);
        Typeface face = Typeface.createFromAsset(this.getAssets(), ".otf");
        splash_text.setTypeface(face);
        splash_text.setText(getResources().getString(R.string.app_name));

        TranslateAnimation transAnim = new TranslateAnimation(0, 0, 0,
                Float.valueOf(AppUtil.getDeviceHeight(Splashactivity.this)/1.8f));
        transAnim.setStartOffset(500);
        transAnim.setDuration(2500);
        transAnim.setFillAfter(true);
        transAnim.setInterpolator(new BounceInterpolator());
        splash_text.startAnimation(transAnim);*/
    }

    private void checkInternetOrGprs(){
        // Handling internet connection
        if (isWorkingInternetPersent()) {
            Log.e("internet ","hszn"+isWorkingInternetPersent());
            // dealyed splash screen for 3 seconds
            handler.postDelayed(new Runnable() {
                public void run() {

                    (new LoadBackgroudData()).execute();
                }
            }, 3500);
        } else {
            Log.e("internet ","hszn"+isWorkingInternetPersent());
            showBasicSplash();
        }


    }

    private void showBasicSplash() {

        handler.postDelayed(new Runnable() {
            public void run() {
                final AppDialog dialog = new AppDialog(Splashactivity.this);
                if (!dialog.isShowing()) {
                    dialog.show();
                    dialog.addTitle(getResources().getString(R.string.internetTitle));
                    dialog.setMessage(getResources().getString(R.string.internetMessage));
                    dialog.isCancelable(false);
                    dialog.onPrimaryClick(getResources().getString(R.string.okButton), new AppDialog.OnClickCallback() {
                        @Override
                        public void clicked(Object... data) {
                            if (isWorkingInternetPersent()) {
                                dialog.dismiss();
                                Log.e("internet ","hszn"+isWorkingInternetPersent());
                                handler.postDelayed(new Runnable() {
                                    public void run() {

                                        (new LoadBackgroudData()).execute();
                                    }
                                }, 2000);
                            } else {
                                dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                startActivity(intent);
                                Log.e("internet2 ","hszn"+isWorkingInternetPersent());

                                Splashactivity.this.finish();
                            }

                        }
                    });
                    dialog.onSecondaryClick(getResources().getString(R.string.exitButton), new AppDialog.OnClickCallback() {
                        @Override
                        public void clicked(Object... data) {
                            dialog.dismiss();
                            Splashactivity.this.finish();
                        }
                    });
                }
            }
        },2000);

    }

    // Method use for check internet connection
    public boolean isWorkingInternetPersent() {
        ConnectivityManager
                cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }


    // this class will execute after 3 seconds
    class LoadBackgroudData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            // Perform load data method here
            // It may be a network operation(loading data from server)
            // or simply loading data from local database

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // after execution go to second activity

                Intent intent = new Intent(Splashactivity.this, MainCategoryActivity.class);
                startActivity(intent);
                finish();

            }

        }



}
