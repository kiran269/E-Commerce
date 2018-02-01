package com.ecommereceassessment.application;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.provider.Settings;
import android.telephony.TelephonyManager;


import com.ecommereceassessment.detector.ConnectionDetector;

import java.security.MessageDigest;

/**
 * Created by Ajay on 10/26/2014.
 */
public class SmartApplication extends Application {


    private Activity mCurrentActivity = null;

    /**
     * get ApiHelper Component for this application,
     */


    public void onCreate() {
        super.onCreate();

        if (isDeveloper()) {


            try {
                PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    //Log.e("App Hash For \"" + getPackageName() + "\": \t" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private boolean isDeveloper() {
        return true;
    }


    public String getDeveloperInfo() {
        return "Kiran bablani@ akiru269@gmail.com";
    }

    public String getAndroidId() {
        return Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public String getDeviceId() {
        return ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    public int getAppVersionCode() {
        try {
            PackageInfo packageInfo = getPackageManager()
                    .getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }


    /*
   * This method is used to check internet connection
   * */
    public void setConnectivityListener(ConnectionDetector.ConnectivityReceiverListener listener) {
        ConnectionDetector.connectivityReceiverListener = listener;
    }

}
