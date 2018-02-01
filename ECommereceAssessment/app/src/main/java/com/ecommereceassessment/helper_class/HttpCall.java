 package com.ecommereceassessment.helper_class;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;


import com.ecommereceassessment.R;
import com.ecommereceassessment.dialog.ProgressBarCustom;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

 /**
  * Created by Ajay on 06-Oct-17.
  */

 public class HttpCall {

     private String message;
     private Context context;
     private ProgressBarCustom progressBarCustom;

     public interface ResponseHandler {
         void Success(JSONObject jsonObject);
         void Error(String volleyError);
     }


     public boolean networkChecker() {
         ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
         return activeNetworkInfo != null && activeNetworkInfo.isConnected();
     }


     // HTTP GET request
     public void HttpGetRequest(Context context, String path, ResponseHandler handler) throws Exception {
         this.context=context;
         progressBarCustom=new ProgressBarCustom(context);
         try {
             progressBarCustom.show();
             new HelperClass().execute(path,handler,"GET");
         }catch (Exception e){
             e.printStackTrace();
         }
     }

     // HTTP POST request
     public void HttpPostRequest(Context context, String path, JSONObject jsonObject, ResponseHandler handler) throws Exception {
         this.context=context;
         progressBarCustom=new ProgressBarCustom(context);
         try {
             progressBarCustom.show();
             new HelperClass().execute(path, handler, "POST", jsonObject);
         }catch (Exception e){
             e.printStackTrace();
         }
     }


     private class HelperClass extends AsyncTask<Object,Object,JSONObject> {

         ResponseHandler responseHandler= null;
         HttpURLConnection httpConnection = null;
         private int responseCode;

         @Override
         protected JSONObject doInBackground(Object... params) {

             String path=(String) params[0];
             responseHandler =(ResponseHandler)params[1];
             String requestType = (String) params[2];
             JSONObject responce=null;

             if (networkChecker()) {
                 try {
                     URL mUrl = new URL(path);
                     httpConnection = (HttpURLConnection) mUrl.openConnection();
                     httpConnection.setRequestMethod(requestType);
                     httpConnection.setUseCaches(false);
                     httpConnection.setAllowUserInteraction(false);
                     httpConnection.setConnectTimeout(100000);
                     httpConnection.setReadTimeout(100000);

                     if (requestType.contentEquals("GET")){
                         httpConnection.setRequestProperty("Content-length", "0");
                         httpConnection.connect();

                     }else {
                         httpConnection.setDoInput(true);
                         httpConnection.setDoOutput(true);

                         httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                         httpConnection.connect();

                         JSONObject jsonParam = (JSONObject)  params[3];

                         DataOutputStream printout = new DataOutputStream(httpConnection.getOutputStream());
                         printout.write(jsonParam.toString().getBytes("utf-8"));
                         printout.flush();

                     }

                     int responseCode = httpConnection.getResponseCode();
                     if (responseCode == HttpURLConnection.HTTP_OK) {

                         BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                         responce = new JSONObject(br.readLine().toString());
                         br.close();

                         httpConnection.disconnect();
                         return responce;
                     } else {
                         httpConnection.disconnect();
                         this.responseCode=responseCode;
                         Log.e("response","response"+responseCode);
                         return responce;
                     }


                 }catch (IOException e) {
                     e.printStackTrace();
                     this.responseCode=504;
                     return responce;
                 } catch (Exception ex) {
                     ex.printStackTrace();
                     this.responseCode=504;
                     return responce;
                 }
             } else {
                 this.responseCode=-50;
                 return responce;
             }

         }

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
         }

         @Override
         protected void onPostExecute(JSONObject jsonObject) {
             super.onPostExecute(jsonObject);
             try {
                 if (progressBarCustom != null && progressBarCustom.isShowing())
                     progressBarCustom.dismiss();
                 if (jsonObject != null) {

                     responseHandler .Success(jsonObject);
                 } else {
                     handleErrorByCode(responseCode);
                     responseHandler.Error(message);

                 }
             }catch (Exception e){
                 e.printStackTrace();
                 if (progressBarCustom != null && progressBarCustom.isShowing())
                     progressBarCustom.dismiss();
             }
         }
     }


     private void handleErrorByCode(int responseCode) {

         try {
             switch (responseCode) {

                 case -50: {

                     message = context.getResources().getString(R.string.internet_connect);

                     break;
                 }

                 case 306:
                     message = "Some thing wrong. please try after some time.";
                     break;

                 case 400: {
                     try {
                         message = "Please try again, Time out.";
                     } catch (Exception e) {
                         e.printStackTrace();
                         message = "Please try again, Time out.";
                     }
                     break;
                 }

                 case 403: {

                     message = "LOGOUT";

                     break;
                 }

                 case 404:
                     message = "Not found";
                     break;
                 case 500:
                     message = "We are working, try again after sometime.";
                     break;
                 case 502:
                     message = "We are working, try again after sometime.";
                     break;
                 case 504:
                     message = "We are working, try again after sometime.";
                     break;
             }
         } catch (Exception e) {
             message = "Some thing wrong. please try after some time.";
         }

     }
 }
