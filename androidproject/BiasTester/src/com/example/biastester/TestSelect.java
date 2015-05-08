package com.example.biastester;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class TestSelect extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_select);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_select, menu);
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
	
	public void beginViolenceTest(View view)
	{
		Intent intent = new Intent(this, Test.class);
		intent.putExtras(getIntent().getExtras());
        startActivity(intent);
	}
	
	public void beginPositiveTest(View view)
	{
		Intent intent = new Intent(this, PositiveTest.class);
		intent.putExtras(getIntent().getExtras());
        startActivity(intent);
	}
	
	public void beginSexualTest(View view)
	{
		Intent intent = new Intent(this, SexualTest.class);
		intent.putExtras(getIntent().getExtras());
        startActivity(intent);
	}
	
	public void beginMultiTest(View view)
	{
		Intent intent = new Intent(this, MultiTest.class);
		intent.putExtras(getIntent().getExtras());
        startActivity(intent);
	}
}
