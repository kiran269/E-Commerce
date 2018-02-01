package com.ecommereceassessment.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ecommereceassessment.R;
import com.ecommereceassessment.application.SmartApplication;
import com.ecommereceassessment.constants.ExtraConstant;
import com.ecommereceassessment.detector.ConnectionDetector;
import com.ecommereceassessment.dialog.AppDialog;
import com.ecommereceassessment.models.Products;

import java.util.ArrayList;
import java.util.HashMap;


public class BaseActivity extends AppCompatActivity implements ConnectionDetector.ConnectivityReceiverListener {

    private static final String TAG = "##BaseActivity##";
    public GrantPermission permissionHandler;

    public static ArrayList<Products> allProductsArrayList=new ArrayList<>();
    public static HashMap<String, ArrayList<Products>> filteredList = new HashMap<>();
    private AppDialog dialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMainApplication().setCurrentActivity(this);
        dialog = new AppDialog(this);

    }

    public SmartApplication getMainApplication() {
        return (SmartApplication) getApplicationContext();
    }


    // Check location permissions and perform action accordingly.
    public void checkLocationPermissions(BaseActivity.GrantPermission locationHandler) {

        this.permissionHandler = locationHandler;

        try {
            // Google API
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, ExtraConstant.PERMISSION_LOCATION_REQUEST_CODE);
            } else {
                locationHandler.isPermissionGranted(ExtraConstant.PERMISSION_YES);
            }

        } catch (Exception e) {
            Log.e(TAG, "InvalidPermission");
            locationHandler.isPermissionGranted(ExtraConstant.PERMISSION_NO);
            e.printStackTrace();
        }
    }

    // Check storage permissions and perform action accordingly.
    public void checkStoragePermissions(BaseActivity.GrantPermission storageHandler) {

        this.permissionHandler = storageHandler;

        try {
            // Google API
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, ExtraConstant.PERMISSION_STORAGE_REQUEST_CODE);
            } else {
                storageHandler.isPermissionGranted(ExtraConstant.PERMISSION_YES);
            }

        } catch (Exception e) {
            Log.e(TAG, "InvalidPermission");
            storageHandler.isPermissionGranted(ExtraConstant.PERMISSION_NO);
            e.printStackTrace();
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case ExtraConstant.PERMISSION_STORAGE_REQUEST_CODE: {

                boolean showRationale = shouldShowRequestPermissionRationale(permissions[0]);

                if (!showRationale) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        permissionHandler.isPermissionGranted(ExtraConstant.PERMISSION_YES);
                    } else {
                        permissionHandler.isPermissionGranted(ExtraConstant.PERMISSION_NO);
                    }
                } else {
                    permissionHandler.isPermissionGranted(ExtraConstant.PERMISSION_RATIONALE);
                }

                break;
            }
        }

        permissionHandler = null;

    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        Log.e("asdisConnected", "isConnected          " + isConnected);

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.getMainApplication().setConnectivityListener(this);
    }

    public interface GrantPermission {
        void isPermissionGranted(int permissionCode);
    }

    /*search category function*/
    public Products searchCategories(String id, ArrayList<Products> productsArrayList)
    {
        Products products=new Products();
        for(int i=0;i<productsArrayList.size();i++)
        {
            if(productsArrayList.get(i).id.equalsIgnoreCase(id))
            {
                products=productsArrayList.get(i);
            }
        }

        return products;
    }


    /*search product function*/
    public Products searchProduct(String id, ArrayList<Products> productsArrayList)
    {
        Products products=new Products();
        for(int i=0;i<productsArrayList.size();i++)
        {
            for(int j=0;j<productsArrayList.get(i).productsArrayList.size();j++)
            if(productsArrayList.get(i).productsArrayList.get(j).id.equalsIgnoreCase(id))
            {
                products=productsArrayList.get(i).productsArrayList.get(j);
            }
        }

        return products;
    }

}