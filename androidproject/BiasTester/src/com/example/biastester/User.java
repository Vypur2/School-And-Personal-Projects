package com.example.biastester;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class User{
	Person person;
	ArrayList<TestType> completedTests;
	String currentTestType;

	User()
	{
		this.person = new Person();
		this.completedTests = new ArrayList<TestType>();
	}

}
