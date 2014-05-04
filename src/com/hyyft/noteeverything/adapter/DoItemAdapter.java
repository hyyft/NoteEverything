package com.hyyft.noteeverything.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.modal.*;

public class DoItemAdapter extends BaseAdapter {

	private List<DoWhat> list = new ArrayList<DoWhat>();  
	private Context context;
	DoItemAdapterCallBack callBack;
	private TextView timeTextView , tagTextView , conTextView;
	
	public DoItemAdapter(Context context , DoItemAdapterCallBack callBack){  
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
				.inflate(R.layout.dofragment_item_layout, null);
		if( convertView == null ){
			Log.i("DoFragment" , "null point");
		}
		timeTextView = (TextView)convertView.findViewById(R.id.do_tv_time);
		tagTextView = (TextView)convertView.findViewById(R.id.do_tv_tag);
		conTextView = (TextView)convertView.findViewById(R.id.do_tv_content);

		final Calendar mCalendar=Calendar.getInstance();
		mCalendar.setTimeInMillis(list.get(position).getBeginTime());
		String timeString = list.get(position).getDate()+"  "+
				mCalendar.get(Calendar.HOUR_OF_DAY)+
				mCalendar.get(Calendar.MINUTE);
		timeTextView.setText(timeString);
		tagTextView.setText(list.get(position).getBigTag()+"-"+list.get(position).getLitleTag());
		conTextView.setText(list.get(position).getContent());
		return convertView;
	}
	public void addList(DoWhat doWhat){  
        list.add(doWhat);  
    } 
	

	public interface DoItemAdapterCallBack{
		
	}

}
