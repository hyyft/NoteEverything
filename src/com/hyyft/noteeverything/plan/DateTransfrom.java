package com.hyyft.noteeverything.plan;

import android.text.format.Time;

public class DateTransfrom {

	
	public long  timeToLong(String dateStr , String timeStr){
		Time time = new Time();
		String date[] = dateStr.split("-");
		String t[] = timeStr.split(":");
		time.set( 0 , 
				Integer.valueOf(t[1]),
				Integer.valueOf(t[0]), 
				Integer.valueOf(date[2]), 
				Integer.valueOf(date[1]), 
				Integer.valueOf(date[0]));
		return time.toMillis(true);
		
	}
}
