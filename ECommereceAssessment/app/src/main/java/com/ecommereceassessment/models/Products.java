package com.ecommereceassessment.models;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.ecommereceassessment.R;
import com.ecommereceassessment.activity.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by yahoo on 30\01\2018.
 */

public class Products implements Serializable {
    public String id;
    public String name;
    public String date_add;
    public String color;
    public String size;
    public String price;
    public String tax_name;
    public String tax_value;
    public String counts;

    public ArrayList<Products> variationArrayList        =  new ArrayList<>();
    public ArrayList<String> childIds                    =  new ArrayList<>();
    public ArrayList<Products> productsArrayList         =  new ArrayList<>();
    public ArrayList<Products> AllProductsList           =  new ArrayList<>();
    public HashMap<String, ArrayList<String>> subcatList =  new HashMap<>();

    /*getting product list*/
    public void getProductList(JSONObject jsonObject) {
        try {

            JSONArray categories = jsonObject.getJSONArray("categories");
            for (int index = 0; index < categories.length(); index++) {
                JSONObject object = categories.getJSONObject(index);
                Products products = new Products();
                products.id = object.getString("id");
                products.name = object.getString("name");
                JSONArray productArray = object.getJSONArray("products");
                JSONArray childArray = object.getJSONArray("child_categories");
                if (productArray.length() > 0) {
                    for (int pIndex = 0; pIndex < productArray.length(); pIndex++) {
                        Products products1 = new Products();
                        JSONObject poOject = productArray.getJSONObject(pIndex);
                        products1.id = poOject.getString("id");
                        products1.name = poOject.getString("name");
                        products1.date_add = poOject.getString("date_added");
                        products1.tax_name = poOject.getJSONObject("tax").getString("name");
                        products1.tax_value = poOject.getJSONObject("tax").getString("value");

                        JSONArray variationArray = poOject.getJSONArray("variants");
                        for (int vIndex = 0; vIndex < variationArray.length(); vIndex++) {
                            JSONObject vObject = variationArray.getJSONObject(vIndex);
                            Products products2 = new Products();
                            products2.id = vObject.getString("id");
                            products2.color = vObject.getString("color");
                            products2.size = vObject.getString("size");
                            products2.price = vObject.getString("price");
                            products1.variationArrayList.add(products2);
                        }
                        products.productsArrayList.add(products1);

                    }
                } else if (childArray.length() > 0) {
                    ArrayList<String> childIds = new ArrayList<String>();
                    for (int cIndex = 0; cIndex < childArray.length(); cIndex++) {
                        childIds.add(childArray.get(cIndex).toString());
                        this.childIds.add(childArray.get(cIndex).toString());

                    }
                    products.subcatList.put(products.id, childIds);
                    subcatList.put(products.id, childIds);

                }
                AllProductsList.add(products);

            }

            BaseActivity.allProductsArrayList.addAll(AllProductsList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*getting filtered list*/
    public void getFilteredList(JSONObject jsonObject, Context context) {
        try {
            JSONArray rankingArray = jsonObject.getJSONArray("rankings");
            for (int i = 0; i < rankingArray.length(); i++) {
                ArrayList<Products> filteredProducts = new ArrayList<>();
                JSONObject object = rankingArray.getJSONObject(i);
                JSONArray productsArray = object.getJSONArray("products");
                for (int j = 0; j < productsArray.length(); j++) {
                    Products products = new Products();
                    JSONObject proObject = productsArray.getJSONObject(j);
                    products.id = proObject.getString("id");

                    if (i == 0) {
                        products.counts = proObject.getString("view_count") +" "+ context.getResources().getString(R.string.view);
                    } else if (i == 1) {
                        products.counts = proObject.getString("order_count") +" "+ context.getResources().getString(R.string.orders);
                    } else if (i == 2) {
                        products.counts = proObject.getString("shares") + " "+context.getResources().getString(R.string.shares);
                    }

                    Products products1 = new BaseActivity().searchProduct(products.id, BaseActivity.allProductsArrayList);
                    products1.counts = products.counts;
                    filteredProducts.add(products1);
                    Log.e("filter", "" + products1.name + "  " + products1.counts);
                }
                BaseActivity.filteredList.put(object.getString("ranking"), filteredProducts);
                Log.e("filter", "" + BaseActivity.filteredList.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
