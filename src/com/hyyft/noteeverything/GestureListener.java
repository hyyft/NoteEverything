package com.hyyft.noteeverything;


import android.content.Context;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;


public class GestureListener extends SimpleOnGestureListener {
	

	private static final int  SWIPE_MIN_DISTANCE = 150;
	private int maxTabIndex = 3;
	private Context context;

	public GestureListener(Context context , int index ){
		this.context = context;

	}
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			
			try {
				
				//Log.i("GestureListener" , "touch");
				
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
					Log.i("test", "right");
					if (MainActivity.index == maxTabIndex ) {
						MainActivity.index = maxTabIndex;
					} else {
						MainActivity.index++;
					}
					MainActivity.tabHost.setCurrentTab(MainActivity.index);
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE ) {
					Log.i("test", "left");
					if (MainActivity.index == 0) {
						MainActivity.index = 0;
					} else {
						MainActivity.index--;
					}
					MainActivity.tabHost.setCurrentTab(MainActivity.index);
				}
			} catch (Exception e) {
			}
			return false;
		}
	}

	
	
