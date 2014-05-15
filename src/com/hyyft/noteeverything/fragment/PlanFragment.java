package com.hyyft.noteeverything.fragment;

import java.util.ArrayList;
import java.util.List;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.adapter.DayPlanAdapter;
import com.hyyft.noteeverything.adapter.DayPlanAdapter.DayPlanAdapterCallBack;
import com.hyyft.noteeverything.dao.DayPlanDao;
import com.hyyft.noteeverything.dao.PlanDbHelperContract;
import com.hyyft.noteeverything.dao.PlanDbHelperContract.PlanTableInfo;
import com.hyyft.noteeverything.global.NoteGlobal;
import com.hyyft.noteeverything.modal.DayPlan;
import com.hyyft.noteeverything.plan.AddPlanActivity;
import com.hyyft.noteeverything.plan.CreateTimeDialog;
import com.hyyft.noteeverything.plan.CreateTimeDialog.CreateTimeDialogCallBack;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PlanFragment extends ListFragment implements CreateTimeDialogCallBack
      , DayPlanAdapterCallBack{

	public static final int CALENDAR_REQUEST_CODE = 1;
	public static final int ADDPLAN_REQUEST_CODE = 2;
	private static final String Tag = "PlanFragment";
	private View planItemView;
	private TextView dateTextView;
	private NoteGlobal noteGlobal;
	private ListView listView;
	private Button mbtn_add;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View mainView = inflater.inflate(R.layout.dayplanfragment_layout, container, false);
		listView = (ListView)mainView.findViewById(android.R.id.list);
		
		mbtn_add = (Button)mainView.findViewById(R.id.btn_add_plan);		
		mbtn_add.setOnClickListener(btn_add_l);	
		
		dateTextView = (TextView)mainView.findViewById(R.id.date_textview);
		dateTextView.setOnClickListener(new OnClickListener() {
			//选择时间
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new CreateTimeDialog( getActivity() , PlanFragment.this ).getDate();
			}
		});
		
		addPlan_View();  //加载今日的计划
		
		//设置日期（显示今天的日期）
		Time time = new Time();
		time.setToNow();
		dateTextView.setText(""+time.year+"-"+(time.month+1)+"-"+time.monthDay);
    	return mainView;
	}
	
	

	/**
	 * 处理subActivity的返回值
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//获取日期
		switch (requestCode) {
		case ADDPLAN_REQUEST_CODE:
			if( resultCode == 1 ){
				addPlan_View();  //重新加载计划
			}
			break;
		default:
			break;
		}
	}
	
	private void addPlan_View(){
		  //加载今日计划
				DayPlanAdapter adapter = new DayPlanAdapter(getActivity() , this);		
				noteGlobal = (NoteGlobal)getActivity().getApplication();
				for( int i=0 ; i<noteGlobal.planList.size() ;i++  ){
					
					adapter.addList(noteGlobal.planList.get(i));
					//Log.i(Tag , noteGlobal.planList.get(i).toString());
				}		
				listView.setAdapter(adapter);
	}
	
			
	
	/**
	 * btn_add 按钮的消息响应函数
	 */
	private OnClickListener btn_add_l = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), AddPlanActivity.class);
			startActivityForResult(intent, ADDPLAN_REQUEST_CODE);
		}
	};
	
	
	
	/**
	 * 加载指定时间的计划
	 */
	@Override
	public void dateDialogCallBack(String date) {
		// TODO Auto-generated method stub
		DayPlanAdapter adapter = new DayPlanAdapter(getActivity() , this);		
		DayPlanDao dbDao = new DayPlanDao(getActivity());
		List<DayPlan> arraylist = new ArrayList<DayPlan>();
		arraylist = dbDao.getAll(PlanTableInfo.PLAN_TABLE_NAME,
				date );
		for( int i=0 ; i<arraylist.size() ;i++  ){	
			adapter.addList(arraylist.get(i));
		}		
		listView.setAdapter(adapter);
		dateTextView.setText(date);
	}

	@Override
	public void timeDialogCallBack(String time) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void ptimeDialogCallBack(int hour, int minute) {
		// TODO Auto-generated method stub
		
	}



	@Override  
	public void PressBtnStart(int position) {
		// TODO Auto-generated method stub
		DayPlanDao dao = new DayPlanDao(getActivity());
		ContentValues values = new ContentValues();
		noteGlobal.planList.get(position).setRealBeginTime(System.currentTimeMillis());
		noteGlobal.planList.get(position).setIsFinish((short)2);
		values.put(PlanDbHelperContract.PlanTableInfo.COLUMN_NAME_ISFINISH, noteGlobal.planList.get(position).getIsFinish());
		values.put(PlanDbHelperContract.PlanTableInfo.COLUMN_NAME_REALBEGINTIME, noteGlobal.planList.get(position).getRealBeginTime());
		dao.update(values,  noteGlobal.planList.get(position).getOrder(), dateTextView.getText().toString());
		
	}



	@Override
	public void PressBtnEnd(int position ,Button startAndStopButton , Button deleteButton  ) {
		// TODO Auto-generated method stub
		deleteButton.setEnabled(false);
		startAndStopButton.setEnabled(false);
		
		DayPlanDao dao = new DayPlanDao(getActivity());
		ContentValues values = new ContentValues();
		long now = System.currentTimeMillis();
		noteGlobal.planList.get(position).setIsFinish((short )1 );
		noteGlobal.planList.get(position).setRealTime( (int)(now - noteGlobal.planList.get(position).getRealBeginTime())/60000);
		values.put(PlanDbHelperContract.PlanTableInfo.COLUMN_NAME_REALTIME, noteGlobal.planList.get(position).getRealTime());
		values.put(PlanDbHelperContract.PlanTableInfo.COLUMN_NAME_ISFINISH, noteGlobal.planList.get(position).getIsFinish());
		dao.update(values, noteGlobal.planList.get(position).getOrder(), dateTextView.getText().toString());
//		Log.i("yuan" , "PressBtnEnd:"+position+"##"+dao.getAll(PlanDbHelperContract.PlanTableInfo.PLAN_TABLE_NAME, dateTextView.getText().toString())
//				.get(position).getIsFinish());
//		Log.i("yuan" , "PressBtnEnd:"+position+"##"+dao.getAll(PlanDbHelperContract.PlanTableInfo.PLAN_TABLE_NAME, dateTextView.getText().toString())
//				.get(position).getOrder());
	}



	@Override
	public void PressBtnStop(int position) {
		// TODO Auto-generated method stub
        DayPlanDao dao = new DayPlanDao(getActivity());
		
		DayPlan dayPlan = noteGlobal.planList.get(position);
		dayPlan.setRealTime( dayPlan.getRealTime() + (int)( ( System.currentTimeMillis()-dayPlan.getRealBeginTime() )/(1000*60) ));
		ContentValues values = new ContentValues();
		values.put(PlanDbHelperContract.PlanTableInfo.COLUMN_NAME_REALTIME,dayPlan.getRealTime());
        dao.update(values,  noteGlobal.planList.get(position).getOrder(), dateTextView.getText().toString());
        
        //Log.i("yuan" , ""+dayPlan.getRealTime());
	}



	@Override
	public void PressBtnDel(int position) {
		// TODO Auto-generated method stub
		noteGlobal.deletePlan(position, dateTextView.getText().toString());
		addPlan_View();
	}



	@Override
	public void timeDialogCallBack(int hour, int minute) {
		// TODO Auto-generated method stub
		
	}



	

}
