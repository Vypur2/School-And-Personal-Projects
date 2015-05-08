package com.example.biastester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Results extends Activity {
	ArrayList<Slide> prevTest = new ArrayList<Slide>();
	ArrayList<TestType> testList = new ArrayList<TestType>();
	ArrayList<Person> userList;
	ListView mainlistview;
	Person currentuser;
	String resultString;

	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		testList = savedInstanceState.getParcelableArrayList("testList");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		mainlistview = (ListView)findViewById(R.id.listView1);
		
		
		if (savedInstanceState != null) {
			testList = savedInstanceState.getParcelableArrayList("testList");
		}
		
		
		TestType newT = new TestType();
		Bundle b = getIntent().getExtras();
		
		if (b != null)
		{
			currentuser = b.getParcelable("user");
			prevTest = b.getParcelableArrayList("history");		
			newT = b.getParcelable("testtype");
			userList = b.getParcelableArrayList("userList");
			//testList = b.getParcelableArrayList("testList");
			testList = currentuser.tests;
		}
		
		//
		prevTest = (ArrayList<Slide>) newT.slides;
		String hour = newT.dateAndTime.substring(9,11);
		String minute = newT.dateAndTime.substring(11,13);
		String seconds = newT.dateAndTime.substring(13);
		
        resultString = parseResults(newT.tag, newT.slides);
		newT.Results = parseResults(newT.tag, newT.slides);
		testList.add(newT);
		
		
		// gets user in the userlist.
		for (int k = 0;k<userList.size();k++)
		{
			if ((userList.get(k).Name.equals(currentuser.Name)) && (userList.get(k).age == currentuser.age))
			{
				userList.get(k).tests = testList;
			}
		}
		//
		
		ArrayList<String> itemList2 = new ArrayList<String>();
		ArrayList<String> testTextList = new ArrayList<String>();
		
		for (int i = 0; i <testList.size();i++)
		{
			testTextList.add(testList.get(i).tag);
		}
		
		
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (int i = 0;i < testList.size();i++) {
		    Map<String, String> datum = new HashMap<String, String>(2);
		    datum.put("title", testList.get(i).tag);
		    datum.put("date", testList.get(i).dateAndTime);
		    data.add(datum);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"title", "date"},
                new int[] {android.R.id.text1,
                           android.R.id.text2});	
		mainlistview.setAdapter(adapter); 	
		
		for (int i = 0; i < prevTest.size();i++)
		{
			if (i > 0)
			{
				prevTest.get(i).prev = prevTest.get(i-1);
			}
			itemList2.add(prevTest.get(i).filename);
		}
		
		mainlistview.setOnItemClickListener(new AdapterView.OnItemClickListener(){ 
			 public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			     Intent intent2 = new Intent(getBaseContext(), Results2.class);
			     Bundle bundle = new Bundle();
			     bundle.putParcelableArrayList("slides",(ArrayList<? extends Parcelable>) testList.get(position).slides);
			     bundle.putString("results",testList.get(position).Results);
			     intent2.putExtras(bundle);
			     startActivity(intent2);
			 }
		});
		TextView tv = (TextView)findViewById(R.id.resultsView);
		String s = parseResults(newT.tag, newT.slides);
		tv.setText(s);
		
		int zz = 0;
		for (int z = 0;z < testList.size();z++)
		{
			zz = z;
		}
		System.out.println(testList.get(zz).tag);
		System.out.println(s);
		testList.get(zz).Results = s;

	}
	
	
	private void sortTestList(ArrayList<TestType> testList2) {
		// TODO fill in
	}

	public String parseResults(String testType, List<Slide> slides){
		ArrayList<Slide> trueHitSlide = new ArrayList<Slide>();
		ArrayList<Float> trueHitTimes = new ArrayList<Float>();
		ArrayList<String> trueRaceList = new ArrayList<String>();
		ArrayList<String> trueGenderList = new ArrayList<String>();
		ArrayList<String> falseRaceList = new ArrayList<String>();
		ArrayList<String> falseGenderList = new ArrayList<String>();
		
		String retString = "";
		
		int wmc=0;/*white male count*/
		float wmt=0.0f;/*white male total*/
		float wma=0.0f;/*white male average*/
		int wfc=0;/*white female count*/
		float wft=0.0f;/*white male total*/
		float wfa=0.0f;
		int amc=0;/*asian*/
		float amt=0.0f;
		float ama=0.0f;
		int afc=0;
		float aft=0.0f;
		float afa=0.0f;
		int bmc=0;/*black*/
		float bmt=0.0f;
		float bma=0.0f;
		int bfc=0;
		float bft=0.0f;
		float bfa=0.0f;
		
		//for(Slide s : slides){
		for (int j = 0;j<slides.size();j++)
		{
			Slide s = slides.get(j);
			if(s.prev==null){continue;}/*an attempt at skipping the null pointer exceptions*/
			if(s.prev.Race==null){continue;}/*an attempt at skipping the null pointer exceptions*/
			if(testType.equals("positive") && s.filename.equals("znazi")){
				retString += "You think nazi is positive?  Are You Kidding Me?\n";
			}
			if(!s.isPhoto){/*is NOT a picture*/
				if(s.Race.equals(testType) && s.wasTapped){/*true hit.  The Race field of a word slide should be it's category. violence, sexual, benign, etc*/
					trueHitTimes.add(s.tapTime);/*put time in one list*/
					trueRaceList.add(s.prev.Race);/*the previous picture's race in another*/
					trueGenderList.add(s.prev.Gender);/*the previous picture's gender in another*/
					
				}
				else if (!s.Race.equals(testType) && s.wasTapped){/*false hit.  if the person tapped a nonsexual word in a sexual test for instance, record the Race and gender that came before*/
					falseRaceList.add(s.prev.Race);
					falseGenderList.add(s.prev.Gender);
				}
			}
			else if(s.isPhoto && s.wasTapped){/*is a picture.  Pictures should not be tapped, thus are alwayse a false hit. Add the races and genders to a list*/
				falseRaceList.add(s.Race);
				falseGenderList.add(s.Gender);
			}
		}
		
		for(int i=0; i<trueRaceList.size(); i++){/*check the race of the pictures that preceded all true hit slides*/
			if(trueRaceList.get(i).equals("White")){
				if(trueGenderList.get(i).equals("Male")){
					wmc++;
					wmt += trueHitTimes.get(i);/*the time it took to correctly identify a word after seeing a white male*/
				}
				else if(trueGenderList.get(i).equals("Female")){
					wfc++;
					wft += trueHitTimes.get(i);/*the time it took to correctly identify a word after seeing a white femal*/
				}
			}
			else if(trueRaceList.get(i).equals("Black")){
				if(trueGenderList.get(i).equals("Male")){
					bmc++;
					bmt += trueHitTimes.get(i);
				}
				else if(trueGenderList.get(i).equals("Female")){
					bfc++;
					bft += trueHitTimes.get(i);
				}
			}
			else if(trueRaceList.get(i).equals("Asian")){
				if(trueGenderList.get(i).equals("Male")){
					amc++;
					amt += trueHitTimes.get(i);
				}
				else if(trueGenderList.get(i).equals("Female")){
					afc++;
					aft += trueHitTimes.get(i);
				}
			}
		}
		if(wmc==0){wmc=1;}/*if count is zero, avoid divide by zero*/
		if(wfc==0){wfc=1;}
		if(bmc==0){bmc=1;}
		if(bfc==0){bfc=1;}
		if(ama==0){ama=1;}
		if(afa==0){afa=1;}
		wma = wmt / wmc; /*White Male Average = White Male Total / White Male Count*/
		wfa = wft / wfc;
		bma = bmt / bmc;
		bfa = bft / bfc;
		ama = amt / amc;
		afa = aft / afc;
		
		float averageHitTime = (wma+wfa+bma+bfa+ama+afa)/6;/*average hit time across all races and genders*/
		if((averageHitTime-wma)/averageHitTime > 0.1 ){
			if(testType.equals("violence")){
				retString += "-You are more likely to view white males as violent.\n";
			}
			else if(testType.equals("sexual")){
				retString += "-You are more likely to view white males as sexual.\n";
			}
			else if(testType.equals("positive")){
				retString += "-You are more likely to view white males as positive.\n";
			}
		}
		if((averageHitTime-wfa)/averageHitTime > 0.1 ){
			if(testType.equals("violence")){
				retString += "-You are more likely to view white females as violent.\n";
			}
			else if(testType.equals("sexual")){
				retString += "-You are more likely to view white females as sexual.\n";
			}
			else if(testType.equals("positive")){
				retString += "-You are more likely to view white females as positive.\n";
			}
		}
		if((averageHitTime-bma)/averageHitTime > 0.1 ){
			if(testType.equals("violence")){
				retString += "-You are more likely to view black males as violent.\n";
			}
			else if(testType.equals("sexual")){
				retString += "-You are more likely to view black males as sexual.\n";
			}
			else if(testType.equals("positive")){
				retString += "-You are more likely to view black males as positive.\n";
			}
		}
		if((averageHitTime-bfa)/averageHitTime > 0.1 ){
			if(testType.equals("violence")){
				retString += "-You are more likely to view black females as violent.\n";
			}
			else if(testType.equals("sexual")){
				retString += "-You are more likely to view black females as sexual.\n";
			}
			else if(testType.equals("positive")){
				retString += "-You are more likely to view black females as positive.\n";
			}
		}
		if((averageHitTime-ama)/averageHitTime > 0.1 ){
			if(testType.equals("violence")){
				retString += "-You are more likely to view asian males as violent.\n";
			}
			else if(testType.equals("sexual")){
				retString += "-You are more likely to view asian males as sexual.\n";
			}
			else if(testType.equals("positive")){
				retString += "-You are more likely to view asian males as positive.\n";
			}
		}
		if((averageHitTime-afa)/averageHitTime > 0.1 ){
			if(testType.equals("violence")){
				retString += "-You are more likely to view asian females as violent.\n";
			}
			else if(testType.equals("sexual")){
				retString += "-You are more likely to view asian females as sexual.\n";
			}
			else if(testType.equals("positive")){
				retString += "-You are more likely to view asian females as positive.\n";
			}
		}
		int fw=0;/*false white*/
		int fb=0;/*false black*/
		int fa=0;/*false asian*/
		int fm=0;/*false male*/
		int ff=0;/*false female*/
		
		/*false hit behavior*/
		for(int i=0; i<falseRaceList.size(); i++){
			if(falseRaceList.get(i).equals("White")){
				fw++;
			}
			if(falseRaceList.get(i).equals("Black")){
				fb++;
			}
			if(falseRaceList.get(i).equals("Asian")){
				fa++;
			}
			if(falseGenderList.get(i).equals("Male")){
				fm++;
			}
			if(falseGenderList.get(i).equals("Female")){
				ff++;
			}
		}
		if(fw>2){
			if(testType.equals("violence")){
				retString += "-You are more likely to think a word is violent after seeing a white person.\n";
			}
			else if(testType.equals("sexual")){
				retString += "-You are more likely to think a word is sexual after seeing a white person.\n";
			}
			else if(testType.equals("positive")){
				retString += "-You are more likely to think a word is positive after seeing a white person.\n";
			}
		}
		if(fb>2){
			if(testType.equals("violence")){
				retString += "-You are more likely to think a word is violent after seeing a black person.\n";
			}
			else if(testType.equals("sexual")){
				retString += "-You are more likely to think a word is sexual after seeing a black person.\n";
			}
			else if(testType.equals("positive")){
				retString += "-You are more likely to think a word is positive after seeing a black person.\n";
			}
		}
		if(fa>2){
			if(testType.equals("violence")){
				retString += "-You are more likely to think a word is violent after seeing a asian person.\n";
			}
			else if(testType.equals("sexual")){
				retString += "-You are more likely to think a word is sexual after seeing a asian person.\n";
			}
			else if(testType.equals("positive")){
				retString += "-You are more likely to think a word is positive after seeing a asian person.\n";
			}
		}
		if(fm>2){
			if(testType.equals("violence")){
				retString += "-You are more likely to think a word is violent after seeing a Male.\n";
			}
			else if(testType.equals("sexual")){
				retString += "-You are more likely to think a word is sexual after seeing a Male.\n";
			}
			else if(testType.equals("positive")){
				retString += "-You are more likely to think a word is positive after seeing a Male.\n";
			}
		}
		if(ff>2){
			if(testType.equals("violence")){
				retString += "-You are more likely to think a word is violent after seeing a Female.\n";
			}
			else if(testType.equals("sexual")){
				retString += "-You are more likely to think a word is sexual after seeing a Female.\n";
			}
			else if(testType.equals("positive")){
				retString += "-You are more likely to think a word is positive after seeing a Female.\n";
			}
		}
		return retString;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
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
	
	public void goToMain(View view)
	{
		for (int k = 0;k<userList.size();k++)
		{
			if ((userList.get(k).Name.equals(currentuser.Name)) && (userList.get(k).age == currentuser.age))
			{
				userList.get(k).tests = testList;
			}
		}
		
		
		Intent intent = new Intent(this, BiasTester.class);
		Bundle b = new Bundle();
		//b.putParcelableArrayList("testList", testList);
		currentuser.tests = testList;
		b.putParcelable("user",currentuser);
		b.putParcelableArrayList("userList",userList);
		intent.putExtras(b);
        startActivity(intent);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		
		savedInstanceState.putParcelableArrayList("testList",testList);
			
	}
}
