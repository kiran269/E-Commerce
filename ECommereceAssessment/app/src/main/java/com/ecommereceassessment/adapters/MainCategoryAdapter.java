package com.ecommereceassessment.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ecommereceassessment.R;
import com.ecommereceassessment.activity.BaseActivity;
import com.ecommereceassessment.activity.MainCategoryActivity;
import com.ecommereceassessment.models.Products;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yahoo on 31\01\2018.
 */

public class MainCategoryAdapter extends BaseExpandableListAdapter {
private ArrayList<Products> categoryList=new ArrayList<>();
    private BaseActivity baseActivity;
    private HashMap<String, ArrayList<Products>> subcat;

    public MainCategoryAdapter(BaseActivity baseActivity, ArrayList<Products>
            categoryList, HashMap<String, ArrayList<Products>> subcat) {
        this.baseActivity=baseActivity;
        this.categoryList=categoryList;
        this.subcat=subcat;

        Log.e("subcat",""+subcat+"   "+categoryList);
    }

    @Override
    public int getGroupCount() {
        return categoryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return subcat.get(categoryList.get(groupPosition).id).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categoryList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return subcat.get(categoryList.get(groupPosition).id).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Products products=(Products)categoryList.get(groupPosition);
        Log.e("hj","   "+products.toString());
        LayoutInflater infalInflater = (LayoutInflater) this.baseActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = infalInflater.inflate(R.layout.group_item, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(products.name);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Products products=(Products)getChild(groupPosition,childPosition);
        LayoutInflater infalInflater = (LayoutInflater) this.baseActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = infalInflater.inflate(R.layout.child_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(products.name);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
