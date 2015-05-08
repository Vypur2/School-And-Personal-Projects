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

public class MultiTest extends Activity {

	public long deltaT = 0;
	ArrayList<Slide> slideList = new ArrayList<Slide>();
	ArrayList<Slide> sexList = new ArrayList<Slide>();
	ArrayList<Slide> violentList = new ArrayList<Slide>();
	ArrayList<Slide> positiveSlides = new ArrayList<Slide>();
	ArrayList<Slide> slideHistory = new ArrayList<Slide>();
	ArrayList<Slide> textSlides = new ArrayList<Slide>();
	Slide currentSlide;
	Slide previous;

	TestType multiTest = new TestType();
	
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
		setContentView(R.layout.activity_multi_test);
		
		multiTest.tag = "multi";
		multiTest.date = 11111;
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
		
		Slide amenable = new Slide("zamenable","positive","",false, false);
		positiveSlides.add(amenable);
		Slide articulate = new Slide("zarticulate","positive","",false, false);
		positiveSlides.add(articulate);
		Slide award = new Slide("zaward","positive","",false, false);
		positiveSlides.add(award);
		Slide brainy = new Slide("zbrainy","positive","",false, false);
		positiveSlides.add(brainy);
		Slide bravo = new Slide("zbravo","positive","",false, false);
		positiveSlides.add(bravo);
		Slide brilliant = new Slide("zbrilliant","positive","",false, false);
		positiveSlides.add(brilliant);
		Slide commendation = new Slide("zcommendation","positive","",false, false);
		positiveSlides.add(commendation);
		Slide couragous = new Slide("zcouragous","positive","",false, false);
		positiveSlides.add(couragous);
		Slide cunning = new Slide("zcunning","positive","",false, false);
		positiveSlides.add(cunning);
		Slide dependable = new Slide("zdependable","positive","",false, false);
		positiveSlides.add(dependable);
		Slide determined = new Slide("zdetermined","positive","",false, false);
		positiveSlides.add(determined);
		Slide elated = new Slide("zelated","positive","",false, false);
		positiveSlides.add(elated);
		Slide energetic = new Slide("zenergetic","positive","",false, false);
		positiveSlides.add(energetic);
		Slide fantastic = new Slide("fantastic","positive","",false, false);
		positiveSlides.add(fantastic);
		Slide freedom = new Slide("zfreedom","positive","",false, false);
		positiveSlides.add(freedom);
		Slide fun = new Slide("zfun","positive","",false, false);
		positiveSlides.add(fun);
		Slide glowing = new Slide("zglowing","positive","",false, false);
		positiveSlides.add(glowing);
		Slide graduate = new Slide("zgraduate","positive","",false, false);
		positiveSlides.add(graduate);
		Slide great = new Slide("zgreat","positive","",false, false);
		positiveSlides.add(great);
		Slide helpful = new Slide("zhelpful","positive","",false, false);
		positiveSlides.add(helpful);
		Slide hilarious = new Slide("zhilarious","positive","",false, false);
		positiveSlides.add(hilarious);
		Slide hope = new Slide("zhope","positive","",false, false);
		positiveSlides.add(hope);
		Slide ingenious = new Slide("zingenious","positive","",false, false);
		positiveSlides.add(ingenious);
		Slide joking = new Slide("zjoking","positive","",false, false);
		positiveSlides.add(joking);
		Slide jovial = new Slide("zjovial","positive","",false, false);
		positiveSlides.add(jovial);
		Slide likable = new Slide("zlikable","positive","",false, false);
		positiveSlides.add(likable);
		Slide lovable = new Slide("zlovable","positive","",false, false);
		positiveSlides.add(lovable);
		Slide love = new Slide("zlove","positive","",false, false);
		positiveSlides.add(love);
		Slide marvelous = new Slide("zmarvelous","positive","",false, false);
		positiveSlides.add(marvelous);
		Slide merit = new Slide("zmerit","positive","",false, false);
		positiveSlides.add(merit);
		Slide merry = new Slide("zmerry","positive","",false, false);
		positiveSlides.add(merry);
		Slide neat = new Slide("zneat","positive","",false, false);
		positiveSlides.add(neat);
		Slide nice = new Slide("znice","positive","",false, false);
		positiveSlides.add(nice);
		Slide noble = new Slide("znoble","positive","",false, false);
		positiveSlides.add(noble);
		Slide optimism = new Slide("zoptimism","positive","",false, false);
		positiveSlides.add(optimism);
		Slide outstanding = new Slide("zoutstanding","positive","",false, false);
		positiveSlides.add(outstanding);
		Slide peaceful = new Slide("zpeaceful","positive","",false, false);
		positiveSlides.add(peaceful);
		Slide perfect = new Slide("zperfect","positive","",false, false);
		positiveSlides.add(perfect);
		Slide pleasing = new Slide("zperfect","positive","",false, false);
		positiveSlides.add(pleasing);
		Slide quality = new Slide("zquality","positive","",false, false);
		positiveSlides.add(quality);
		Slide respect = new Slide("zrespect","positive","",false, false);
		positiveSlides.add(respect);
		Slide righteous = new Slide("zrighteous","positive","",false, false);
		positiveSlides.add(righteous);
		Slide smiles = new Slide("zsmiles","positive","",false, false);
		positiveSlides.add(smiles);
		Slide zsuper = new Slide("zsuper","positive","",false, false);
		positiveSlides.add(zsuper);
		Slide sweet = new Slide("zsweet","positive","",false, false);
		positiveSlides.add(sweet);
		Slide terrific = new Slide("zterrific","positive","",false, false);
		positiveSlides.add(terrific);
		Slide thrilling = new Slide("zthrilling","positive","",false, false);
		positiveSlides.add(thrilling);
		Slide trustworthy = new Slide("ztrustworthy","positive","",false, false);
		positiveSlides.add(trustworthy);
		Slide unique = new Slide("zunique","positive","",false, false);
		positiveSlides.add(unique);
		Slide uplifting = new Slide("zuplifting","positive","",false, false);
		positiveSlides.add(uplifting);
		Slide values = new Slide("zvalues","positive","",false, false);
		positiveSlides.add(values);
		Slide victory = new Slide("zvictory","positive","",false, false);
		positiveSlides.add(victory);
		Slide welcoming = new Slide("zwelcoming","positive","",false, false);
		positiveSlides.add(welcoming);
		Slide winner = new Slide("zwinner","positive","",false, false);
		positiveSlides.add(winner);
		Slide worthy = new Slide("zworthy","positive","",false, false);
		positiveSlides.add(worthy);
		
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
		sexList.add(and);
		positiveSlides.add(and);
		Slide apple = new Slide("zapple", "benign", "", false, false);
		textSlides.add(apple);
		sexList.add(apple);
		positiveSlides.add(apple);
		Slide blender = new Slide("zblender", "benign", "", false, false);
		textSlides.add(blender);
		sexList.add(blender);
		positiveSlides.add(blender);
		Slide bottle = new Slide("zbottle", "benign", "", false, false);
		textSlides.add(bottle);
		sexList.add(bottle);
		positiveSlides.add(bottle);
		Slide cake = new Slide("zcake", "benign", "", false, false);
		textSlides.add(cake);
		sexList.add(cake);
		positiveSlides.add(cake);
		Slide clip = new Slide("zclip", "benign", "", false, false);
		textSlides.add(clip);
		sexList.add(clip);
		positiveSlides.add(clip);
		Slide cows = new Slide("zcows", "benign", "", false, false);
		textSlides.add(cows);
		sexList.add(cows);
		positiveSlides.add(cows);
		Slide crayon = new Slide("zcrayon", "benign", "", false, false);
		textSlides.add(crayon);
		sexList.add(crayon);
		positiveSlides.add(crayon);
		Slide dental = new Slide("zdental", "benign", "", false, false);
		textSlides.add(dental);
		sexList.add(dental);
		positiveSlides.add(dental);
		Slide door = new Slide("zdoor", "benign", "", false, false);
		textSlides.add(door);
		sexList.add(door);
		positiveSlides.add(door);
		Slide each = new Slide("zeach", "benign", "", false, false);
		textSlides.add(each);
		sexList.add(each);
		positiveSlides.add(each);
		Slide eagle = new Slide("zeagle", "benign", "", false, false);
		textSlides.add(eagle);
		sexList.add(eagle);
		positiveSlides.add(eagle);
		Slide eclipse = new Slide("zeclipse", "benign", "", false, false);
		textSlides.add(eclipse);
		sexList.add(eclipse);
		positiveSlides.add(eclipse);
		Slide find = new Slide("zfind", "benign", "", false, false);
		textSlides.add(find);
		sexList.add(find);
		positiveSlides.add(find);
		Slide finger1 = new Slide("zfinger", "benign", "", false, false);
		textSlides.add(finger1);
		sexList.add(finger1);
		positiveSlides.add(finger1);
		Slide forest = new Slide("zforest", "benign", "", false, false);
		textSlides.add(forest);
		sexList.add(forest);
		positiveSlides.add(forest);
		Slide found = new Slide("zfound", "benign", "", false, false);
		textSlides.add(found);
		sexList.add(found);
		positiveSlides.add(found);
		Slide ground = new Slide("zground", "benign", "", false, false);
		textSlides.add(ground);
		sexList.add(ground);
		positiveSlides.add(ground);
		Slide gum = new Slide("zgum", "benign", "", false, false);
		textSlides.add(gum);
		sexList.add(gum);
		positiveSlides.add(gum);
		Slide hand = new Slide("zhand", "benign", "", false, false);
		textSlides.add(hand);
		sexList.add(hand);
		positiveSlides.add(hand);
		Slide hotel = new Slide("zhotel", "benign", "", false, false);
		textSlides.add(hotel);
		sexList.add(hotel);
		positiveSlides.add(hotel);
		Slide igloo = new Slide("zigloo", "benign", "", false, false);
		textSlides.add(igloo);
		sexList.add(igloo);
		positiveSlides.add(igloo);
		Slide india = new Slide("zindia", "benign", "", false, false);
		textSlides.add(india);
		sexList.add(india);
		positiveSlides.add(india);
		Slide jester = new Slide("zjester", "benign", "", false, false);
		textSlides.add(jester);
		sexList.add(jester);
		positiveSlides.add(jester);
		Slide jump = new Slide("zjump", "benign", "", false, false);
		textSlides.add(jump);
		sexList.add(jump);
		positiveSlides.add(jump);
		Slide kale = new Slide("zkale", "benign", "", false, false);
		textSlides.add(kale);
		sexList.add(kale);
		positiveSlides.add(kale);
		Slide kite = new Slide("zkite", "benign", "", false, false);
		textSlides.add(kite);
		sexList.add(kite);
		positiveSlides.add(kite);
		Slide lemon = new Slide("zlemon", "benign", "", false, false);
		textSlides.add(lemon);
		sexList.add(lemon);
		positiveSlides.add(lemon);
		Slide loud = new Slide("zloud", "benign", "", false, false);
		textSlides.add(loud);
		sexList.add(loud);
		positiveSlides.add(loud);
		Slide mandate = new Slide("zmandate", "benign", "", false, false);
		textSlides.add(mandate);
		sexList.add(mandate);
		positiveSlides.add(mandate);
		Slide march = new Slide("zmarch", "benign", "", false, false);
		textSlides.add(march);
		sexList.add(march);
		positiveSlides.add(march);
		Slide needful = new Slide("zneedful", "benign", "", false, false);
		textSlides.add(needful);
		sexList.add(needful);
		positiveSlides.add(needful);
		Slide news = new Slide("znews", "benign", "", false, false);
		textSlides.add(news);
		sexList.add(news);
		positiveSlides.add(news);
		Slide open = new Slide("zopen", "benign", "", false, false);
		textSlides.add(open);
		sexList.add(open);
		positiveSlides.add(open);
		Slide opiate = new Slide("zopiate", "benign", "", false, false);
		textSlides.add(opiate);
		sexList.add(opiate);
		positiveSlides.add(opiate);
		Slide pants = new Slide("zpants", "benign", "", false, false);
		textSlides.add(pants);
		sexList.add(pants);
		positiveSlides.add(pants);
		Slide pencil = new Slide("zpencil", "benign", "", false, false);
		textSlides.add(pencil);
		sexList.add(pencil);
		positiveSlides.add(pencil);
		Slide quickly = new Slide("zquickly", "benign", "", false, false);
		textSlides.add(quickly);
		sexList.add(quickly);
		positiveSlides.add(quickly);
		Slide qwerty = new Slide("zqwerty", "benign", "", false, false);
		textSlides.add(qwerty);
		sexList.add(qwerty);
		positiveSlides.add(qwerty);
		Slide radio = new Slide("zradio", "benign", "", false, false);
		textSlides.add(radio);
		sexList.add(radio);
		positiveSlides.add(radio);
		Slide red = new Slide("zred", "benign", "", false, false);
		textSlides.add(red);
		sexList.add(red);
		positiveSlides.add(red);
		Slide rotunda = new Slide("zrotunda", "benign", "", false, false);
		textSlides.add(rotunda);
		sexList.add(rotunda);
		positiveSlides.add(rotunda);
		Slide scales = new Slide("zscales", "benign", "", false, false);
		textSlides.add(scales);
		sexList.add(scales);
		positiveSlides.add(scales);
		Slide sound = new Slide("zsound", "benign", "", false, false);
		textSlides.add(sound);
		sexList.add(sound);
		positiveSlides.add(sound);
		Slide throne = new Slide("zthrone", "benign", "", false, false);
		textSlides.add(throne);
		sexList.add(throne);
		positiveSlides.add(throne);
		Slide time = new Slide("ztime", "benign", "", false, false);
		textSlides.add(time);
		sexList.add(time);
		positiveSlides.add(time);
		Slide treble = new Slide("ztreble", "benign", "", false, false);
		textSlides.add(treble);
		sexList.add(treble);
		positiveSlides.add(treble);
		Slide umbrella = new Slide("zumbrella", "benign", "", false, false);
		textSlides.add(umbrella);
		sexList.add(umbrella);
		positiveSlides.add(umbrella);
		Slide upstairs = new Slide("zupstairs", "benign", "", false, false);
		textSlides.add(upstairs);
		sexList.add(upstairs);
		positiveSlides.add(upstairs);
		Slide voices = new Slide("zvoices", "benign", "", false, false);
		textSlides.add(voices);
		sexList.add(voices);
		positiveSlides.add(voices);
		Slide vowel = new Slide("zvowel", "benign", "", false, false);
		textSlides.add(vowel);
		sexList.add(vowel);
		positiveSlides.add(vowel);
		Slide words = new Slide("zwords", "benign", "", false, false);
		textSlides.add(words);
		sexList.add(words);
		positiveSlides.add(words);
		Slide world = new Slide("zworld", "benign", "", false, false);
		textSlides.add(world);
		sexList.add(world);
		positiveSlides.add(world);
		Slide yellow = new Slide("zyellow", "benign", "", false, false);
		textSlides.add(yellow);
		sexList.add(yellow);
		positiveSlides.add(yellow);
		
