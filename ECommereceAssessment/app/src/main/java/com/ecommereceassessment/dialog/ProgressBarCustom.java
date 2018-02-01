package com.ecommereceassessment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

import com.ecommereceassessment.R;


/**
 * Created by ajays on 5/12/16.
 */
public class ProgressBarCustom extends Dialog {

    ProgressBar progressBar;

    public ProgressBarCustom(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progressbar_view);
        closeOptionsMenu();
        setCancelable(false);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setBackgroundResource(0);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }
}
