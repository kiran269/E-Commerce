package com.ecommereceassessment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecommereceassessment.R;
import com.ecommereceassessment.adapters.MainCategoryAdapter;
import com.ecommereceassessment.helper_class.HttpCall;
import com.ecommereceassessment.constants.ApiConstant;
import com.ecommereceassessment.models.Products;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainCategoryActivity extends BaseActivity {

    private ExpandableListView categoryList;
    private Products products;
    private ArrayList<Products> mainCategoryList=new ArrayList<>();
    private HashMap<String, ArrayList<String>> mainList=new HashMap<>();
    private HashMap<String, ArrayList<Products>> sortedList=new HashMap<>();

    private HashMap<String, ArrayList<String>> subcat=new HashMap<>();
    MainCategoryAdapter mainCategoryAdapter;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);

        iniViews();
    }

    private void iniViews() {
        categoryList   =   (ExpandableListView)findViewById(R.id.maincategoryList);
        products       =   new Products();
        toolbar        =   (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle   =   (TextView) findViewById(R.id.toolbarTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbarTitle.setText(getResources().getString(R.string.mainCategory));

        getCategories();

        categoryList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
               Products product1=(Products)mainCategoryAdapter.getChild(groupPosition,childPosition);
                Intent intent=new Intent(MainCategoryActivity.this,SubCategoryListActivity.class);
                intent.putExtra("products",product1.subcatList.get(product1.id));
                startActivity(intent);
                return false;
            }
        });

        categoryList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                ImageView iconExpansion = (ImageView)v.findViewById(R.id.expand_icon);
                if(categoryList.isGroupExpanded(groupPosition))
                {
                    iconExpansion.setImageResource(R.mipmap.ic_arrow_down);
                }
                else
                {
                    iconExpansion.setImageResource(R.mipmap.ic_arow_up);
                }
                return false;
            }
        });


    }
    private void getCategories()
    {
        /*api parsing*/
        try {
            new HttpCall().HttpGetRequest(MainCategoryActivity.this, ApiConstant.PRODUCTLISTURL, new HttpCall.ResponseHandler() {
                @Override
                public void Success(JSONObject jsonObject) {
                    try {
                        JSONObject obj = (JSONObject) jsonObject;
                        products.getProductList(obj);
                        products.getFilteredList(obj,MainCategoryActivity.this);

                        /*sorting main and sub category list*/
                        subcat=products.subcatList;
                        for(String key:subcat.keySet())
                        {
                            if(!products.childIds.contains(key))
                            {
                                mainList.put(key,subcat.get(key));
                            }
                        }

                        /*filling group List*/
                        for(int i=0;i<products.AllProductsList.size();i++)
                        {
                            Products products1=new Products();
                            if(mainList.containsKey(products.AllProductsList.get(i).id))
                            {
                                products1=products.AllProductsList.get(i);
                                mainCategoryList.add(products1);
                                Log.e("main sub","  "+products1.name);
                            }
                        }
                        /*child list*/
                        for(String key: mainList.keySet())
                        {
                            ArrayList<Products>tmpList=new ArrayList<Products>();
                            for(int i=0;i<mainList.get(key).size();i++)
                            {
                               tmpList.add(searchCategories(mainList.get(key).get(i),products.AllProductsList));
                            }
                            sortedList.put(key,tmpList);
                        }

                        /*setting adapter*/
                        mainCategoryAdapter = new MainCategoryAdapter(MainCategoryActivity.this, mainCategoryList,sortedList);
                        categoryList.setAdapter(mainCategoryAdapter);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                @Override
                public void Error(String volleyError) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

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
