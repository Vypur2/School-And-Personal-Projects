package com.example.biastester;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class TestType implements Parcelable{
	List<Slide> slides;
	String tag;
	String Results;
	int date;
	String dateAndTime;
	
	
	public TestType(Parcel in) {
		this();
		in.readTypedList(slides,Slide.CREATOR);
		tag = in.readString();
		Results = in.readString();
		date = in.readInt();
		dateAndTime = in.readString();
	}
	public TestType() {
		slides = new ArrayList<Slide>();
		tag = "";
		Results = "";
		date = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		dateAndTime = sdf.format(new Date()); 
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(slides);
		dest.writeString(tag);
		dest.writeString(Results);
		dest.writeInt(date);
		dest.writeString(dateAndTime);
	}
	
	public static final Parcelable.Creator<TestType> CREATOR = new Parcelable.Creator<TestType>() {
		 public TestType createFromParcel(Parcel in) {
            return new TestType(in);
        }

		@Override
		public TestType[] newArray(int size) {
			return new TestType[size];
		}
	 };
}
