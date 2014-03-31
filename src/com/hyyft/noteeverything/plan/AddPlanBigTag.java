package com.hyyft.noteeverything.plan;

import com.hyyft.noteeverything.R;
import com.hyyft.noteeverything.myconst.PrefConst;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * 在使用的时候必须通过setPlanBigTagCallBack的设置回调函数
 * @author Administrator
 *
 */
public class AddPlanBigTag extends ListFragment {

	private View view;
	private ListView listView;
	private String[] bigTag;
	private PlanBigTagCallBack callBack;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		view = inflater.inflate(R.layout.addplan_tagfragment_layout, container ,false);
		listView = (ListView) view.findViewById(android.R.id.list);
		showBigTag();
		return view;
	}
	
	
	
	
	/**
	 * 获取sharedPreference的数据
	 */
	private void getBigTag(){
		int i = 1;
		SharedPreferences sharedPreferences = 
				getActivity().getSharedPreferences(PrefConst.NAME,Context.MODE_PRIVATE);
		int count = sharedPreferences.getInt(PrefConst.BIGTAG_COUNT, 0);
		bigTag = new String[count];
		String temp;
		while( true && count!=0 ){
			temp = sharedPreferences.getString(""+i, "none");
			if( temp.equals("none") )break;
			else bigTag[i-1] = temp;
			i++;
		}
	}
	/**
	 * 显示bigTag
	 */
	public void showBigTag(){
		getBigTag();
		listView.setAdapter(new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1, bigTag));
	}
	
	/**
	 * 设置回调 函数
	 */
	public void setPlanBigTagCallBack(PlanBigTagCallBack callBack){
		this.callBack = callBack;
	}
	public interface PlanBigTagCallBack{
		public void  getBigTag(String bigTag , String bigTagID);
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		callBack.getBigTag(l.getItemAtPosition(position).toString() , ""+(position+1));
	}
	
	
	
}
