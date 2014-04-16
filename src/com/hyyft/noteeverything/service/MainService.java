package com.hyyft.noteeverything.service;

import java.util.Calendar;

import com.hyyft.noteeverything.plan.DialogActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

/**
 * 用于注册Alram的service
 * @author hyyft
 *
 */
public class MainService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new MainServiceBinder();
	}
	public class MainServiceBinder extends Binder{
		public MainService getService() {
			
			return MainService.this;
		}
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Intent intent = new Intent(this, InitService.class);
		startService(intent);
		
	}
	



	/**
	 * 注册Alarm
	 * @param planTime 计划响铃时间
	 */
	public void regeditAlarm(long planTime) {
		PendingIntent sender = PendingIntent.getActivity(this, 0, new Intent(
				this, DialogActivity.class), 0);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(planTime);
		//calendar.add(Calendar.SECOND, 10);
		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		manager.cancel(sender);
		manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
	}
	
	public void Log(){
		Toast.makeText(this, "****************************", Toast.LENGTH_LONG).show();
	}
	
	

}
