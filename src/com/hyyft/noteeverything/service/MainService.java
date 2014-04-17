package com.hyyft.noteeverything.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.hyyft.noteeverything.dao.DayPlanDao;
import com.hyyft.noteeverything.dao.PlanDbHelperContract.PlanTableInfo;
import com.hyyft.noteeverything.global.NoteGlobal;
import com.hyyft.noteeverything.modal.DayPlan;
import com.hyyft.noteeverything.myconst.PrefConst;
import com.hyyft.noteeverything.plan.DialogActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Binder;
import android.os.IBinder;
import android.text.format.Time;

/**
 * ����ע��Alram��service
 * @author hyyft
 *
 */
public class MainService extends Service {

	private NoteGlobal noteGlobal;
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
		initGlobal();
		noteGlobal = (NoteGlobal)getApplication();
		updateAlarm();
	}
	

	/**
	 * ��������
	 */
	public void updateAlarm(){		
		DayPlan dayPlan = noteGlobal.getNearPlan();
		if( dayPlan != null ){
			regeditAlarm(dayPlan.getPlanBeginTime() , dayPlan.getTitle());
		}
	}

	/**
	 * ע��Alarm
	 * @param planTime �ƻ�����ʱ��
	 */
	public void regeditAlarm(long planTime , String message) {
		PendingIntent sender = PendingIntent.getActivity(this, 0, new Intent(
				this, DialogActivity.class).putExtra("TEXT", message), 0);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(planTime);
		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		manager.cancel(sender);
		manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
	}
	
	
	private void initGlobal() {
		// TODO Auto-generated method stub
		
		initDatabase();
		initTag();
		stopSelf();
		
	}
	
	/**
	 * ��ʼ������
	 */
	private void initTag(){
		SharedPreferences sharedPreferences = this.getSharedPreferences(PrefConst.NAME, MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		if( sharedPreferences.getInt(PrefConst.FIRSTINIT, 0) == 0 ){
			editor.putInt(PrefConst.FIRSTINIT, 1);
			editor.putString(PrefConst.DATELY_TAG, "�ճ�");
			editor.putString(PrefConst.STUDY_TAG, "ѧϰ");
			editor.putString(PrefConst.SPORT_TAG, "�˶�");
			editor.putString(PrefConst.FUNNY_TAG, "����");
			editor.putInt(PrefConst.BIGTAG_COUNT, 4);
			
			editor.putString(PrefConst.DATELY_TAG+"-"+"1", "�Է�");
			editor.putString(PrefConst.DATELY_TAG+"-"+"2", "˯��");
			
			editor.putString(PrefConst.STUDY_TAG+"-"+"1", "רҵѧϰ");
			editor.putString(PrefConst.STUDY_TAG+"-"+"2", "������");
			
			editor.putString(PrefConst.SPORT_TAG+"-"+"1", "����");
			editor.putString(PrefConst.SPORT_TAG+"-"+"2", "����");
			editor.putString(PrefConst.SPORT_TAG+"-"+"3", "�ܲ�");
			
			editor.putString(PrefConst.FUNNY_TAG+"-"+"1", "����Ӱ");
			editor.putString(PrefConst.FUNNY_TAG+"-"+"2", "������");
			
			
			editor.putInt(PrefConst.DATELY_COUNT, 2);
			editor.putInt(PrefConst.STUDY_COUNT, 2);
			editor.putInt(PrefConst.SPORT_COUNT, 3);
			editor.putInt(PrefConst.FUNNY_COUNT, 2);
			
			
			editor.commit();
			
		}
		else return;
		
	}
	
	private void initDatabase(){
		NoteGlobal noteGlobal = (NoteGlobal)getApplication();
		//ֻ��global�����һ������ʱ��ִ�����²���
		if( !noteGlobal.globalFirstRun )return;
		noteGlobal.globalFirstRun = false;
		
		DayPlanDao dbDao = new DayPlanDao(this);
		Time time = new Time();
		time.setToNow();
		
		List<DayPlan> arraylist = new ArrayList<DayPlan>();
		arraylist = dbDao.getAll(PlanTableInfo.PLAN_TABLE_NAME,
				time.year+"-"+(time.month+1)+"-"+time.monthDay );
		
		
		for( int i=0 ; i< arraylist.size() ; i++ ){	
			if( noteGlobal.maxPlanOrder<arraylist.get(i).getOrder() )
				noteGlobal.maxPlanOrder = arraylist.get(i).getOrder();
			if(noteGlobal.planList.isEmpty()){
				noteGlobal.planList.add(arraylist.get(i));
			}
			else{
				int j;
				for( j=0 ; 
					j<noteGlobal.planList.size() && ( noteGlobal.planList.get(j).getPlanBeginTime() < arraylist.get(i).getPlanBeginTime() );
					j++ );
				noteGlobal.planList.add( j , arraylist.get(i));
				
			}
			//Log.i("yuan", noteGlobal.planList.get(i).toString() );
		}
		//Log.i("yuan", "Init OK");
		
		
	}

}
