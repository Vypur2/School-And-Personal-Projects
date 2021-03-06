package com.example.biastester;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PositiveTest extends Activity {
	
	public long deltaT = 0;
	ArrayList<Slide> slideList = new ArrayList<Slide>();
	ArrayList<Slide> violentList = new ArrayList<Slide>();
	ArrayList<Slide> slideHistory = new ArrayList<Slide>();
	ArrayList<Slide> textSlides = new ArrayList<Slide>();
	Slide currentSlide;
	Slide previous;

	TestType positiveTest = new TestType();
		
	/*Calendar c = Calendar.getInstance();*/
	long seconds = System.currentTimeMillis();/*c.get(Calendar.SECOND);*/
	long interval = 5000;
	long currentFrameTime = 0;
	long frameStartTime = 0;
	boolean cycleImages = false;
	int filenameIndex = 0;
	//public ImageView mImageView = (ImageView) this.findViewById(R.id.imageView1);
	int slideCounter = 0;
	
	
		
	Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
        	frameStartTime = System.currentTimeMillis();
            imageCycle();
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_positive_test);
		
		positiveTest.tag = "positive";
		positiveTest.date = 11223333;
		
		Slide ainsley = new Slide("ainsley","Black","Male",true,false);
		slideList.add(ainsley);
		Slide blackm6 = new Slide("blackm6","Black","Male",true,false);
		slideList.add(blackm6);
		Slide blackm7 = new Slide("blackm7","Black","Male",true,false);
		slideList.add(blackm7);
		Slide blackm8 = new Slide("blackm8","Black","Male",true,false);
		slideList.add(blackm8);
		Slide blackm9 = new Slide("blackm9","Black","Male",true,false);
		slideList.add(blackm9);
		Slide blackm10 = new Slide("blackm10","Black","Male",true,false);
		slideList.add(blackm10);
		Slide sulu = new Slide("sulu","Asian","Male",true,false);
		slideList.add(sulu);
		Slide picard = new Slide("picard","White","Male",true,false);
		slideList.add(picard);
		Slide willSmith = new Slide("willsmith","Black","Male",true,false);
		slideList.add(willSmith);
		Slide fatGuy = new Slide("fatblack","Black","Male",true,false);
		slideList.add(fatGuy);
		Slide obama = new Slide("obama","Black","Male",true,false);
		slideList.add(obama);
		Slide blackMan = new Slide("blackman1","Black","Male",true,false);
		slideList.add(blackMan);
		Slide blackMenHair = new Slide("blackmenhair","Black","Male",true,false);
		slideList.add(blackMenHair);
		Slide angryMichelle = new Slide("angrymichelle","Black","Female",true, false);
		slideList.add(angryMichelle);
		Slide blackWoman1 = new Slide("blackwoman1","Black","Female",true,false);
		slideList.add(blackWoman1);
		Slide dload = new Slide("download","Black","Female",true,false);
		slideList.add(dload);
		Slide fine = new Slide("fine","Black","Female",true,false);
		slideList.add(fine);
		Slide obw = new Slide("obw","Black","Female",true,false);
		slideList.add(obw);
		Slide blackw6 = new Slide("blackw6","Black","Female",true,false);
		slideList.add(blackw6);
		Slide blackw7 = new Slide("blackw7","Black","Female",true,false);
		slideList.add(blackw7);
		Slide blackw8 = new Slide("blackw8","Black","Female",true,false);
		slideList.add(blackw8);
		Slide blackw9 = new Slide("blackw9","Black","Female",true,false);
		slideList.add(blackw9);
		Slide blackw10 = new Slide("blackw10","Black","Female",true,false);
		slideList.add(blackw10);
		Slide asianMan = new Slide("asianman","Asian","Male",true,false);
		slideList.add(asianMan);
		Slide asianMan1 = new Slide("asianman1","Asian","Male",true,false);
		slideList.add(asianMan1);
		Slide asianMan2 = new Slide("asianman2","Asian","Male",true,false);
		slideList.add(asianMan2);
		Slide mickeyRooney = new Slide("mickeyrooney","Asian","Male",true,false);
		slideList.add(mickeyRooney);
		Slide samuraiCat = new Slide("samcat","Asian","Male",true,false);
		slideList.add(samuraiCat);
		Slide asian6 = new Slide("asian6","Asian","Male",true,false);
		slideList.add(asian6);
		Slide asian7 = new Slide("asian7","Asian","Male",true,false);
		slideList.add(asian7);
		Slide asian8 = new Slide("asian8","Asian","Male",true,false);
		slideList.add(asian8);
		Slide asian9 = new Slide("asian9","Asian","Male",true,false);
		slideList.add(asian9);
		Slide asian10 = new Slide("asian10","Asian","Male",true,false);
		slideList.add(asian10);
		Slide asianWomanShushing = new Slide("asianwoman2","Asian","Female",true,false);
		slideList.add(asianWomanShushing);
		Slide asianWoman1 = new Slide("asianwoman1","Asian","Female",true,false);
		slideList.add(asianWoman1);
		Slide asianWoman3 = new Slide("asianwoman3","Asian","Female",true,false);
		slideList.add(asianWoman3);
		Slide asianWoman4 = new Slide("asianwoman4","Asian","Female",true,false);
		slideList.add(asianWoman4);
		Slide sailorMoon = new Slide("sailormoon","Asian","Female",true,false);
		slideList.add(sailorMoon);
		Slide asianw6 = new Slide("asianw6","Asian","Female",true,false);
		slideList.add(asianw6);
		Slide asianw7 = new Slide("asianw7","Asian","Female",true,false);
		slideList.add(asianw7);
		Slide asianw8 = new Slide("asianw8","Asian","Female",true,false);
		slideList.add(asianw8);
		Slide asianw9 = new Slide("asianw9","Asian","Female",true,false);
		slideList.add(asianw9);
		Slide asianw10 = new Slide("asianw10","Asian","Female",true,false);
		slideList.add(asianw10);
		Slide asianw11 = new Slide("asianw11","Asian","Female",true,false);
		slideList.add(asianw11);
		Slide jeff = new Slide("jeff","White","Male",true,false);
		slideList.add(jeff);
		Slide killer2 = new Slide("killer2","White","Male",true,false);
		slideList.add(killer2);
		Slide gacy = new Slide("gacy","White","Male",true,false);
		slideList.add(gacy);
		Slide dylan = new Slide("dylan","White","Male",true,false);
		slideList.add(dylan);
		Slide eric = new Slide("eric","White","Male",true,false);
		slideList.add(eric);
		Slide whitem6 = new Slide("whitem6","White","Male",true,false);
		slideList.add(whitem6);
		Slide whitem7 = new Slide("whitem7","White","Male",true,false);
		slideList.add(whitem7);
		Slide whitem8 = new Slide("whitem8","White","Male",true,false);
		slideList.add(whitem8);
		Slide whitem9 = new Slide("whitem9","White","Male",true,false);
		slideList.add(whitem9);
		Slide whitem10 = new Slide("whitem10","White","Male",true,false);
		slideList.add(whitem10);
		Slide casey1 = new Slide("casey1","White","Female",true,false);
		slideList.add(casey1);
		Slide casey2 = new Slide("casey2","White","Female",true,false);
		slideList.add(casey2);
		Slide casey3 = new Slide("casey3","White","Female",true,false);
		slideList.add(casey3);
		Slide casey4 = new Slide("casey4","White","Female",true,false);
		slideList.add(casey4);
		Slide casey6 = new Slide("casey6","White","Female",true,false);
		slideList.add(casey6);
		Slide casey7 = new Slide("casey7","White","Female",true,false);
		slideList.add(casey7);
		Slide casey8 = new Slide("casey8","White","Female",true,false);
		slideList.add(casey8);
		Slide casey9 = new Slide("casey9","White","Female",true,false);
		slideList.add(casey9);
		Slide casey10 = new Slide("casey10","White","Female",true,false);
		slideList.add(casey10);
		Slide casey5 = new Slide("casey5","White","Female",true,false);
		slideList.add(casey5);
		
		
		//populate text slides here
		Slide amenable = new Slide("zamenable","positive","",false, false);
		textSlides.add(amenable);
		Slide articulate = new Slide("zarticulate","positive","",false, false);
		textSlides.add(articulate);
		Slide award = new Slide("zaward","positive","",false, false);
		textSlides.add(award);
		Slide brainy = new Slide("zbrainy","positive","",false, false);
		textSlides.add(brainy);
		Slide bravo = new Slide("zbravo","positive","",false, false);
		textSlides.add(bravo);
		Slide brilliant = new Slide("zbrilliant","positive","",false, false);
		textSlides.add(brilliant);
		Slide commendation = new Slide("zcommendation","positive","",false, false);
		textSlides.add(commendation);
		Slide couragous = new Slide("zcouragous","positive","",false, false);
		textSlides.add(couragous);
		Slide cunning = new Slide("zcunning","positive","",false, false);
		textSlides.add(cunning);
		Slide dependable = new Slide("zdependable","positive","",false, false);
		textSlides.add(dependable);
		Slide determined = new Slide("zdetermined","positive","",false, false);
		textSlides.add(determined);
		Slide elated = new Slide("zelated","positive","",false, false);
		textSlides.add(elated);
		Slide energetic = new Slide("zenergetic","positive","",false, false);
		textSlides.add(energetic);
		Slide fantastic = new Slide("fantastic","positive","",false, false);
		textSlides.add(fantastic);
		Slide freedom = new Slide("zfreedom","positive","",false, false);
		textSlides.add(freedom);
		Slide fun = new Slide("zfun","positive","",false, false);
		textSlides.add(fun);
		Slide glowing = new Slide("zglowing","positive","",false, false);
		textSlides.add(glowing);
		Slide graduate = new Slide("zgraduate","positive","",false, false);
		textSlides.add(graduate);
		Slide great = new Slide("zgreat","positive","",false, false);
		textSlides.add(great);
		Slide helpful = new Slide("zhelpful","positive","",false, false);
		textSlides.add(helpful);
		Slide hilarious = new Slide("zhilarious","positive","",false, false);
		textSlides.add(hilarious);
		Slide hope = new Slide("zhope","positive","",false, false);
		textSlides.add(hope);
		Slide ingenious = new Slide("zingenious","positive","",false, false);
		textSlides.add(ingenious);
		Slide joking = new Slide("zjoking","positive","",false, false);
		textSlides.add(joking);
		Slide jovial = new Slide("zjovial","positive","",false, false);
		textSlides.add(jovial);
		Slide likable = new Slide("zlikable","positive","",false, false);
		textSlides.add(likable);
		Slide lovable = new Slide("zlovable","positive","",false, false);
		textSlides.add(lovable);
		Slide love = new Slide("zlove","positive","",false, false);
		textSlides.add(love);
		Slide marvelous = new Slide("zmarvelous","positive","",false, false);
		textSlides.add(marvelous);
		Slide merit = new Slide("zmerit","positive","",false, false);
		textSlides.add(merit);
		Slide merry = new Slide("zmerry","positive","",false, false);
		textSlides.add(merry);
		Slide neat = new Slide("zneat","positive","",false, false);
		textSlides.add(neat);
		Slide nice = new Slide("znice","positive","",false, false);
		textSlides.add(nice);
		Slide noble = new Slide("znoble","positive","",false, false);
		textSlides.add(noble);
		Slide optimism = new Slide("zoptimism","positive","",false, false);
		textSlides.add(optimism);
		Slide outstanding = new Slide("zoutstanding","positive","",false, false);
		textSlides.add(outstanding);
		Slide peaceful = new Slide("zpeaceful","positive","",false, false);
		textSlides.add(peaceful);
		Slide perfect = new Slide("zperfect","positive","",false, false);
		textSlides.add(perfect);
		Slide pleasing = new Slide("zperfect","positive","",false, false);
		textSlides.add(pleasing);
		Slide quality = new Slide("zquality","positive","",false, false);
		textSlides.add(quality);
		Slide respect = new Slide("zrespect","positive","",false, false);
		textSlides.add(respect);
		Slide righteous = new Slide("zrighteous","positive","",false, false);
		textSlides.add(righteous);
		Slide smiles = new Slide("zsmiles","positive","",false, false);
		textSlides.add(smiles);
		Slide zsuper = new Slide("zsuper","positive","",false, false);
		textSlides.add(zsuper);
		Slide sweet = new Slide("zsweet","positive","",false, false);
		textSlides.add(sweet);
		Slide terrific = new Slide("zterrific","positive","",false, false);
		textSlides.add(terrific);
		Slide thrilling = new Slide("zthrilling","positive","",false, false);
		textSlides.add(thrilling);
		Slide trustworthy = new Slide("ztrustworthy","positive","",false, false);
		textSlides.add(trustworthy);
		Slide unique = new Slide("zunique","positive","",false, false);
		textSlides.add(unique);
		Slide uplifting = new Slide("zuplifting","positive","",false, false);
		textSlides.add(uplifting);
		Slide values = new Slide("zvalues","positive","",false, false);
		textSlides.add(values);
		Slide victory = new Slide("zvictory","positive","",false, false);
		textSlides.add(victory);
		Slide welcoming = new Slide("zwelcoming","positive","",false, false);
		textSlides.add(welcoming);
		Slide winner = new Slide("zwinner","positive","",false, false);
		textSlides.add(winner);
		Slide worthy = new Slide("zworthy","positive","",false, false);
		textSlides.add(worthy);
			
		
		Slide and = new Slide("zand", "benign", "", false, false);
		textSlides.add(and);
		Slide apple = new Slide("zapple", "benign", "", false, false);
		textSlides.add(apple);
		Slide blender = new Slide("zblender", "benign", "", false, false);
		textSlides.add(blender);
		Slide bottle = new Slide("zbottle", "benign", "", false, false);
		textSlides.add(bottle);
		Slide cake = new Slide("zcake", "benign", "", false, false);
		textSlides.add(cake);
		Slide clip = new Slide("zclip", "benign", "", false, false);
		textSlides.add(clip);
		Slide cows = new Slide("zcows", "benign", "", false, false);
		textSlides.add(cows);
		Slide crayon = new Slide("zcrayon", "benign", "", false, false);
		textSlides.add(crayon);
		Slide dental = new Slide("zdental", "benign", "", false, false);
		textSlides.add(dental);
		Slide door = new Slide("zdoor", "benign", "", false, false);
		textSlides.add(door);
		Slide each = new Slide("zeach", "benign", "", false, false);
		textSlides.add(each);
		Slide eagle = new Slide("zeagle", "benign", "", false, false);
		textSlides.add(eagle);
		Slide eclipse = new Slide("zeclipse", "benign", "", false, false);
		textSlides.add(eclipse);
		Slide find = new Slide("zfind", "benign", "", false, false);
		textSlides.add(find);
		Slide finger = new Slide("zfinger", "benign", "", false, false);
		textSlides.add(finger);
		Slide forest = new Slide("zforest", "benign", "", false, false);
		textSlides.add(forest);
		Slide found = new Slide("zfound", "benign", "", false, false);
		textSlides.add(found);
		Slide ground = new Slide("zground", "benign", "", false, false);
		textSlides.add(ground);
		Slide gum = new Slide("zgum", "benign", "", false, false);
		textSlides.add(gum);
		Slide hand = new Slide("zhand", "benign", "", false, false);
		textSlides.add(hand);
		Slide hotel = new Slide("zhotel", "benign", "", false, false);
		textSlides.add(hotel);
		Slide igloo = new Slide("zigloo", "benign", "", false, false);
		textSlides.add(igloo);
		Slide india = new Slide("zindia", "benign", "", false, false);
		textSlides.add(india);
		Slide jester = new Slide("zjester", "benign", "", false, false);
		textSlides.add(jester);
		Slide jump = new Slide("zjump", "benign", "", false, false);
		textSlides.add(jump);
		Slide kale = new Slide("zkale", "benign", "", false, false);
		textSlides.add(kale);
		Slide kite = new Slide("zkite", "benign", "", false, false);
		textSlides.add(kite);
		Slide lemon = new Slide("zlemon", "benign", "", false, false);
		textSlides.add(lemon);
		Slide loud = new Slide("zloud", "benign", "", false, false);
		textSlides.add(loud);
		Slide mandate = new Slide("zmandate", "benign", "", false, false);
		textSlides.add(mandate);
		Slide march = new Slide("zmarch", "benign", "", false, false);
		textSlides.add(march);
		Slide needful = new Slide("zneedful", "benign", "", false, false);
		textSlides.add(needful);
		Slide news = new Slide("znews", "benign", "", false, false);
		textSlides.add(news);
		Slide open = new Slide("zopen", "benign", "", false, false);
		textSlides.add(open);
		Slide opiate = new Slide("zopiate", "benign", "", false, false);
		textSlides.add(opiate);
		Slide pants = new Slide("zpants", "benign", "", false, false);
		textSlides.add(pants);
		Slide pencil = new Slide("zpencil", "benign", "", false, false);
		textSlides.add(pencil);
		Slide quickly = new Slide("zquickly", "benign", "", false, false);
		textSlides.add(quickly);
		Slide qwerty = new Slide("zqwerty", "benign", "", false, false);
		textSlides.add(qwerty);
		Slide radio = new Slide("zradio", "benign", "", false, false);
		textSlides.add(radio);
		Slide red = new Slide("zred", "benign", "", false, false);
		textSlides.add(red);
		Slide rotunda = new Slide("zrotunda", "benign", "", false, false);
		textSlides.add(rotunda);
		Slide scales = new Slide("zscales", "benign", "", false, false);
		textSlides.add(scales);
		Slide sound = new Slide("zsound", "benign", "", false, false);
		textSlides.add(sound);
		Slide throne = new Slide("zthrone", "benign", "", false, false);
		textSlides.add(throne);
		Slide time = new Slide("ztime", "benign", "", false, false);
		textSlides.add(time);
		Slide treble = new Slide("ztreble", "benign", "", false, false);
		textSlides.add(treble);
		Slide umbrella = new Slide("zumbrella", "benign", "", false, false);
		textSlides.add(umbrella);
		Slide upstairs = new Slide("zupstairs", "benign", "", false, false);
		textSlides.add(upstairs);
		Slide voices = new Slide("zvoices", "benign", "", false, false);
		textSlides.add(voices);
		Slide vowel = new Slide("zvowel", "benign", "", false, false);
		textSlides.add(vowel);
		Slide words = new Slide("zwords", "benign", "", false, false);
		textSlides.add(words);
		Slide world = new Slide("zworld", "benign", "", false, false);
		textSlides.add(world);
		Slide yellow = new Slide("zyellow", "benign", "", false, false);
		textSlides.add(yellow);
		
		runnable.run();
		
	}
	
	public boolean onTouchEvent(MotionEvent event){
		checkTime();
		return true;
	}
	
	void imageCycle()
    {
		int i =0;
		ImageView mImageView = (ImageView) this.findViewById(R.id.imageView1);
		if (slideCounter == 0)
		{
			Random randomGenerator = new Random();
			int randomInt;
			randomInt = randomGenerator.nextInt(slideList.size());
			currentSlide = slideList.get(randomInt);
			slideList.remove(randomInt);
			int imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
			mImageView.setImageResource(imageRes);
			slideCounter = 1;
			previous = currentSlide;
			slideHistory.add(currentSlide);
			handler.postDelayed(runnable, 1000);
			return;
		}
		else
		{
			if (slideCounter >= 120) //number of slides in 2 minutes
			{
				loadResults(mImageView);
				//this is innefficient but i don't really care.
			}
			else
			{
				if (currentSlide.prev != null)
				{
					if (!currentSlide.prev.wasTapped)
					{
						currentSlide.prev.tapTime = 1000f;
					}
				}
				int imageRes = 0;
				mImageView.setImageResource(imageRes);

				Random randomGenerator = new Random();
				int randomInt;
				if (slideCounter%2 != 1) //take a picture
				{
					//generate again for size less than the size of the slides array
					randomInt = randomGenerator.nextInt(slideList.size());	
					currentSlide = slideList.get(randomInt);
					slideList.remove(randomInt);
					currentSlide.wasTapped = false;
					currentSlide.prev = previous;
					imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
					mImageView.setImageResource(imageRes);
				} 
				else //take a text slide
				{
					//generate again for size less than the size of the slides array
					randomInt = randomGenerator.nextInt(textSlides.size());
		
					currentSlide = textSlides.get(randomInt);
					textSlides.remove(randomInt);
					currentSlide.wasTapped = false;
					currentSlide.prev = previous;
					imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
					mImageView.setImageResource(imageRes);
				}
				previous = currentSlide;
				slideCounter++;
				slideHistory.add(currentSlide);
				handler.postDelayed(runnable, 1000);
				return;
			}			
		}
        //Toast.makeText(getBaseContext(),"test",Toast.LENGTH_SHORT).show();  
    }
	
	public void checkTime(){
		long currSeconds = System.currentTimeMillis();
		deltaT = currSeconds - frameStartTime; 
		if (currentSlide.wasTapped)
		{
			
		}
		else
		{
			currentSlide.tapTime = deltaT;
			currentSlide.wasTapped = true;
		}
			
		deltaT = currSeconds;
	}
	
	public void loadResults(View view)
	{
		handler.removeCallbacksAndMessages(null);
		
		Bundle b = getIntent().getExtras();
		Person p = b.getParcelable("user");
		
		Intent intent = new Intent(this, Results.class);
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("history",slideHistory);
		bundle.putParcelable("testtype", positiveTest);
		bundle.putParcelable("user", p);
		ArrayList<TestType> testList = b.getParcelableArrayList("testList");
		bundle.putParcelableArrayList("testList",testList);
		positiveTest.slides = slideHistory;
		ArrayList<Person> ppl = b.getParcelableArrayList("userList");
		bundle.putParcelableArrayList("userList",ppl);
		
		intent.putExtras(bundle);
		
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.positive_test, menu);
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
