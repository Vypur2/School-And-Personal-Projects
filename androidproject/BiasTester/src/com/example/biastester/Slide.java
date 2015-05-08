package com.example.biastester;

import android.os.Parcel;
import android.os.Parcelable;

public class Slide implements Parcelable{
	String Race;
	String filename;
	String Gender;
	Boolean isPhoto;
	Boolean wasTapped;
	Slide prev;
	float tapTime;


	public Slide(String fname, String r,String g,Boolean photo,Boolean tapped)
	{
		this.Race = r;
		this.filename = fname;
		this.Gender = g;
		this.isPhoto = photo;
		this.wasTapped = tapped;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	 public static final Parcelable.Creator<Slide> CREATOR = new Parcelable.Creator<Slide>() {
		 public Slide createFromParcel(Parcel in) {
             return new Slide(in);
         }

		@Override
		public Slide[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Slide[size];
		}
	 };
	 
	 private Slide(Parcel in)
	 {
		 Race = in.readString();
		 filename = in.readString();
		 Gender = in.readString();
		 isPhoto = in.readByte() != 0;
		 wasTapped = in.readByte() != 0;
		 tapTime = in.readFloat();
	 }

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.Race);
		dest.writeString(this.filename);
		dest.writeString(this.Gender);
		dest.writeByte((byte) (this.isPhoto ? 1 : 0));
		dest.writeByte((byte) (this.wasTapped ? 1 : 0));
		dest.writeFloat(this.tapTime);
	}
	
}
