package com.example.biastester;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class IntroScreen2 extends ActionBarActivity {
	
	Bundle b;
	Person p;
	
	public final static String EXTRA_MESSAGE = "com.example.IntroScreen2.MESSAGE";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro_screen2);
		
		b = getIntent().getExtras();
		
		if (b != null)
		{
			p = b.getParcelable("user");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.intro_screen2, menu);
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
	
	@SuppressLint("WrongViewCast")
	public void returnToMainMenu(View view)
	{
		Intent intent = new Intent(this, BiasTester.class);
    	EditText editText = (EditText) findViewById(R.id.textView1);
    	String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
	}
	
	public void progressToNext(View view)
	{
		Intent intent = new Intent(this, IntroScreen3.class);
		intent.putExtras(getIntent().getExtras());
		
        startActivity(intent);
	}
}
