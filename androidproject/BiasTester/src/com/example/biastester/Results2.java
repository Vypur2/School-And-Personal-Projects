package com.example.biastester;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Results2 extends Activity {

	ListView mainlistview;
	ArrayList<Slide> curTest = new ArrayList<Slide>();
	String resString = ".....";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results2);
		
		mainlistview = (ListView)findViewById(R.id.listView1);
		
		Bundle b = getIntent().getExtras();
		if (b != null)
		{
			curTest = b.getParcelableArrayList("slides");
			resString = b.getString("results");
		}
		
		//load results text package here
		TextView tv = (TextView)findViewById(R.id.textView2);
		tv.setText(resString);
		//
				

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results2, menu);
		return true;
	}
	
	public void gotoSlideDetails(View view)
	{
		Intent intent2 = new Intent(getBaseContext(), Results3.class);
		 Bundle bundle = new Bundle();
	     bundle.putParcelableArrayList("slides",(ArrayList<? extends Parcelable>) curTest);
	     intent2.putExtras(bundle);
	     startActivity(intent2);
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
