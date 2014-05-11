package com.hyyft.noteeverything.global;

import java.util.LinkedList;
import java.util.List;

import com.hyyft.noteeverything.dao.DayPlanDao;
import com.hyyft.noteeverything.dao.DoWhatDao;
import com.hyyft.noteeverything.dao.PlanDbHelperContract.DoWhatTableInfo;
import com.hyyft.noteeverything.modal.DayPlan;
import com.hyyft.noteeverything.modal.DoWhat;

import android.app.Application;
import android.content.ContentValues;

public class NoteGlobal extends Application {
	public LinkedList<DayPlan> planList = new LinkedList<DayPlan>();
	public LinkedList<DoWhat> doList = new LinkedList<DoWhat>();
	public int maxPlanOrder = 0;
	public int maxDoItemOrder = 0;
	public boolean globalFirstRun = true;

	public void AddAPlan(DayPlan dayPlan) {
		int j;
		for ( j= 0; j < planList.size()
				&& (planList.get(j).getPlanBeginTime() < dayPlan.getPlanBeginTime()); j++);
		planList.add( j, dayPlan );
		maxPlanOrder++;

	}
    public void deletePlan(int index , String date){
		
		DayPlanDao  dao= new DayPlanDao(getApplicationContext());
		dao.delete( planList.get(index).getOrder() , date);
		planList.remove(index);
		
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
	
	
	public void getDoWhat( String date  ){
		DoWhatDao  dao= new DoWhatDao(getApplicationContext());
	    List<DoWhat>list =  dao.getAll(DoWhatTableInfo.TABLE_NAME, date);
	    int i;
	    if(!doList.isEmpty())doList.removeAll(doList);
	    for(  i=0 ; i < list.size() ; i++){
	    	doList.add(list.get(i));
	    }
	    maxDoItemOrder = i;
	}
	public void AddDoItem( DoWhat doWhat ){
		int j;
		for ( j= 0; j < doList.size()
				&& (doList.get(j).getBeginTime() < doWhat.getBeginTime()); j++);
		doList.add( j, doWhat );
		DoWhatDao  dao= new DoWhatDao(getApplicationContext());
		doWhat.setOrder(maxDoItemOrder);
		dao.add(doWhat);
		maxDoItemOrder++;
	}
	public void DelDoItem( int index , String date ){
		DoWhatDao  dao= new DoWhatDao(getApplicationContext());
		dao.delete( doList.get(index).getOrder() , date);
		doList.remove(index);
	}
	public void UpdateDoItem(ContentValues values , int index , String date){
		DoWhatDao  dao= new DoWhatDao(getApplicationContext());
		dao.update( values ,doList.get(index).getOrder() , date);
		getDoWhat( date  );
	}
}
