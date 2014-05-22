package com.hyyft.noteeverything.fragment;

import java.util.ArrayList;
import java.util.List;

import com.hyyft.noteeverything.MainActivity;
import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.adapter.DayPlanAdapter;
import com.hyyft.noteeverything.adapter.DayPlanAdapter.DayPlanAdapterCallBack;
import com.hyyft.noteeverything.dao.DayPlanDao;
import com.hyyft.noteeverything.dao.PlanDbHelperContract;
import com.hyyft.noteeverything.dao.PlanDbHelperContract.PlanTableInfo;
import com.hyyft.noteeverything.global.NoteGlobal;
import com.hyyft.noteeverything.modal.DayPlan;
import com.hyyft.noteeverything.plan.AddPlanActivity;
import com.hyyft.noteeverything.plan.CheckMoreActivity;
import com.hyyft.noteeverything.plan.CreateTimeDialog;
import com.hyyft.noteeverything.plan.CreateTimeDialog.CreateTimeDialogCallBack;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.Time;
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
		dateString = ""+time.year+"-"+(time.month+1)+"-"+time.monthDay;
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
			//ѡ��ʱ��
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new CreateTimeDialog( getActivity() , PlanFragment.this ).getDate();
			}
		});
		dateTextView.setText(dateString);
		
		addPlanByDate(dateTextView.getText().toString());  //���ؽ��յļƻ�
		
		//�������ڣ���ʾ��������ڣ�
		
		
		
    	return mainView;
	}
	
	

	/**
	 * ����subActivity�ķ���ֵ
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//��ȡ����
		switch (requestCode) {
		case ADDPLAN_REQUEST_CODE:
			if( resultCode == 1 ){
				addPlanByDate(dateTextView.getText().toString());  //���¼��ؼƻ�
			}
			break;
		default:
			break;
		}
	}
	
	private void addPlanByDate(String date){
		Time time = new Time();
		time.setToNow();
		String today = ""+time.year+"-"+(time.month+1)+"-"+time.monthDay;
		if( today.equals(date) ){
			addPlan_View();
		}
		else {
			dateDialogCallBack(date);
		}
	}
	
	private void addPlan_View(){
		  //���ؽ��ռƻ�
				DayPlanAdapter adapter = new DayPlanAdapter(getActivity() , this);		
				noteGlobal = (NoteGlobal)getActivity().getApplication();
				for( int i=0 ; i<noteGlobal.planList.size() ;i++  ){
					
					adapter.addList(noteGlobal.planList.get(i));
					//Log.i(Tag , noteGlobal.planList.get(i).toString());
				}		
				listView.setAdapter(adapter);
	}
	
			
	
	/**
	 * btn_add ��ť����Ϣ��Ӧ����
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
	 * ����ָ��ʱ��ļƻ�
	 */
	@Override
	public void dateDialogCallBack(String date) {
		// TODO Auto-generated method stub
		DayPlanAdapter adapter = new DayPlanAdapter(getActivity() , this);		
		DayPlanDao dbDao = new DayPlanDao(getActivity());
		List<DayPlan> arraylist = new ArrayList<DayPlan>();
		arraylist = dbDao.getAll(PlanTableInfo.PLAN_TABLE_NAME,
				date );
		for( int i=arraylist.size()-1 ; i>=0 ;i--  ){	
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
		MainActivity.mainService.updateAlarm();
	}



	@Override
	public void PressBtnEnd(int position ,Button startAndStopButton , Button deleteButton  ) {
		// TODO Auto-generated method stub
//		deleteButton.setEnabled(false);
//		startAndStopButton.setEnabled(false);
		
//		listView.getChildAt(position).findViewById(R.id.btn_plan_start_stop).setEnabled(false);
		listView.getChildAt(position-listView.getFirstVisiblePosition())
				.findViewById(R.id.btn_plan_give_up).setEnabled(false);
		
		DayPlanDao dao = new DayPlanDao(getActivity());
		ContentValues values = new ContentValues();
		long now = System.currentTimeMillis();
		if(noteGlobal.planList.get(position).getIsFinish() == 0){
			noteGlobal.planList.get(position).setRealTime(0);
		}
		else {
			noteGlobal.planList.get(position).setRealTime( (int)(now - noteGlobal.planList.get(position).getRealBeginTime())/60000);
		}
		noteGlobal.planList.get(position).setIsFinish((short )1 );
		
		
		values.put(PlanDbHelperContract.PlanTableInfo.COLUMN_NAME_REALTIME, noteGlobal.planList.get(position).getRealTime());
		values.put(PlanDbHelperContract.PlanTableInfo.COLUMN_NAME_ISFINISH, noteGlobal.planList.get(position).getIsFinish());
		dao.update(values, noteGlobal.planList.get(position).getOrder(), dateTextView.getText().toString());
		MainActivity.mainService.updateAlarm();
		//addPlan_View();
		//		Log.i("yuan" , "PressBtnEnd:"+position+"##"+dao.getAll(PlanDbHelperContract.PlanTableInfo.PLAN_TABLE_NAME, dateTextView.getText().toString())
//				.get(position).getIsFinish());
//		Log.i("yuan" , "PressBtnEnd:"+position+"##"+dao.getAll(PlanDbHelperContract.PlanTableInfo.PLAN_TABLE_NAME, dateTextView.getText().toString())
//				.get(position).getOrder());
	}



	@Override
	public void PressBtnCheck(int position) {
		// TODO Auto-generated method stub
		
          Intent intent = new Intent(getActivity() , CheckMoreActivity.class);  
          intent.putExtra("index", position);
          Time time = new Time();
  		  time.setToNow();
  		  String today = ""+time.year+"-"+(time.month+1)+"-"+time.monthDay;
  		  if( today.equals(dateTextView.getText().toString()) ){
  			  intent.putExtra("istoday", true);
  		  }
  		  else {
  			intent.putExtra("istoday", false);
  			intent.putExtra("date", dateTextView.getText().toString());
		 }
          
          startActivity(intent);
	}



	@Override
	public void PressBtnDel(int position) {
		// TODO Auto-generated method stub
		noteGlobal.deletePlan(position, dateTextView.getText().toString());
		MainActivity.mainService.updateAlarm();
		addPlanByDate(dateTextView.getText().toString());
	}



	@Override
	public void timeDialogCallBack(int hour, int minute) {
		// TODO Auto-generated method stub
		
	}



	

}
