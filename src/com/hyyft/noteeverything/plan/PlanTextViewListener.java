package com.hyyft.noteeverything.plan;


import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.plan.CreateTimeDialog.CreateTimeDialogCallBack;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PlanTextViewListener implements OnClickListener ,  
CreateTimeDialogCallBack {

	public static final int dateTV = 0;
	public static final int timeTV = 1;
	public static final int planTimeTV = 2;
	public static final int tagTV = 3;
	private Context context;
	private View view;
	
	public PlanTextViewListener( Context context ){
		this.context = context;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		this.view = v;
		if (v.getId() == R.id.add_date_textview) {
			
			onDateTextView();
			
		} else if (v.getId() == R.id.add_time_textview) {
			
			onTimeTextView();

		} else if (v.getId() == R.id.add_plantime_textview) {
			onPlanTimeTextView();
		} 
	}
	
	private void onDateTextView(){
		new CreateTimeDialog( context , this ).getDate();
	}
	private void onTimeTextView(){
		new CreateTimeDialog(context, this).getTime();
	}
	private void onPlanTimeTextView(){
		new CreateTimeDialog(context, this).getPlanTime();
	}
	
	
	
	@Override
	public void dateDialogCallBack(String date) {
		// TODO Auto-generated method stub
		TextView tView = (TextView)view;
		tView.setText(date);
	}
	@Override
	public void timeDialogCallBack(String time) {
		// TODO Auto-generated method stub
		TextView tView = (TextView)view;
		tView.setText(time);
	}
	@Override
	public void ptimeDialogCallBack(int hour , int minute) {
		// TODO Auto-generated method stub
		TextView tView = (TextView)view;
		tView.setText( ""+(hour*60 + minute ) );
	}
	@Override
	public void timeDialogCallBack(int hour, int minute) {
		// TODO Auto-generated method stub
		
	}
	

}
