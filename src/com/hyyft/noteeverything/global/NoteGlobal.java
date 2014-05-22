package com.hyyft.noteeverything.global;

import java.util.LinkedList;
import java.util.List;

import com.hyyft.noteeverything.dao.DayPlanDao;
import com.hyyft.noteeverything.dao.DoWhatDao;
import com.hyyft.noteeverything.dao.PlanDbHelperContract;
import com.hyyft.noteeverything.dao.PlanDbHelperContract.DoWhatTableInfo;
import com.hyyft.noteeverything.dao.PlanDbHelperContract.PlanTableInfo;
import com.hyyft.noteeverything.modal.DayPlan;
import com.hyyft.noteeverything.modal.DoWhat;

import android.app.Application;
import android.content.ContentValues;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.util.Log;

public class NoteGlobal extends Application {
	public LinkedList<DayPlan> planList = new LinkedList<DayPlan>();
	public LinkedList<DoWhat> doList = new LinkedList<DoWhat>();
	public String dateString;
//	private int maxPlanOrder = 0;
//	private int maxDoItemOrder = 0;
	public boolean globalFirstRun = true;
	
	
	public void getPlan( String date  ){
		//Log.i("hyyft" ,date );
		DayPlanDao  dao= new DayPlanDao(getApplicationContext());
	    List<DayPlan>list =  dao.getAll(PlanTableInfo.PLAN_TABLE_NAME, date);
	    int i;
	    if(!planList.isEmpty())planList.removeAll(planList);
	    for(  i=list.size()-1 ; i >=0 ; i--){
	    	planList.add(list.get(i));
	    	//Log.i("hyyft" ,list.get(i).toString() );
	    }
	}

	public void AddAPlan(DayPlan dayPlan) {
		int j;
		if (dateString.equals(dayPlan.getDate())) {
			for (j = 0; j < planList.size()
					&& (planList.get(j).getPlanBeginTime() > dayPlan
							.getPlanBeginTime()); j++)
				;
			planList.add(j, dayPlan);
		}
		//Log.i("hyyft" ,dayPlan.toString() );
		DayPlanDao dao = new DayPlanDao(getApplicationContext());
		dao.add(dayPlan);

	}
    public void deletePlan(int index , String date){
		
		DayPlanDao  dao= new DayPlanDao(getApplicationContext());
		dao.delete( planList.get(index).getOrder() , date);
		planList.remove(index);
		
	}
    public void updatePlan(int index , String date , ContentValues values){
		
		DayPlanDao  dao= new DayPlanDao(getApplicationContext());
		dao.update(values, planList.get(index).getOrder(), date);
		getPlan(date);		
	}
    
    public int getMaxPlanOrder(String date){
		
		DayPlanDao  dao= new DayPlanDao(getApplicationContext());
		return dao.getMaxPlanOrder(date)+1;
	}
    
    
    
	/**
	 * 获取一个最靠近当前时间的未来任务
	 * @return
	 */
	public DayPlan getNearPlan(  ){
		long currentTime = System.currentTimeMillis();
		for ( int j= 0; j < planList.size() ; j++ ){
			if( currentTime <= planList.get(j).getPlanBeginTime() && planList.get(j).getIsFinish()==0 )
				return planList.get(j);
		}
	     return null;
	}
	
	
	public void getDoWhat( String date  ){
		DoWhatDao  dao= new DoWhatDao(getApplicationContext());
	    List<DoWhat>list =  dao.getAll(DoWhatTableInfo.TABLE_NAME, date);
	    int i;
	    if(!doList.isEmpty())doList.removeAll(doList);
	    for(  i=0; i < list.size() ; i++){
	    	doList.add(list.get(i));
	    }
	}
	public void AddDoItem( DoWhat doWhat ){
		int j;
		for ( j= 0; j < doList.size()
				&& (doList.get(j).getBeginTime() < doWhat.getBeginTime()); j++);
		doList.add( j, doWhat );
		DoWhatDao  dao= new DoWhatDao(getApplicationContext());
		dao.add(doWhat);
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
	public int getMaxDoItemOrder(String date){
		
		DoWhatDao  dao= new DoWhatDao(getApplicationContext());
		return dao.getMaxDoItemOrder(date)+1;
	}
	
	
	
}
