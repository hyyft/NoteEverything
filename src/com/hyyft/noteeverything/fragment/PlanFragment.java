package com.hyyft.noteeverything.fragment;


import com.hyyft.noteeverything.MainActivity;
import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.adapter.DayPlanAdapter;
import com.hyyft.noteeverything.adapter.DayPlanAdapter.DayPlanAdapterCallBack;
import com.hyyft.noteeverything.dao.DayPlanDao;
import com.hyyft.noteeverything.dao.PlanDbHelperContract;
import com.hyyft.noteeverything.global.NoteGlobal;
import com.hyyft.noteeverything.plan.AddPlanActivity;
import com.hyyft.noteeverything.plan.CheckMoreActivity;
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
	private String dateString;
	//private View planItemView;
	private TextView dateTextView;
	private NoteGlobal noteGlobal;
	private ListView listView;
	private Button mbtn_add;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Time time = new Time();
		time.setToNow();
		noteGlobal = (NoteGlobal)getActivity().getApplication();
		dateString = ""+time.year+"-"+(time.month+1)+"-"+time.monthDay;
		noteGlobal.dateString = dateString;
		
	}



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
		dateTextView.setText(dateString);
		noteGlobal.getPlan(dateTextView.getText().toString());
		
		addPlan_View();      //加载计划
		
		//设置日期（显示今天的日期）
		
		
		
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
	
	
	
	/**
	 * 加载计划
	 */
	private void addPlan_View(){
		  
				DayPlanAdapter adapter = new DayPlanAdapter(getActivity() , this);		
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
		Time time = new Time();
		time.setToNow();
		
		long reaBeginltime = System.currentTimeMillis();
		short isFinish = 2;
	    //从这里开始修改
		values.put(PlanDbHelperContract.PlanTableInfo.COLUMN_NAME_ISFINISH, isFinish);
		values.put(PlanDbHelperContract.PlanTableInfo.COLUMN_NAME_REALBEGINTIME, reaBeginltime);
		noteGlobal.updatePlan(position, dateTextView.getText().toString(), values);
		MainActivity.mainService.updateAlarm();
	}



	@Override
	public void PressBtnEnd(int position ,Button startAndStopButton , Button deleteButton  ) {
		// TODO Auto-generated method stub
		listView.getChildAt(position-listView.getFirstVisiblePosition())
				.findViewById(R.id.btn_plan_give_up).setEnabled(false);
		listView.getChildAt(position-listView.getFirstVisiblePosition())
		.findViewById(R.id.btn_plan_start_stop).setEnabled(false);
		
		long now = System.currentTimeMillis();
		int realTime ;
		if(noteGlobal.planList.get(position).getIsFinish() == 0){
			realTime = 0;		
		}
		else {
			realTime =  (int) ((now - noteGlobal.planList.get(position).getRealBeginTime())/60000);
		}
		short isFinish = 1 ;
		ContentValues values = new ContentValues();
		values.put(PlanDbHelperContract.PlanTableInfo.COLUMN_NAME_REALTIME, realTime);
		values.put(PlanDbHelperContract.PlanTableInfo.COLUMN_NAME_ISFINISH, isFinish);
		noteGlobal.updatePlan(position, dateTextView.getText().toString(), values);
		
		
		
		MainActivity.mainService.updateAlarm();

	}



	@Override
	public void PressBtnCheck(int position) {
		// TODO Auto-generated method stub
		
          Intent intent = new Intent(getActivity() , CheckMoreActivity.class);  
          intent.putExtra("index", position);
          
          startActivity(intent);
	}



	@Override
	public void PressBtnDel(int position) {
		// TODO Auto-generated method stub
		noteGlobal.deletePlan(position, dateTextView.getText().toString());
		MainActivity.mainService.updateAlarm();
		addPlan_View();
	}



	@Override
	public void timeDialogCallBack(int hour, int minute) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void dateDialogCallBack(String date) {
		// TODO Auto-generated method stub
		noteGlobal.dateString=date;
		noteGlobal.getPlan(date);
		dateTextView.setText(date);
		dateString = date;
		addPlan_View();
	}



	

}
