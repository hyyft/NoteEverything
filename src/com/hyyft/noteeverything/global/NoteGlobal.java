package com.hyyft.noteeverything.global;

import java.util.Calendar;
import java.util.LinkedList;

import com.hyyft.noteeverything.modal.DayPlan;

import android.app.Application;
import android.util.Log;

public class NoteGlobal extends Application {
	public LinkedList<DayPlan> planList = new LinkedList<DayPlan>();
	public int maxPlanOrder = 0;
	public boolean globalFirstRun = true;

	public void AddAPlan(DayPlan dayPlan) {
		int j;
		for ( j= 0; j < planList.size()
				&& (planList.get(j).getPlanBeginTime() < dayPlan.getPlanBeginTime()); j++);
		planList.add( j, dayPlan );
		maxPlanOrder++;

	}
	/**
	 * 获取一个最靠近当前时间的未来任务
	 * @return
	 */
	public DayPlan getNearPlan(  ){
		long currentTime = System.currentTimeMillis();
		for ( int j= 0; j < planList.size() ; j++ ){
			if( currentTime <= planList.get(j).getPlanBeginTime() )
				return planList.get(j);
		}
	     return null;
	}
}
