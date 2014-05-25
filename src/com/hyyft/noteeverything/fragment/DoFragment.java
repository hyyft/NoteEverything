package com.hyyft.noteeverything.fragment;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.adapter.DoItemAdapter;
import com.hyyft.noteeverything.adapter.DoItemAdapter.DoItemAdapterCallBack;
import com.hyyft.noteeverything.dowhat.AddDoItem_Activity;
import com.hyyft.noteeverything.dowhat.ModifyDoItem_Activity;
import com.hyyft.noteeverything.global.NoteGlobal;
import com.hyyft.noteeverything.plan.CreateTimeDialog;
import com.hyyft.noteeverything.plan.CreateTimeDialog.CreateTimeDialogCallBack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * dowhat 的fragment 类
 * @author Administrator
 *
 */
public class DoFragment extends Fragment implements DoItemAdapterCallBack , CreateTimeDialogCallBack{

	//private static final String TAG = "DoFragment";
	private TextView dateTextView;
	private NoteGlobal noteGlobal;
	private ListView listView;
	private Button addButton;
	private View fragmentView;
	private int index = -1;
	private static final int ID_MODIFY = 0;
	private static final int ID_DELETE = 1;
	private String dateStr;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		noteGlobal = (NoteGlobal)getActivity().getApplication();
		Time time = new Time();
		time.setToNow();
		dateStr = ""+time.year+"-"+(time.month+1)+"-"+time.monthDay;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragmentView = inflater.inflate(R.layout.dofragment_layout, container, false);
		listView = (ListView)fragmentView.findViewById(android.R.id.list);
		dateTextView = (TextView)fragmentView.findViewById(R.id.do_tv_date);
		addButton = (Button )fragmentView.findViewById(R.id.do_btn_additem);
		addButton.setOnClickListener(listener);
		
		
		dateTextView.setText(dateStr);
		dateTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new CreateTimeDialog(getActivity(), DoFragment.this).getDate();
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				index = position;
				return false;
			}
		});
		listView.setOnCreateContextMenuListener(contentlistener);
		
		setView();
		return fragmentView;
	}
	
	/**
	 * 设置fragment的界面
	 */
	private void setView(){
		DoItemAdapter adapter = new DoItemAdapter(getActivity(), this);
		noteGlobal.getDoWhat(dateTextView.getText().toString());
		
		
		for( int i=0 ; i<noteGlobal.doList.size() ;i++  ){
			
			adapter.addList(noteGlobal.doList.get(i));
			//Log.i("yuan" , noteGlobal.doList.get(i).getContent());
		}		
		listView.setAdapter(adapter);
	}
	
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), ""+item.getItemId(), Toast.LENGTH_LONG).show();
		switch (item.getItemId()) {
		case ID_MODIFY:
			startActivityForResult(new Intent( getActivity() , ModifyDoItem_Activity.class)
				.putExtra("index", index) , 1 );
			break;
		case ID_DELETE:
			noteGlobal.DelDoItem(index, dateTextView.getText().toString());
			setView();
			break;

		default:
			break;
		}
		index = -1;
		return true;
	}
	
	
	



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 1:
			if(resultCode ==1)setView();
			break;
		case 2:
			if(resultCode == 1)setView();
			break;
		default:
			break;
		}
	}






	/**
	 * 添加条目的listener
	 */
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			startActivityForResult(new Intent(getActivity() , AddDoItem_Activity.class) , 2);
		}
	};
	
	private OnCreateContextMenuListener contentlistener = new OnCreateContextMenuListener() {
		
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			menu.setHeaderTitle("menu");
			menu.add(0, ID_MODIFY, 0, "修改");   
			menu.add(0, ID_DELETE, 1, "删除");
		}
	};
	
	
	@Override
	public void dateDialogCallBack(String date) {
		// TODO Auto-generated method stub
		dateStr = date;
		dateTextView.setText(dateStr);
		setView();
	}

	@Override
	public void timeDialogCallBack(String time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void timeDialogCallBack(int hour, int minute) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ptimeDialogCallBack(int hour, int minute) {
		// TODO Auto-generated method stub
		
	}
	

}
