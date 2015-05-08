package com.example.biastester;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class BiasTester extends ActionBarActivity {

	public final static String EXTRA_MESSAGE = "com.example.biastester.MESSAGE";
    ArrayList<TestType> testList = new ArrayList<TestType>();
    ArrayList<TestType> tests = new ArrayList<TestType>();
    Person currentPerson = null;
    public boolean readExistingUser = false;
    ArrayList<Person> userList = new ArrayList<Person>();
    
    public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		currentPerson = savedInstanceState.getParcelable("user");
		
		Spinner ageSpinner = (Spinner)findViewById(R.id.spinner1);
		Spinner genderSpinner = (Spinner)findViewById(R.id.spinner2);
		Spinner raceSpinner = (Spinner)findViewById(R.id.spinner3);
		
		int position;
		if (currentPerson.age == 20)
		{
			position = 0;
		}
		else if (currentPerson.age == 24)
		{
			position = 1;
		}
		else if (currentPerson.age == 29)
		{
			position = 2;
		}
		else if (currentPerson.age == 33)
		{
			position = 3;
		}
		else if (currentPerson.age == 38)
		{
			position = 4;
		}
		else if (currentPerson.age == 45)
		{
			position = 5;
		}
		else
		{
			position = 0;
		}
		ageSpinner.setSelection(position);
		 
		if (currentPerson.gender.equals("Male"))
		{
			position = 0;
		}
		else if (currentPerson.gender.equals("Female"))
		{
			position = 1;
		}
		else
		{
			position = 2;
		}
		genderSpinner.setSelection(position);
		 
		if (raceSpinner.getSelectedItem().toString().equals("White"))
	    {
	    	position = 0;
	    }
	    else if (raceSpinner.getSelectedItem().toString().equals("African American"))
	    {
	    	position = 1;
	    }
	    else if (raceSpinner.getSelectedItem().toString().equals("Asian"))
	    {
	    	position = 2;
	    }
	    else if (raceSpinner.getSelectedItem().toString().equals("Indian"))
	    {
	    	position = 3;
	    }
	    else if (raceSpinner.getSelectedItem().toString().equals("Nordic"))
	    {
	    	position = 4;
	    }
	    else if (raceSpinner.getSelectedItem().toString().equals("African"))
	    {
	    	position = 5;
	    }
		raceSpinner.setSelection(position);
		
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bias_tester);
        
        Spinner ageSpinner = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"18 - 22", "23 - 26", "27 - 31", "31 - 35", "36 - 40", "41 - 50"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        ageSpinner.setAdapter(adapter);
        
        Spinner genderSpinner = (Spinner)findViewById(R.id.spinner2);
        String[] genders = new String[]{"Male", "Female", "Other"};
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genders);
        genderSpinner.setAdapter(adapt);
        
        Spinner raceSpinner = (Spinner)findViewById(R.id.spinner3);
        String[] races = new String[]{"White", "African American", "African", "Asian", "American Indian", "Indian","Nordic"};
        ArrayAdapter<String> asdf = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, races);
        raceSpinner.setAdapter(asdf);
        
        
        Bundle b = getIntent().getExtras();
        if (b != null)
        {
        	//testList = b.getParcelableArrayList("testList");
        	currentPerson = b.getParcelable("user");
        	userList = b.getParcelableArrayList("userList");
        	tests = currentPerson.tests;
    		readExistingUser = true;
    		
    		TextView vw = (TextView) findViewById(R.id.editText1);
    		vw.setText(currentPerson.Name);
    		ageSpinner = (Spinner)findViewById(R.id.spinner1);
    		genderSpinner = (Spinner)findViewById(R.id.spinner2);
    		raceSpinner = (Spinner)findViewById(R.id.spinner3);
    		
    		int position1, position2, position3 = 0;
    		if (currentPerson.age == 20)
    		{
    			position1 = 0;
    		}
    		else if (currentPerson.age == 24)
    		{
    			position1 = 1;
    		}
    		else if (currentPerson.age == 29)
    		{
    			position1 = 2;
    		}
    		else if (currentPerson.age == 33)
    		{
    			position1 = 3;
    		}
    		else if (currentPerson.age == 38)
    		{
    			position1 = 4;
    		}
    		else if (currentPerson.age == 45)
    		{
    			position1 = 5;
    		}
    		else
    		{
    			position1 = 0;
    		}
    		ageSpinner.setSelection(position1);
    		 
    		if (currentPerson.gender.equals("Male"))
    		{
    			position2 = 0;
    		}
    		else if (currentPerson.gender.equals("Female"))
    		{
    			position2 = 1;
    		}
    		else
    		{
    			position2 = 2;
    		}
    		genderSpinner.setSelection(position2);
    		 
    		if (currentPerson.Race.equals("White"))
    	    {
    	    	position3 = 0;
    	    }
    	    else if (currentPerson.Race.equals("African American"))
    	    {
    	    	position3 = 1;
    	    }
    	    else if (currentPerson.Race.equals("African"))
    	    {
    	    	position3 = 2;
    	    }
    	    else if (currentPerson.Race.equals("Asian"))
    	    {
    	    	position3 = 3;
    	    }
    	    else if (currentPerson.Race.equals("American Indian"))
    	    {
    	    	position3 = 4;
    	    }
    	    else if (currentPerson.Race.equals("Indian"))
    	    {
    	    	position3 = 5;
    	    }
    	    else
    	    {
    	    	position3 = 6;
    	    }
    		raceSpinner.setSelection(position3);
        }
               
   
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		
		savedInstanceState.putParcelable("user",currentPerson);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bias_tester, menu);
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
    
    /** called when the user clicks the button */
    public void beginTutorial(View view)
    {
    	
    	//get text from fields here
    	
    	//
    	Person p = new Person();
    	TextView vw = (TextView) findViewById(R.id.editText1);
    	p.Name = vw.getText().toString();
    	//
    	Spinner mySpinner=(Spinner) findViewById(R.id.spinner1);
    	if (mySpinner.getSelectedItem().toString().equals("18 - 22"))
    	{
    		p.age = 20;
    	}
    	else if (mySpinner.getSelectedItem().toString().equals("23 - 26"))
    	{
    		p.age = 24;
    	}
    	else if (mySpinner.getSelectedItem().toString().equals("27 - 31"))
    	{
    		p.age = 29;
    	}
    	else if (mySpinner.getSelectedItem().toString().equals("31 - 35"))
    	{
    		p.age = 33;
    	}
    	else if (mySpinner.getSelectedItem().toString().equals("36 - 40"))
    	{
    		p.age = 38;
    	}
    	else if (mySpinner.getSelectedItem().toString().equals("41 - 50"))
    	{
    		p.age = 45;
    	}
    	else
    	{
    		p.age = 26;
    	}
    	//
    	Spinner genderSpinner=(Spinner) findViewById(R.id.spinner2);

    	if (genderSpinner.getSelectedItem().toString().equals("Male"))
    	{
    		p.gender = "Male";
    	}
    	else if (genderSpinner.getSelectedItem().toString().equals("Female"))
    	{
    		p.gender = "Female";
    	}
    	else
    	{
    		p.gender = "undefined";
    	}
    	//
    	
    	//
    	Spinner ethnicity =(Spinner) findViewById(R.id.spinner3);
    	if (ethnicity.getSelectedItem().toString().equals("White"))
    	{
    		p.Race = "White";
    	}
    	else if (ethnicity.getSelectedItem().toString().equals("African American"))
    	{
    		p.Race = "African American";
    	}
    	else if (ethnicity.getSelectedItem().toString().equals("Asian"))
    	{
    		p.Race = "Asian";
    	}
    	else if (ethnicity.getSelectedItem().toString().equals("Indian"))
    	{
    		p.Race = "Indian";
    	}
    	else if (ethnicity.getSelectedItem().toString().equals("Nordic"))
    	{
    		p.Race = "Nordic";
    	}
    	else if (ethnicity.getSelectedItem().toString().equals("African"))
    	{
    		p.Race = "African";
    	}
    	//
    	
    	if (!readExistingUser) //first instance of program
    	{
    		currentPerson = p;
    		userList.add(currentPerson);
    	} 
    	else 
    	{
    		
	    	boolean foundUser = false;
	    	for (int i = 0;i<userList.size();i++)
	    	{
	    		
	    		if ((userList.get(i).Name.equals(p.Name)) && (userList.get(i).age == p.age)){
	    			currentPerson = userList.get(i);
	    			foundUser = true;
	    			break;
	    	   }
	    		
	    	}
	    	
	    	if (!foundUser)
	    	{
	    		currentPerson = p;
	    		userList.add(currentPerson);
	    	}
    	}    	
    	
    	Intent intent = new Intent(this, IntroScreen2.class);
    	Bundle bundle = new Bundle();
 
    	bundle.putParcelable("user", currentPerson);
    	bundle.putParcelableArrayList("userList",userList);
    	//bundle.putParcelableArrayList("testList", testList);
    	intent.putExtras(bundle);
        startActivity(intent);
    }
}
