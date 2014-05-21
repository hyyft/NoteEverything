package com.hyyft.noteeverything.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.modal.*;

public class DayPlanAdapter extends BaseAdapter {

	private List<DayPlan> list = new ArrayList<DayPlan>();  
	private Context context;
	DayPlanAdapterCallBack callBack;
	private Button startAndStopButton , endButton , deleteButton , checkButton;
	
	public DayPlanAdapter(Context context , DayPlanAdapterCallBack callBack){  
        this.context = context;  
        this.callBack = callBack;
    } 
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size(); 
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = LayoutInflater.from(context)
				.inflate(R.layout.dayplanfragment_item_layout, null);
		TextView timeTextView , tagTextView , contentTextView , titleTextView;
		timeTextView = (TextView)convertView.findViewById(R.id.dayplanfragment_item_time);
		tagTextView = (TextView)convertView.findViewById(R.id.dayplanfragment_item_tag);
		contentTextView = (TextView)convertView.findViewById(R.id.dayplanfragment_item_content);
		titleTextView = (TextView)convertView.findViewById(R.id.dayplanfragment_item_title);
		
		
		startAndStopButton = (Button) convertView.findViewById(R.id.btn_plan_start_stop);
		startAndStopButton.setTag(position);
		endButton = (Button)convertView.findViewById(R.id.btn_plan_finish);
		endButton.setTag(position);
		
		deleteButton = ( Button )convertView.findViewById(R.id.btn_plan_give_up);
		deleteButton.setTag(position);
		startAndStopButton.setOnClickListener(listViewListener);
		endButton.setOnClickListener(listViewListener);
		deleteButton.setOnClickListener(listViewListener);
		
		checkButton = (Button)convertView.findViewById(R.id.btn_plan_checkplan);
		checkButton.setTag(position);
		checkButton.setOnClickListener(listViewListener);
		
		Calendar calendar = Calendar.getInstance();
		String dateString = ""+calendar.get(Calendar.YEAR)+"-"+
							( calendar.get(Calendar.MONTH) +1 )+"-"+
							calendar.get(Calendar.DAY_OF_MONTH);
		if(list.get(position).getDate().compareTo(dateString) < 0 )
		{
			startAndStopButton.setEnabled(false);
			deleteButton.setEnabled(false);
			endButton.setEnabled(false); 
		}
		else if( list.get(position).getIsFinish() == 1 ){
			startAndStopButton.setEnabled(false);
			deleteButton.setEnabled(false);
			endButton.setEnabled(false); 
			}
		else  if( list.get(position).getIsFinish() == 2 )
			startAndStopButton.setEnabled(false);
		
		final Calendar mCalendar=Calendar.getInstance();
		mCalendar.setTimeInMillis(list.get(position).getPlanBeginTime());
		
		timeTextView.setText(mCalendar.get(Calendar.HOUR_OF_DAY)+":"+mCalendar.get(Calendar.MINUTE));
		tagTextView.setText(list.get(position).getBigTag()+"-"+list.get(position).getLitleTag());
		titleTextView.setText(list.get(position).getTitle());
		contentTextView.setText(list.get(position).getContent());
		//Log.i("TAG"  , list.get(position).getLevel()+list.get(position).getOrder() );
		return convertView;
	}
	public void addList(DayPlan dayPlan){  
        list.add(dayPlan);  
    } 
	
	
	
	
     private OnClickListener listViewListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			// TODO Auto-generated method stub
			//int position = listView.getSelectedItemPosition();
			
			
			int position = (Integer)view.getTag();
			Button button = (Button)view;
			//Resources resources = context.getResources();
			
			if( view.getId() == R.id.btn_plan_start_stop ){
					button.setEnabled(false);
					callBack.PressBtnStart(position);
				
				
			}
			else if( view.getId() == R.id.btn_plan_finish ){
				
				button.setEnabled(false);
				//startAndStopButton.setEnabled(false);
				//deleteButton.setEnabled(false);
				
				callBack.PressBtnEnd(position , startAndStopButton , deleteButton);			
			}else if( view.getId() == R.id.btn_plan_give_up ) {
				callBack.PressBtnDel(position);

			}
			else if( view.getId() == R.id.btn_plan_checkplan ){
				callBack.PressBtnCheck(position);
			}
      }
	};
	
	public interface DayPlanAdapterCallBack{
		public void PressBtnStart(int position);
		public void PressBtnEnd(int position ,Button startAndStopButton , Button deleteButton);
		public void PressBtnCheck(int position );
		public void PressBtnDel(int position);
	}

}
