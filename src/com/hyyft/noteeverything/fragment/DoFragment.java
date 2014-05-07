package com.hyyft.noteeverything.fragment;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.adapter.DoItemAdapter;
import com.hyyft.noteeverything.adapter.DoItemAdapter.DoItemAdapterCallBack;
import com.hyyft.noteeverything.dowhat.AddDoItem_Activity;
import com.hyyft.noteeverything.global.NoteGlobal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * dowhat 的fragment 类
 * @author Administrator
 *
 */
public class DoFragment extends Fragment implements DoItemAdapterCallBack{

	//private static final String TAG = "DoFragment";
	private TextView dateTextView;
	private NoteGlobal noteGlobal;
	private ListView listView;
	private Button addButton;
	private View fragmentView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragmentView = inflater.inflate(R.layout.dofragment_layout, container, false);
		listView = (ListView)fragmentView.findViewById(android.R.id.list);
		dateTextView = (TextView)fragmentView.findViewById(R.id.do_tv_date);
		addButton = (Button )fragmentView.findViewById(R.id.do_btn_additem);
		addButton.setOnClickListener(listener);
		
		setView();
		return fragmentView;
	}
	
	/**
	 * 设置fragment的界面
	 */
	private void setView(){
		DoItemAdapter adapter = new DoItemAdapter(getActivity(), this);
		noteGlobal = (NoteGlobal)getActivity().getApplication();
		Time time = new Time();
		time.setToNow();
		String dateStr = ""+time.year+"-"+(time.month+1)+"-"+time.monthDay;
		dateTextView.setText(dateStr);
		
		for( int i=0 ; i<noteGlobal.doList.size() ;i++  ){
			
			adapter.addList(noteGlobal.doList.get(i));
		}		
		listView.setAdapter(adapter);
	}
	
	/**
	 * 添加条目的listener
	 */
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			startActivity(new Intent(getActivity() , AddDoItem_Activity.class));
		}
	};
	

}