		runnable.run();
		
	}
	
	@Override
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
			if (slideCounter >= 180) //number of slides in 2 minutes
			{
				loadResults(mImageView);
			}
			else if (slideCounter >= 30 && slideCounter < 60)
			{
				if (currentSlide.prev != null)
				{
					if (!currentSlide.prev.wasTapped)
					{
						currentSlide.prev.tapTime = 1000f;
					}
				}
				
				int imageRes;
				Random randomGenerator = new Random();
				int randomInt;
				
				if (slideCounter%2 != 1){ //picture
					randomInt = randomGenerator.nextInt(slideList.size());	
					currentSlide = slideList.get(randomInt);
					currentSlide.wasTapped = false;
					currentSlide.prev = previous;
					imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
					mImageView.setImageResource(imageRes);
				}
				else //text
				{
					randomInt = randomGenerator.nextInt(sexList.size());				
					currentSlide = sexList.get(randomInt);
					sexList.remove(randomInt);
					currentSlide.wasTapped = false;
					currentSlide.prev = previous;
					imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
					mImageView.setImageResource(imageRes);
				}
				previous = currentSlide;
				slideHistory.add(currentSlide);
				slideCounter++;
				handler.postDelayed(runnable, 1000);
				return;
				//sexual test
			}
			else if (slideCounter >= 60 && slideCounter < 90)
			{
				if (currentSlide.prev != null)
				{
					if (!currentSlide.prev.wasTapped)
					{
						currentSlide.prev.tapTime = 1000f;
					}
				}
				
				//positive test
				int imageRes;
				Random randomGenerator = new Random();
				int randomInt;
				
				if (slideCounter%2 != 1){ //picture
					randomInt = randomGenerator.nextInt(slideList.size());	
					currentSlide = slideList.get(randomInt);
					currentSlide.wasTapped = false;
					currentSlide.prev = previous;
					imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
					mImageView.setImageResource(imageRes);
				}
				else //text
				{
					randomInt = randomGenerator.nextInt(positiveSlides.size());				
					currentSlide = positiveSlides.get(randomInt);
					positiveSlides.remove(randomInt);
					currentSlide.wasTapped = false;
					currentSlide.prev = previous;
					imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
					mImageView.setImageResource(imageRes);
				}
				previous = currentSlide;
				slideHistory.add(currentSlide);
				slideCounter++;
				handler.postDelayed(runnable, 1000);
				return;
			}
			else if (slideCounter >= 90 && slideCounter < 120)
			{
				if (currentSlide.prev != null)
				{
					if (!currentSlide.prev.wasTapped)
					{
						currentSlide.prev.tapTime = 1000f;
					}
				}
				
				//violence test
				int imageRes;
				Random randomGenerator = new Random();
				int randomInt;
				
				if (slideCounter%2 != 1){
					randomInt = randomGenerator.nextInt(slideList.size());	
					
					currentSlide = slideList.get(randomInt);
					currentSlide.wasTapped = false;
					currentSlide.prev = previous;
					imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
					mImageView.setImageResource(imageRes);
				}
				else
				{
					randomInt = randomGenerator.nextInt(textSlides.size());
					
					currentSlide = textSlides.get(randomInt);
					textSlides.remove(randomInt);
					currentSlide.wasTapped = false;
					currentSlide.prev = previous;
					imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
					mImageView.setImageResource(imageRes);
				}
				previous = currentSlide;
				slideHistory.add(currentSlide);
				slideCounter++;
				handler.postDelayed(runnable, 1000);
				return;
			}
			else if (slideCounter >= 120 && slideCounter < 150)
			{
				if (currentSlide.prev != null)
				{
					if (!currentSlide.prev.wasTapped)
					{
						currentSlide.prev.tapTime = 1000f;
					}
				}
				
				//sexual test
				int imageRes;
				Random randomGenerator = new Random();
				int randomInt;
				
				if (slideCounter%2 != 1){ //picture
					randomInt = randomGenerator.nextInt(slideList.size());	
					currentSlide = slideList.get(randomInt);
					currentSlide.wasTapped = false;
					currentSlide.prev = previous;
					imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
					mImageView.setImageResource(imageRes);
				}
				else //text
				{
					randomInt = randomGenerator.nextInt(sexList.size());				
					currentSlide = sexList.get(randomInt);
					sexList.remove(randomInt);
					currentSlide.wasTapped = false;
					currentSlide.prev = previous;
					imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
					mImageView.setImageResource(imageRes);
				}
				previous = currentSlide;
				slideHistory.add(currentSlide);
				slideCounter++;
				handler.postDelayed(runnable, 1000);
				return;
			}
			else if (slideCounter >= 150 && slideCounter < 180)
			{
				if (currentSlide.prev != null)
				{
					if (!currentSlide.prev.wasTapped)
					{
						currentSlide.prev.tapTime = 1000f;
					}
				}
				//positive test
				int imageRes;
				Random randomGenerator = new Random();
				int randomInt;
				
				if (slideCounter%2 != 1){ //picture
					randomInt = randomGenerator.nextInt(slideList.size());	
					currentSlide = slideList.get(randomInt);
					currentSlide.wasTapped = false;
					currentSlide.prev = previous;
					imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
					mImageView.setImageResource(imageRes);
				}
				else //text
				{
					randomInt = randomGenerator.nextInt(positiveSlides.size());				
					currentSlide = positiveSlides.get(randomInt);
					positiveSlides.remove(randomInt);
					currentSlide.wasTapped = false;
					currentSlide.prev = previous;
					imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
					mImageView.setImageResource(imageRes);
				}
				previous = currentSlide;
				slideHistory.add(currentSlide);
				slideCounter++;
				handler.postDelayed(runnable, 1000);
				return;
			}
			else if (slideCounter > 0 && slideCounter < 30)
			{
				if (currentSlide.prev != null)
				{
					if (!currentSlide.prev.wasTapped)
					{
						currentSlide.prev.tapTime = 1000f;
					}
				}
				
				//violence test
				int imageRes;
				Random randomGenerator = new Random();
				int randomInt;
				
				if (slideCounter%2 != 1){
					randomInt = randomGenerator.nextInt(slideList.size());	
					
					currentSlide = slideList.get(randomInt);
					currentSlide.wasTapped = false;
					currentSlide.prev = previous;
					imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
					mImageView.setImageResource(imageRes);
				}
				else
				{
					randomInt = randomGenerator.nextInt(textSlides.size());
					
					currentSlide = textSlides.get(randomInt);
					textSlides.remove(randomInt);
					currentSlide.wasTapped = false;
					currentSlide.prev = previous;
					imageRes = getResources().getIdentifier(currentSlide.filename, "drawable", getPackageName());
					mImageView.setImageResource(imageRes);
				}
				previous = currentSlide;
				slideHistory.add(currentSlide);
				slideCounter++;
				handler.postDelayed(runnable, 1000);
				return;
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
				
				int imageRes;
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
				slideHistory.add(currentSlide);
				slideCounter++;
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
		ArrayList<Person> ppl = b.getParcelableArrayList("userList");
				
		Intent intent = new Intent(this, Results.class);
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("history",slideHistory);
		multiTest.slides = slideHistory;
		bundle.putParcelable("testtype", multiTest);
		bundle.putParcelable("user",p);
		ArrayList<TestType> testList = b.getParcelableArrayList("testList");
		bundle.putParcelableArrayList("testList",testList);
		bundle.putParcelableArrayList("userList",ppl);
		
		intent.putExtras(bundle);

		
		
		
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.multi_test, menu);
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
