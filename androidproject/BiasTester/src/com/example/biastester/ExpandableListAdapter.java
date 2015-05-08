package com.example.biastester;

import java.util.List;
import java.util.Map;

import com.example.biastester.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter{

	private Activity context;
	private Map<String, List<String>> Slides;
	private List<String> filenames;
	
	public ExpandableListAdapter(Activity context, List<String> laptops,
			Map<String, List<String>> laptopCollections) {
		this.context = context;
		this.Slides = laptopCollections;
		this.filenames = laptops;
	}
	
	@Override
    public int getGroupCount() {
        return this.filenames.size();
    }

	@Override
    public int getChildrenCount(int groupPosition) {
        return this.Slides.get(this.filenames.get(groupPosition)).size();
    }

	@Override
    public Object getGroup(int groupPosition) {
        return this.filenames.get(groupPosition);
    }

	 @Override
	 public Object getChild(int groupPosition, int childPosititon) {
	       return this.Slides.get(this.filenames.get(groupPosition))
	               .get(childPosititon);
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
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_item, null);
        }
 
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.laptop);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        return convertView;
    }

	 @Override
	 public View getChildView(int groupPosition, final int childPosition,
	         boolean isLastChild, View convertView, ViewGroup parent) {
	        final String childText = (String) getChild(groupPosition, childPosition);
	     if (convertView == null) {
	         LayoutInflater infalInflater = (LayoutInflater) this.context
	                 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	         convertView = infalInflater.inflate(R.layout.child_item, null);
	     }
	 
	     TextView txtListChild = (TextView) convertView
	             .findViewById(R.id.laptop);
	 
	     txtListChild.setText(childText);
	     return convertView;
	 }

	 @Override
	 public boolean isChildSelectable(int groupPosition, int childPosition) {
	     return true;
	 }
	
}
