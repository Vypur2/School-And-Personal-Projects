package com.example.biastester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Results3 extends Activity {
	
	ListView mainlistview;
	ArrayList<Slide> curTest = new ArrayList<Slide>();
	private int lastExpandedPosition = -1;
	
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ImageView imview;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results3);
		
				
		//mainlistview = (ListView)findViewById(R.id.listView1);
		ArrayList<String> itemList = new ArrayList<String>();
		List<String> tapTime = new ArrayList<String>();
		
		Bundle b = getIntent().getExtras();
		if (b != null)
		{
			curTest = b.getParcelableArrayList("slides");
		}
		
		for (int i = 0; i < curTest.size();i++)
		{
			if (i > 0)
			{
				curTest.get(i).prev = curTest.get(i-1);
			}
			itemList.add(curTest.get(i).filename);
		}
		
		/*
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	              android.R.layout.simple_list_item_1, android.R.id.text1, itemList);
		mainlistview.setAdapter(adapter); 
		*/
		
		//expandable list stuff here
		expListView = (ExpandableListView) findViewById(R.id.expL);
		imview = (ImageView) findViewById(R.id.imageView1);
		listDataHeader = itemList;
		
		for (int j = 0;j<itemList.size();j++)
		{
			List<String> taps = new ArrayList<String>();
			if (curTest.get(j).tapTime > 990){ //not tapped
				taps.add("no tap");
			}
			else
			{
				taps.add("tap time: " + Float.toString(curTest.get(j).tapTime) + "ms");
			}
			
			if (curTest.get(j).isPhoto)
			{
				taps.add("photo");
			}
			else
			{
				if (j > 0)
				{
					taps.add("previous slide Race: " + curTest.get(j).prev.Race);
					taps.add("previous slide Gender: " + curTest.get(j).prev.Gender);
				}
			}
				
			listDataChild.put(itemList.get(j), taps);
		}
		
		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
		expListView.setAdapter(listAdapter);
		//
		
		/*
		mainlistview.setOnItemClickListener(new AdapterView.OnItemClickListener(){ 
			 public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				 Toast.makeText(Results3.this, "Tap Time for This Slide : " + Float.toString(curTest.get(position).tapTime) + "ms", 1).show();
			 }
		});
		*/
		
		expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener(){
			public void OnGroupClickListener(ExpandableListView arg0, View arg1, int position, long arg3) {
				
			}
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				ImageView img = (ImageView) findViewById(R.id.imageView1);
				int imageRes = getResources().getIdentifier(curTest.get(groupPosition).filename, "drawable", getPackageName());
				imview.setImageResource(imageRes);
				
				return false;
			}
		});
		
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			 @Override
			 public void onGroupExpand(int groupPosition) {
			       if (lastExpandedPosition != -1
			                   && groupPosition != lastExpandedPosition) {
			           expListView.collapseGroup(lastExpandedPosition);
			       }
			       lastExpandedPosition = groupPosition;
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results2, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
