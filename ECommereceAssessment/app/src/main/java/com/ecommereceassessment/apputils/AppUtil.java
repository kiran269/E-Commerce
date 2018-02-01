package com.ecommereceassessment.apputils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecommereceassessment.activity.BaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.Html.FROM_HTML_MODE_LEGACY;


/**
 * Created by ajay singh on 2/18/16.
 */
public class AppUtil {


    private static LayoutInflater inflater;
    private static boolean isOpened;


    BaseActivity baseActivity;
    public static int cartCount;

    public static boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getApplicationContext().getSystemService(activity.getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getDeviceWidth(Activity activity) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;

        return width;
    }

    public static int getDeviceHeight(Activity activity) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;

        return height;
    }


    /**
     * Turn drawable into byte array.
     *
     * @param drawable data
     * @return byte array
     */
    public static byte[] getFileDataFromDrawable(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * This method is used to check edit is empty or conatin any text
     *
     * @param s Stirng
     * @return bool
     */
    public static Boolean validateEditText(String s) {
        return TextUtils.isEmpty(s.trim());
    }


    public static void buttonSingleClick(TextView textView, boolean check) {
        if (check == false) {
            textView.setClickable(false);
        } else {
            textView.setClickable(true);
        }
    }

    public static void recycleViewVertical(BaseActivity baseActivity, RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(baseActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(true);
    }


    public static void recycleViewHorizontal(BaseActivity baseActivity, RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(baseActivity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(true);
    }

    /*public static void messageShow(Toolbar toolbar, BaseActivity baseActivity, String msg, int color, int image){
        if (!msg.equalsIgnoreCase("")) {
            TSnackbar snackbar = TSnackbar
                    .make(toolbar, msg, TSnackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.setIconLeft(image, 24); //Size in dp - 24 is great!

            snackbar.setIconPadding(8);
            snackbar.setMaxWidth(3000); //if you want fullsize on tablets
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundResource(color);
            TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }

    public static void messageShowFrag(View toolbar, BaseActivity baseActivity, String msg, int color, int image){
        if (!msg.equalsIgnoreCase("")) {
            TSnackbar snackbar = TSnackbar
                    .make(toolbar, msg, TSnackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.setIconLeft(image, 24); //Size in dp - 24 is great!

            snackbar.setIconPadding(8);
            snackbar.setMaxWidth(3000); //if you want fullsize on tablets
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundResource(color);
            TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }*/

    public static String firstLetterCap(String myString) {
        try {
            String upperString = myString.substring(0, 1).toUpperCase() + myString.substring(1);
            return upperString;
        } catch (Exception e) {
            return "";
        }
    }

    public static Bitmap roundedCorner(Context context, int image) {
        Bitmap mbitmap = ((BitmapDrawable) context.getResources().getDrawable(image)).getBitmap();
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 20, 20, mpaint);

        return imageRounded;
    }


    /*Login, Forgot password and signup replace fragment*/
   /* public static void loginFragmentReplace(BaseFragment baseFragment, BaseFragment baseFragment2) {
        FragmentManager fragmentManager = baseFragment.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentView, baseFragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }*/


    public static Bitmap getCircularBitmapWithWhiteBorder(Bitmap bitmap, int borderWidth) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }

        final int width = bitmap.getWidth() + borderWidth;
        final int height = bitmap.getHeight() + borderWidth;

        Bitmap canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Canvas canvas = new Canvas(canvasBitmap);
        float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(borderWidth);
        canvas.drawCircle(width / 2, height / 2, radius - borderWidth / 2, paint);
        return canvasBitmap;
    }



    public static CharSequence setTextColor(String color, String text, String title){

        String input = "<font color=" + color + ">" + text + "</font>";
        Spanned spanned;
        if (Build.VERSION.SDK_INT >= 24) {
            spanned=Html.fromHtml(input, FROM_HTML_MODE_LEGACY );
        } else {
            spanned = Html.fromHtml(input);
        }

        return TextUtils.concat(title, spanned);
    }

    public static String dateReverse(String date){
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
        Date date1=null;
        try {
            date1 = input.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(date1);
    }

    public static String checkBlankString(String text){
        if (text.equalsIgnoreCase("")){
            return "-";
        }else {
            return text;
        }
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public static void expandOrCollapseView(final View v, boolean expand){

        if(expand){
            v.setVisibility(View.VISIBLE);

            final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            v.measure(widthSpec, heightSpec);

            ValueAnimator mAnimator = slideAnimator(v,0, v.getMeasuredHeight());
            mAnimator.start();
        }
        else
        {
            final int initialHeight = v.getMeasuredHeight();
            ValueAnimator valueAnimator = ValueAnimator.ofInt(initialHeight,0);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    v.getLayoutParams().height = (int) animation.getAnimatedValue();
                    v.requestLayout();
                    if((int)animation.getAnimatedValue() == 0)
                        v.setVisibility(View.GONE);
                }
            });
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.setDuration(500);
            valueAnimator.start();
          //  v.setVisibility(View.GONE);
        }
    }

    private static ValueAnimator slideAnimator(final View v , int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

}
