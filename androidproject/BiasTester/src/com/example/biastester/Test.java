package com.example.biastester;

import java.util.ArrayList;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
/*import android.transition.Slide;*/
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Test extends ActionBarActivity{
	
	public long deltaT = 0;
	ArrayList<Slide> slideList = new ArrayList<Slide>();
	ArrayList<Slide> sexList = new ArrayList<Slide>();
	ArrayList<Slide> violentList = new ArrayList<Slide>();
	ArrayList<Slide> positiveSlides = new ArrayList<Slide>();
	ArrayList<Slide> slideHistory = new ArrayList<Slide>();
	ArrayList<Slide> textSlides = new ArrayList<Slide>();
	Slide currentSlide;
	Slide previous;

	TestType violenceTest = new TestType();
		
	/*Calendar c = Calendar.getInstance();*/
	long seconds = System.currentTimeMillis();/*c.get(Calendar.SECOND);*/
	long interval = 5000;
	long currentFrameTime = 0;
	long frameStartTime = 0;
	boolean cycleImages = false;
	int filenameIndex = 0;
	//public ImageView mImageView = (ImageView) this.findViewById(R.id.imageView1);
	int slideCounter = 0;
	ImageView mImageView;
	
	
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
		setContentView(R.layout.activity_test);
		
		violenceTest.tag = "violence";
		violenceTest.date = 11111;
		mImageView = (ImageView) findViewById(R.id.imageView1);
		
		//populate picture slides here
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
		
		Slide anal = new Slide("zanal","sexual","",false,false);
		sexList.add(anal);
		Slide blowjob = new Slide("zblowjob","sexual","",false,false);
		sexList.add(blowjob);
		Slide bondage = new Slide("zbondage","sexual","",false,false);
		sexList.add(bondage);
		Slide breasts = new Slide("zbreasts","sexual","",false,false);
		sexList.add(breasts);
		Slide cleavage = new Slide("zcleavage","sexual","",false,false);
		sexList.add(cleavage);
		Slide cock = new Slide("zcock","sexual","",false,false);
		sexList.add(cock);
		Slide cunt = new Slide("zcunt","sexual","",false,false);
		sexList.add(cunt);
		Slide dick = new Slide("zdick","sexual","",false,false);
		sexList.add(dick);
		Slide dp = new Slide("zdp","sexual","",false,false);
		sexList.add(dp);
		Slide erection = new Slide("zerection","sexual","",false,false);
		sexList.add(erection);
		Slide fetish = new Slide("zfetish","sexual","",false,false);
		sexList.add(fetish);
		Slide finger = new Slide("zfinger","sexual","",false,false);
		sexList.add(finger);
		Slide fucking = new Slide("zfucking","sexual","",false,false);
		sexList.add(fucking);
		Slide groping = new Slide("zgroping","sexual","",false,false);
		sexList.add(groping);
		Slide handjob = new Slide("zhandjob","sexual","",false,false);
		sexList.add(handjob);
		Slide hard = new Slide("zhard","sexual","",false,false);
		sexList.add(hard);
		Slide insertion = new Slide("zinsertion","sexual","",false,false);
		sexList.add(insertion);
		Slide intercourse = new Slide("zintersourse","sexual","",false,false);
		sexList.add(intercourse);
		Slide jerkoff = new Slide("zjerkoff","sexual","",false,false);
		sexList.add(jerkoff);
		Slide jizz = new Slide("zjizz","sexual","",false,false);
		sexList.add(jizz);
		Slide labia = new Slide("zlabia","sexual","",false,false);
		sexList.add(labia);
		Slide licking = new Slide("zlicking","sexual","",false,false);
		sexList.add(licking);
		Slide masturbation = new Slide("zmasturbation","sexual","",false,false);
		sexList.add(masturbation);
		Slide milf = new Slide("zmilf","sexual","",false,false);
		sexList.add(milf);
		Slide naked = new Slide("znaked","sexual","",false,false);
		sexList.add(naked);
		Slide naughty = new Slide("znaughty","sexual","",false,false);
		sexList.add(naughty);
		Slide orgasm = new Slide("zorgasm","sexual","",false,false);
		sexList.add(orgasm);
		Slide orgy = new Slide("zorgy","sexual","",false,false);
		sexList.add(orgy);
		Slide panties = new Slide("zpanties","sexual","",false,false);
		sexList.add(panties);
		Slide penis = new Slide("zpenis","sexual","",false,false);
		sexList.add(penis);
		Slide queer = new Slide("zqueer","sexual","",false,false);
		sexList.add(queer);
		Slide rimjob = new Slide("zrimjob","sexual","",false,false);
		sexList.add(rimjob);
		Slide roadhead = new Slide("zroadhead","sexual","",false,false);
		sexList.add(roadhead);
		Slide sensual = new Slide("zsensual","sexual","",false,false);
		sexList.add(sensual);
		Slide sex = new Slide("zsex","sexual","",false,false);
		sexList.add(sex);
		Slide sexy = new Slide("zsexy","sexual","",false,false);
		sexList.add(sexy);
		Slide tits = new Slide("ztits","sexual","",false,false);
		sexList.add(tits);
		Slide tongue = new Slide("ztongue","sexual","",false,false);
		sexList.add(tongue);
		Slide vagina = new Slide("zvagina","sexual","",false,false);
		sexList.add(vagina);
		Slide vulva = new Slide("zvulva","sexual","",false,false);
		sexList.add(vulva);
		Slide wet = new Slide("zwet","sexual","",false,false);
		sexList.add(wet);
		
		Slide assassin = new Slide("zassassin","violence","",false,false);
		textSlides.add(assassin);
		Slide assault = new Slide("zassault","violence","",false,false);
		textSlides.add(assault);
		Slide attack = new Slide("zattack","violence","",false,false);
		textSlides.add(attack);
		Slide blood = new Slide("zblood","violence","",false,false);
		textSlides.add(blood);
		Slide brutality = new Slide("zbrutality","violence","",false,false);
		textSlides.add(brutality);
		Slide chaos = new Slide("zchaos","violence","",false,false);
		textSlides.add(chaos);
		Slide corpse = new Slide("zcorpse","violence","",false,false);
		textSlides.add(corpse);
		Slide curbstomp = new Slide("zcurbstomp","violence","",false,false);
		textSlides.add(curbstomp);
		Slide death = new Slide("zdeath","violence","",false,false);
		textSlides.add(death);
		Slide doom = new Slide("zdoom","violence","",false,false);
		textSlides.add(doom);
		Slide execute = new Slide("zexecute","violence","",false,false);
		textSlides.add(execute);
		Slide fatal = new Slide("zfatal","violence","",false,false);
		textSlides.add(fatal);
		Slide fight = new Slide("zfight","violence","",false,false);
		textSlides.add(fight);
		Slide genocide = new Slide("zgenocide","violence","",false,false);
		textSlides.add(genocide);
		Slide grave = new Slide("zgrave","violence","",false,false);
		textSlides.add(grave);
		Slide guns = new Slide("zguns","violence","",false,false);
		textSlides.add(guns);
		Slide hijack = new Slide("zhijack","violence","",false,false);
		textSlides.add(hijack);
		Slide holocaust = new Slide("zholocaust","violence","",false,false);
		textSlides.add(holocaust);
		Slide insurgent = new Slide("zinsurgent","violence","",false,false);
		textSlides.add(insurgent);
		Slide kick = new Slide("zkick","violence","",false,false);
		textSlides.add(kick);
		Slide kidnap = new Slide("zkidnap","violence","",false,false);
		textSlides.add(kidnap);
		Slide knife = new Slide("zknife","violence","",false,false);
		textSlides.add(knife);
		Slide massacre = new Slide("zmassacre","violence","",false,false);
		textSlides.add(massacre);
		Slide murder = new Slide("zmurder","violence","",false,false);
		textSlides.add(murder);
		Slide nazi = new Slide("znazi","violence","",false,false);
		textSlides.add(nazi);
		Slide punch = new Slide("zpunch","violence","",false,false);
		textSlides.add(punch);
		Slide rage = new Slide("zrage","violence","",false,false);
		textSlides.add(rage);
		Slide rape = new Slide("zrape","violence","",false,false);
		textSlides.add(rape);
		Slide riot = new Slide("zriot","violence","",false,false);
		textSlides.add(riot);
		Slide slaughter = new Slide("zslaughter","violence","",false,false);
		textSlides.add(slaughter);
		Slide strangle = new Slide("zstrangle","violence","",false,false);
		textSlides.add(strangle);
		Slide terrorist = new Slide("zterrorist","violence","",false,false);
		textSlides.add(terrorist);
		Slide trample = new Slide("ztrample","violence","",false,false);
		textSlides.add(trample);
		Slide vicious = new Slide("zvicious","violence","",false,false);
		textSlides.add(vicious);
		Slide war = new Slide("zwar","violence","",false,false);
		textSlides.add(war);
		Slide wreckage = new Slide("zwreckage","violence","",false,false);
		textSlides.add(wreckage);
		
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

		Slide finger1 = new Slide("zfinger", "benign", "", false, false);
		textSlides.add(finger1);

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
		sexList.add(time);
		positiveSlides.add(time);
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

		
		
		//populate text slides here
		/*Slide brut = new Slide ("brutality",null,"",false,false);CANNOT MAKE THE RACE ON ANY CLIDE NULL.  TEXT SLIDES RACE IS IT'S CATEGORY
		textSlides.add(brut);
		Slide disorder = new Slide ("disorder",null,"",false,false);CANNOT MAKE THE RACE ON ANY CLIDE NULL.  TEXT SLIDES RACE IS IT'S CATEGORY
		textSlides.add(disorder);
		Slide struggle = new Slide ("struggle",null,"",false,false);CANNOT MAKE THE RACE ON ANY CLIDE NULL.  TEXT SLIDES RACE IS IT'S CATEGORY
		textSlides.add(struggle);*/
		
		runnable.run();
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		checkTime();
		return true;
	}
	
	
	//cycles through all the images in the arraylist of filenames
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
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
		bundle.putParcelable("testtype", violenceTest);
		bundle.putParcelable("user", p);
		ArrayList<TestType> testList = b.getParcelableArrayList("testList");
		bundle.putParcelableArrayList("testList",testList);
		violenceTest.slides = slideHistory;
		ArrayList<Person> ppl = b.getParcelableArrayList("userList");
		bundle.putParcelableArrayList("userList",ppl);
		
		intent.putExtras(bundle);	
		
		startActivity(intent);
	}
	
}
