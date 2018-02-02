package com.ecommereceassessment.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ecommereceassessment.R;
import com.ecommereceassessment.custom_views.CustomTextView;
import com.ecommereceassessment.models.Products;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ProductDetailActivity extends BaseActivity {

    private CustomTextView price;
    private CustomTextView productName;
    private Spinner colorSpinner;
    private Spinner sizeSpinner;
    ArrayList<Products> variationList=new ArrayList<>();
    ArrayList<String> colors=new ArrayList<>();
    ArrayList<String> sizes=new ArrayList<>();
    private String color;
    private String size;
    private LinearLayout sizeLyt;
    private Toolbar toolbar;
    private TextView toolbarTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        iniViews();
    }

    private void iniViews() {
        colorSpinner    = (Spinner)findViewById(R.id.color_spin);
        sizeSpinner     = (Spinner)findViewById(R.id.size_spin);
        price           = (CustomTextView) findViewById(R.id.price_txt);
        productName     = (CustomTextView) findViewById(R.id.productTxt);
        sizeLyt         = (LinearLayout)findViewById(R.id.sizeLyt);
        toolbar         = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle    = (TextView) findViewById(R.id.toolbarTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        toolbarTitle.setText(getResources().getString(R.string.products));
        variationList   = (ArrayList<Products>) getIntent().getSerializableExtra("variations");

        productName.setText(getIntent().getStringExtra("title"));
        getSpinnerData();

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                color = (String) parent.getItemAtPosition(position);
                price.setText(setPrice(color, String.valueOf(sizeSpinner.getSelectedItem())));
                checkAvailability(setPrice(color, String.valueOf(sizeSpinner.getSelectedItem())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                color = (String) parent.getItemAtPosition(0);
                 price.setText(setPrice(color, String.valueOf(sizeSpinner.getSelectedItem())));
                checkAvailability(setPrice(color, String.valueOf(sizeSpinner.getSelectedItem())));
            }
        });

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                size = (String) parent.getItemAtPosition(position);
                price.setText(setPrice(String.valueOf(colorSpinner.getSelectedItem()), size));
                checkAvailability(setPrice(String.valueOf(colorSpinner.getSelectedItem()),size));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                size = (String) parent.getItemAtPosition(0);
                price.setText(setPrice(String.valueOf(colorSpinner.getSelectedItem()), size));
                checkAvailability(setPrice(String.valueOf(colorSpinner.getSelectedItem()),size));
            }
        });
    }

    private void getSpinnerData()
    {
        for(int i=0;i<variationList.size();i++)
        {
            Products products=variationList.get(i);

           addElement(products.color,colors);
           addElement(products.size,sizes);

        }
        Log.e("colorsize"," "+colors.toString()+"   "+sizes.toString());
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item,colors);
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, sizes);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(colorAdapter);
        sizeSpinner.setAdapter(sizeAdapter);

        if (sizes.contains("null")){
            sizeLyt.setVisibility(View.GONE);
        }else {
            sizeLyt.setVisibility(View.VISIBLE);
        }
    }

    private void addElement(String element,ArrayList<String> list)
    {
        if (!list.contains(element)) {
            list.add(element);
        }

    }

    private String setPrice(String color,String size)
    {
        String price="";
        for(int i=0;i<variationList.size();i++)
        {
            Products products=variationList.get(i);
            if(products.color.equalsIgnoreCase(color))
            {
                if(products.size.equalsIgnoreCase(size))
                {
                   price=products.price;
                }

            }

            Log.e("fghj"," "+products.price);
        }
        return price;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkAvailability(String price)
    {
        if(price.equalsIgnoreCase(""))
        {
            ProductDetailActivity.this.price.setText(getResources().getString(R.string.notavailable));
        }
    }
}
