package com.example.biastester;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable{
	public String Name;
	public int age;
	public String Race;
	public String gender;
	public ArrayList<TestType> tests;
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(Name);
		dest.writeInt(age);
		dest.writeString(Race);
		dest.writeString(gender);	
		dest.writeTypedList(tests);
	}
	
	private Person(Parcel in)
	{
		this();
		Name = in.readString();
		age = in.readInt();
		Race = in.readString();
		gender = in.readString();
		in.readTypedList(tests, TestType.CREATOR);
	}
	
	public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
		 public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

		@Override
		public Person[] newArray(int size) {
			return new Person[size];
		}
	 };
	
	public Person()
	{
		Name = "";
		age = 22;
		Race = "";
		gender = "";
		tests = new ArrayList<TestType>();
	}
	
}
