package com.ecommereceassessment.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.ecommereceassessment.R;
import com.ecommereceassessment.adapters.ProductListAdapter;
import com.ecommereceassessment.apputils.AppUtil;
import com.ecommereceassessment.models.Products;

import java.util.ArrayList;

public class SubCategoryListActivity extends BaseActivity {

    private RecyclerView categoryListRv;
    private Products products;
    private ArrayList<Products> productsList=new ArrayList<>();
    private ProductListAdapter productListAdapter;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        iniViews();
    }

    private void iniViews() {
        categoryListRv   =   (RecyclerView)findViewById(R.id.categoryListRv);
        toolbar          =   (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle     =   (TextView) findViewById(R.id.toolbarTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbarTitle.setText(getResources().getString(R.string.mainCategory));
        AppUtil.recycleViewVertical(SubCategoryListActivity.this,categoryListRv);
        getSubcategories();


    }
    private void getSubcategories() {
        ArrayList<String> ids=getIntent().getStringArrayListExtra("products");
        Log.e("TG",""+getIntent().getStringArrayListExtra("products"));
        for(int i=0;i<ids.size();i++)
        {
            productsList.add(searchCategories(ids.get(i),allProductsArrayList));

        }
        productListAdapter = new ProductListAdapter(SubCategoryListActivity.this, productsList, false,false);
        categoryListRv.setAdapter(productListAdapter);
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
