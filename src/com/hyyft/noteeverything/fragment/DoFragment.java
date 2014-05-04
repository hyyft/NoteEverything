package com.hyyft.noteeverything.fragment;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.adapter.DoItemAdapter;
import com.hyyft.noteeverything.adapter.DoItemAdapter.DoItemAdapterCallBack;
import com.hyyft.noteeverything.global.NoteGlobal;
import com.hyyft.noteeverything.modal.DoWhat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DoFragment extends Fragment implements DoItemAdapterCallBack{

	private static final String TAG = "DoFragment";
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
		
		setView();
		return fragmentView;
	}
	
	private void setView(){
		DoItemAdapter adapter = new DoItemAdapter(getActivity(), this);
		noteGlobal = (NoteGlobal)getActivity().getApplication();
		Time time = new Time();
		time.setToNow();
		String dateStr = ""+time.year+"-"+(time.month+1)+"-"+time.monthDay;
		dateTextView.setText(dateStr);
		
		
		DoWhat doWhat = new DoWhat();
		doWhat.setBeginTime(time.toMillis(false));
		doWhat.setBigTag("日常");
		doWhat.setLitleTag("吃饭");
		doWhat.setContent("去吃中午饭，然后逛了逛");
		doWhat.setDate(dateStr);

		//noteGlobal.AddDoItem(doWhat);
		  //noteGlobal.AddDoItem(doWhat);
		 //noteGlobal.DelDoItem(0, dateStr);
		for( int i=0 ; i<noteGlobal.doList.size() ;i++  ){
			
			adapter.addList(noteGlobal.doList.get(i));
			Log.i(TAG , noteGlobal.doList.get(i).toString());
		}		
		listView.setAdapter(adapter);
	}
	
	
	

}
