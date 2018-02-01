package com.ecommereceassessment.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ecommereceassessment.R;
import com.ecommereceassessment.activity.BaseActivity;
import com.ecommereceassessment.activity.ProductDetailActivity;
import com.ecommereceassessment.activity.ProductListActivity;
import com.ecommereceassessment.models.Products;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yahoo on 16\10\2017.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    private BaseActivity context;
    private ArrayList<Products> categoryArrayList = new ArrayList<>();
    private HashMap<String,ArrayList<Products>> sortedList=new HashMap<>();
    private View convertView;
    private boolean displaycount;
    private boolean visibility;

    public ProductListAdapter(BaseActivity context, ArrayList<Products> categoryArrayList, boolean visibility,boolean displaycount) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        this.visibility = visibility;
        this.displaycount = displaycount;
    }

    @Override
    public ProductListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.category_list_item, parent, false);

        return new ProductListAdapter.MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(final ProductListAdapter.MyViewHolder holder, int position) {
        final Products products = (Products) categoryArrayList.get(position);

        holder.itemName.setText(products.name);
        try {
            if(displaycount) {
                holder.countText.setVisibility(View.VISIBLE);
                if (!products.counts.equalsIgnoreCase("null"))
                    holder.countText.setText(products.counts);
            }
            else {
                holder.countText.setVisibility(View.GONE);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        holder.cardLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!visibility) {
                    Intent intent = new Intent(context, ProductListActivity.class);
                    intent.putExtra("productList", products.productsArrayList);
                    context.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("variations", products.variationArrayList);
                    intent.putExtra("title", products.name);
                    Log.e("rrtt","vvvv"+products.variationArrayList);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView countText;
        private CardView cardLyt;

        MyViewHolder(View view) {
            super(view);
            itemName     = (TextView) view.findViewById(R.id.productName);
            countText    = (TextView) view.findViewById(R.id.countTxt);
            cardLyt      = (CardView) view.findViewById(R.id.card);

        }
    }

}
