package com.hyyft.noteeverything.fragment;

import com.hyyft.noteeverything.MainActivity;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.TabHost;


public class GestureListener extends SimpleOnGestureListener {
	

	private static final int  SWIPE_MIN_DISTANCE = 150;
	private int maxTabIndex = 3;
	private Context context;
	private int index;
	public GestureListener(Context context , int index ){
		this.context = context;
		this.index = index;
	}
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			
			try {
				
				Log.i("GestureListener" , "touch");
				
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
					Log.i("test", "right");
					if (index == maxTabIndex ) {
						index = maxTabIndex;
					} else {
						index++;
					}
					MainActivity.tabHost.setCurrentTab(index);
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE ) {
					Log.i("test", "left");
					if (index == 0) {
						index = 0;
					} else {
						index--;
					}
					MainActivity.tabHost.setCurrentTab(index);
				}
			} catch (Exception e) {
			}
			return false;
		}
	}

	
	
