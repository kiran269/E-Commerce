package com.ecommereceassessment.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ecommereceassessment.R;
import com.ecommereceassessment.adapters.ProductListAdapter;
import com.ecommereceassessment.apputils.AppUtil;
import com.ecommereceassessment.models.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductListActivity extends BaseActivity {
    private RecyclerView productListRv;
    private ArrayList<Products> productsList=new ArrayList<>();
    private Spinner sortCriteriaSpin;
    ProductListAdapter productListAdapter;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ArrayList<Products> tmpList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        iniViews();
    }

    /*initializing views*/
    private void iniViews() {

        productListRv      =  (RecyclerView) findViewById(R.id.productListRv);
        sortCriteriaSpin   =  (Spinner) findViewById(R.id.sorttype_list);
        toolbar            =   (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle       =   (TextView) findViewById(R.id.toolbarTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        toolbarTitle.setText(getResources().getString(R.string.products));
        AppUtil.recycleViewVertical(ProductListActivity.this, productListRv);


        getProducts();

        /*filling spinner data*/
        List<String> spinList=new ArrayList<>();
        spinList.add(getResources().getString(R.string.sortList));
        spinList.addAll(filteredList.keySet());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
               R.layout.spinner_item, spinList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortCriteriaSpin.setAdapter(dataAdapter);

        sortCriteriaSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0) {
                    try {
                        /*getting filtered product list*/
                        productsList = getfilteredList(String.valueOf(parent.getItemAtPosition(position)));
                        productListAdapter = new ProductListAdapter(ProductListActivity.this, productsList, true,true);
                        productListRv.setAdapter(productListAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    getProducts();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                getProducts();
            }
        });
    }

    /*Get products*/
    private void getProducts() {
        tmpList=(ArrayList<Products>) getIntent().getSerializableExtra("productList");
        try{
            productListAdapter = new ProductListAdapter(ProductListActivity.this, tmpList, true,false);
            productListRv.setAdapter(productListAdapter);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

     /*getting sorted products*/
    private ArrayList<Products> getfilteredList(String key)
    {
        ArrayList<Products> filterList=new ArrayList<>();
        for(int i=0;i<filteredList.get(key).size();i++)
        {
            Products products=new Products();
            for(int j=0;j<tmpList.size();j++)
            {
                if(tmpList.get(j).id.equalsIgnoreCase(filteredList.get(key).get(i).id))
                {
                    filterList.add(tmpList.get(j));

                }

            }

        }
        return filterList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
